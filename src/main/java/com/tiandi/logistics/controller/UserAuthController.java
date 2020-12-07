package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.async.AsyncPhoneRegisterTask;
import com.tiandi.logistics.entity.pojo.Company;
import com.tiandi.logistics.entity.pojo.OrganizationRelation;
import com.tiandi.logistics.entity.pojo.Role;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.CompanyService;
import com.tiandi.logistics.service.OrganizationRelationService;
import com.tiandi.logistics.service.RoleService;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.JWTUtil;
import com.tiandi.logistics.utils.Md5Encoding;
import com.tiandi.logistics.utils.RedisUtil;
import com.tiandi.logistics.utils.SMSSendingUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ParameterType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 用户登录相关控制层接口
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:59
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "用户登录")
public class UserAuthController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OrganizationRelationService relationService;
    @Autowired
    private AsyncPhoneRegisterTask registerTask;

    /**
     * 邮箱校验正则表达式
     */
    private final Pattern emailRole = Pattern.compile("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$");
    /**
     * 电话校验正则表达式
     */
    private final Pattern phoneRole = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");

    /**
     * 登录用接口，登录时判断账户状态，将登陆产生的平整机进行缓存，进行二次校验使用
     *
     * @param identification 用户登录标识 包括： 用户名，邮箱及电话
     * @param password       密码
     * @return
     */
    @PostMapping("/login")
    @ControllerLogAnnotation(remark = "用户登录——用户名密码方式", sysType = SysTypeEnum.NORMAL, opType = OpTypeEnum.LOGIN)
    @ApiOperation(value = "用户登录接口",notes = "支持用户名、邮箱、手机号码三种登录输入，该方法为传统的密码校验方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "identification", value = "用户名、邮箱、手机号码选其一", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "修改成功！请稍后前往邮箱进行激活！"),
            @ApiResponse(code = 50010, message = "服务器内部错误"),
            @ApiResponse(code = 50011, message = "账户已被封禁，请另行注册!"),
            @ApiResponse(code = 50012, message = "密码错误或账户不存在!")
    })
    public ResultMap login(@RequestParam("identification") String identification, @RequestParam("password") String password) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (emailRole.matcher(identification).matches()) {
            wrapper.eq("email", identification);
        } else if (phoneRole.matcher(identification).matches()) {
            wrapper.eq("phone", identification);
        } else {
            wrapper.eq("username", identification);
        }

        User user = userService.getOne(wrapper);

        if (user != null && user.getPassword().equals(Md5Encoding.md5RanSaltEncode(password, user.getSalt()))) {
            if (user.getBan() == 1) {
                return resultMap.fail().code(50010).message("账户未激活，请激活后进行登录!");
            }
            if (user.getIsDelete() == 1) {
                return resultMap.fail().code(50011).message("账户已被封禁，请另行注册!");
            }
            Role role = roleService.getOne(new QueryWrapper<Role>().eq("id_tb_role", user.getRole()));
            List<String> list = new ArrayList<>();
            list.add(role.getRole());
            list.add(role.getPermission());
            String token = JWTUtil.createToken(user.getUsername(), user.getIdTbUser().toString(),
                    role.getRole(), role.getPermission(), JSON.toJSONString(list));
            if (redisUtil.hasKey(user.getUsername())) {
                redisUtil.del(user.getUsername());
            }
            redisUtil.set(user.getUsername(), token, 60 * 60 * 24);
            resultMap.success().message("登陆成功！").addElement("token", token);
        } else {
            resultMap.fail().code(50012).message("密码错误或账户不存在!");
        }

        return resultMap;
    }

    /**
     * 登陆用短信验证码获取接口
     *
     * @param phone 登录用手机号
     * @return
     * @throws Exception
     */
    @PostMapping("/getPhoneLoginCode")
    @ControllerLogAnnotation(sysType = SysTypeEnum.CUSTOMER, opType = OpTypeEnum.ADD)
    @ApiOperation(value = "手机短信登录验证码获取接口")
    @ApiImplicitParam(name = "phone", value = "手机号码", required = true, paramType = "query", dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "短信已发送"),
            @ApiResponse(code = 50011, message = "请输入正确的手机号")
    })
    public ResultMap getPhoneLoginCode(@RequestParam("phone") String phone) throws Exception {
        if (!phoneRole.matcher(phone).matches()) {
            return resultMap.fail().code(50011).message("请输入正确的手机号");
        }

        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            builder.append(random.nextInt(10));
        }

        int count = userService.count(new QueryWrapper<User>().eq("phone", phone));

        if (count < 1) {
            registerTask.phoneRegister(phone);
        }

        //反射拿到对应方法
        Method method = UserAuthController.class.getDeclaredMethod("getPhoneLoginCode", String.class);
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
        memberValues.put("remark", "发送至 " + phone + " 的登陆验证码为： " + builder.toString());

        redisUtil.set(phone, builder.toString(), 60 * 5);
        SMSSendingUtil.sendALiSms(phone, builder.toString());

        return resultMap.success().message("短信已发送");
    }

    /**
     * 手机号登陆接口
     *
     * @param phone 电话
     * @param code  验证码
     * @return
     */
    @PostMapping("/loginByPhone")
    @ControllerLogAnnotation(remark = "用户登录——电话验证", sysType = SysTypeEnum.NORMAL, opType = OpTypeEnum.LOGIN)
    @ApiOperation(value = "手机短信登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "六位验证码", required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "登陆成功！"),
            @ApiResponse(code = 50011, message = "验证码过期，请重新获取")
    })
    public ResultMap loginByPhone(@RequestParam("phone") String phone, @RequestParam("code") String code) {

        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("phone", phone);

        if (redisUtil.hasKey(phone) && code.equals(redisUtil.get(phone).toString())) {
            User user = userService.getOne(wrapper);
            Role role = roleService.getOne(new QueryWrapper<Role>().eq("id_tb_role", user.getRole()));
            List<String> list = new ArrayList<>();
            list.add(role.getRole());
            list.add(role.getPermission());
            String token = JWTUtil.createToken(user.getUsername(), user.getIdTbUser().toString(),
                    role.getRole(), role.getPermission(), JSON.toJSONString(list));
            redisUtil.del(phone);
            redisUtil.set(user.getUsername(), token, 60 * 60 * 24);
            return resultMap.success().message("登陆成功！").addElement("token", token);
        } else {
            return resultMap.fail().code(50011).message("验证码过期，请重新获取");
        }
    }

    /**
     * 信息获取接口，前端动态授权支持接口
     *
     * @param token 凭证
     * @return
     */
    @PostMapping("/getInf")
    @ApiOperation(value = "前端授权信息获取接口")
    @ApiImplicitParam(name = "token", value = "用户登陆凭证", required = true, paramType = "header", dataType = "String")
    @ApiResponse(code = 40000, message = "认证信息返回")
    @RequiresRoles(logical = Logical.OR,value = {"user","driver","vehicleManager","distribution","branchCompany","headCompany","admin"})
    public ResultMap getInf(@RequestHeader String token) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", JWTUtil.getUsername(token));

        User user = userService.getOne(wrapper);

        String roleJSON = JWTUtil.getRoleJSON(token);

        String userRole = JWTUtil.getUserRole(token);

        if ("user".equals(userRole)) {
            resultMap.addElement("orgName",user.getPetname() + "  用户  欢迎您");
        } else if ("admin".equals(userRole)){
            resultMap.addElement("orgName","欢迎登陆管理员主界面");
        } else {
            String organization = relationService.getOne(
                    new QueryWrapper<OrganizationRelation>().eq("user_id", user.getIdTbUser())).getOrganization();
            resultMap.addElement("orgName", organization);
        }

        resultMap.success().addElement("username", user.getUsername()).addElement("email", user.getEmail())
                .addElement("phone", user.getPhone()).addElement("role", roleJSON).addElement("icon",user.getIcon())
                .addElement("petName", user.getPetname()).message("认证信息返回");

        return resultMap;
    }
}
