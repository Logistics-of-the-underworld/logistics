package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.async.AsyncMailTask;
import com.tiandi.logistics.entity.pojo.OrganizationRelation;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.OrganizationRelationService;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.*;
import io.swagger.annotations.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/25 15:17
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "用户注册")
public class UserRegisterController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AsyncMailTask mailTask;
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationRelationService relationService;

    /**
     * 电话校验正则表达式
     */
    private final Pattern phoneRole = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");

    /**
     * 注册方法，此处全套不进行实体内部的值的判断
     * 一个方法包含多种注册实现方式
     * 如果通过电话注册，那么在进行直接通过短信获取验证码即可进行用户的激活处理
     * 邮箱注册主要是实现企业添加员工账号时使用的一种方式
     *
     * @param userStr User对应的JSON字符串
     * @return 注册提示信息
     */
    @PostMapping("/register")
    @ControllerLogAnnotation(remark = "注册功能", sysType = SysTypeEnum.NORMAL, opType = OpTypeEnum.ADD)
    @ApiOperation(value = "用户注册接口",notes = "继承普通用户注册及公司内部添加员工方法为一体\n利用缺省的空值作为判断依据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "User实体类JSON字符串", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "icon", value = "用户头像", paramType = "query", dataType = "File"),
            @ApiImplicitParam(name = "code", value = "六位验证码，用于手机号注册用户使用", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "organization", value = "用户所属组织", paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "登陆成功！"),
            @ApiResponse(code = 50011, message = "验证码过期，请重新获取")
    })
    public ResultMap register(@RequestParam("user") String userStr,
                              @RequestParam(value = "icon", required = false) MultipartFile file,
                              @RequestParam(value = "code", required = false) String code,
                              @RequestParam(value = "organization", required = false) String organization,
                              @RequestHeader(required = false) String token) {
        //判空，防止抛出异常
        if (userStr == null || "".equals(userStr)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        //JSON转换对象
        User user = JSON.parseObject(userStr, User.class);
        //判断手机验证码是否过期，如若过期则直接拒绝
        if (null != user.getPhone() && code != null && !code.equals(redisUtil.get(user.getPhone() + "TP").toString())) {
            return resultMap.code(50010).fail().message("验证码过期，请重新获取");
        }
        if (null == user.getRole()) {
            //强制配置权限
            user.setRole(1);
        }
        //默认用户昵称
        if (user.getPetname() == null || "".equals(user.getPetname())) {
            user.setPetname("TP用户" + new Random().nextInt(1000));
        }

        //密码加密处理
        String salt = Md5Encoding.md5RandomSaltGenerate();
        user.setSalt(salt);
        user.setPassword(Md5Encoding.md5RanSaltEncode(user.getPassword(), salt));
        //UUID为键，存储在Redis中，供激活使用
        String userUUID = UUID.randomUUID().toString();
        user.setActiveuuid(userUUID);
        boolean insert = false;
        if (user.getPhone() == null || "".equals(user.getPhone())) {
            user.setBan(1);
        } else {
            user.setBan(0);
        }
        if (token != null) {
            String userRole = JWTUtil.getUserRole(token);
            if ("branchCompany".equals(userRole)) {
                user.setRole(8);
            } else if ("headCompany".equals(userRole)) {
                user.setRole(10);
            } else if ("distribution".equals(userRole)) {
                user.setRole(6);
            }
        }
        //判断是否有用户头像的输入
        if (file != null) {
            user.setIcon(MinioUtil.getInstance().upLoadMultipartFile(file));
            file = null;
            //插入操作
            insert = userService.save(user);
        } else {
            //插入操作
            insert = userService.save(user);
        }
        //注册成功后的逻辑判断
        if (insert) {
            if (user.getPhone() != null) {
                redisUtil.del(user.getPhone() + "TP");
                return resultMap.success().message("注册成功！");
            } else {
                OrganizationRelation relation = new OrganizationRelation();
                relation.setOrganization(organization);
                relation.setUser_id(user.getIdTbUser());
                relationService.save(relation);
                //Reds保存(UUID，userID)键值对
                redisUtil.set(userUUID, user.getIdTbUser(), 60 * 60 * 24);
                //异步提交激活邮件发送请求
                mailTask.activeMailTask(userUUID, user.getEmail());
                return resultMap.success().message("添加成功！请通知其前往邮箱进行激活！");
            }
        }
        //所有方法均为匹配，特殊处理返回
        return resultMap.fail().code(40010).message("服务器内部错误");
    }

    /**
     * 用户注册获取手机验证码进行注册验证
     *
     * @param phone 手机号
     * @return
     * @throws Exception
     */
    @PostMapping("/getRegisterPhoneCode")
    @ControllerLogAnnotation(sysType = SysTypeEnum.CUSTOMER, opType = OpTypeEnum.ADD)
    @ApiOperation(value = "手机短信注册验证码获取接口")
    @ApiImplicitParam(name = "phone", value = "手机号码", required = true, paramType = "query", dataType = "String")
    @ApiResponse(code = 40000, message = "短信已发送")
    public ResultMap getPhoneCode(@RequestParam("phone") String phone) throws Exception {

        if (!phoneRole.matcher(phone).matches()) {
            return resultMap.fail().message("请输入正确的手机号");
        }

        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            builder.append(random.nextInt(10));
        }

        //反射拿到对应方法
        Method method = UserRegisterController.class.getDeclaredMethod("getPhoneCode", String.class);
        //获取日志切面注解
        ControllerLogAnnotation annotation = method.getAnnotation(ControllerLogAnnotation.class);
        //获取注解实例所持有的的InvocationHandler
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        //获取该注解中所有的属性
        Field field = handler.getClass().getDeclaredField("memberValues");
        //该属性为private final的，需要开启权限
        field.setAccessible(true);
        //获取map对象
        Map<String, Object> memberValues = (Map<String, Object>) field.get(handler);
        //利用反射修改
        memberValues.put("remark", "发送至 " + phone + " 的注册验证码为： " + builder.toString());

        builder.append("TP");
        redisUtil.set(phone, builder.toString(), 60 * 5);
        SMSSendingUtil.sendALiSms(phone, builder.toString());

        return resultMap.success().message("短信已发送");
    }

    /**
     * 表单校验支持接口 —— username
     *
     * @param username 用户名
     * @return 是否可用 => 200 可用 => 201 不可用
     */
    @GetMapping("/verifyUsername/{username}")
    @ApiOperation(value = "表单校验支持接口 —— username")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "可用"),
            @ApiResponse(code = 50013, message = "已被使用")
    })
    public ResultMap verifyUsername(@PathVariable String username) {
        if (userService.count(new QueryWrapper<User>().eq("username", username)) < 1) {
            return resultMap.success().message("可用");
        }
        return resultMap.fail().message("已被使用");
    }

    /**
     * 表单校验支持接口 —— email
     *
     * @param email 邮箱
     * @return 是否可用 => 200 可用 => 201 不可用
     */
    @GetMapping("/verifyEmail/{email}")
    @ApiOperation(value = "表单校验支持接口 —— email")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "path", dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "可用"),
            @ApiResponse(code = 50013, message = "已被使用")
    })
    public ResultMap verifyEmail(@PathVariable String email) {
        if (userService.count(new QueryWrapper<User>().eq("email", email)) < 1) {
            return resultMap.success().message("可用");
        }
        return resultMap.fail().message("已被使用");
    }

    /**
     * 表单校验支持接口 —— phone
     *
     * @param phone 手机号码
     * @return 是否可用 => 200 可用 => 201 不可用
     */
    @GetMapping("/verifyPhone/{phone}")
    @ApiOperation(value = "表单校验支持接口 —— phone")
    @ApiImplicitParam(name = "phone", value = "手机号码", required = true, paramType = "path", dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "可用"),
            @ApiResponse(code = 50013, message = "已被使用")
    })
    public ResultMap verifyPhone(@PathVariable String phone) {
        if (userService.count(new QueryWrapper<User>().eq("phone", phone)) < 1) {
            return resultMap.success().message("可用");
        }
        return resultMap.fail().message("已被使用");
    }
}
