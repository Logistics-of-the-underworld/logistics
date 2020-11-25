package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.async.AsyncMailTask;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.Md5Encoding;
import com.tiandi.logistics.utils.MinioUtil;
import com.tiandi.logistics.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;
import java.util.UUID;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/25 15:17
 */
@RestController
@RequestMapping("/auth")
public class UserRegisterController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AsyncMailTask mailTask;
    @Autowired
    private UserService userService;

    /**
     * 注册方法，此处全套不进行实体内部的值的判断
     * 所有校验交由前端进行处理！
     * <p>
     * **此注册方法为使用默认头像进行注册的方法接口
     * <p>
     * 数据库中允许为空的字段：
     * icon(用户头像，提供默认头像)
     * pet_name(用户昵称，为空则提供随机生成)
     * phone(直接null值即可)
     * [email,phone](两者取其一为空，涉及到用户激活处理) => 此处暂不能实现phone的相关激活功能实现
     * 其余字段：
     * username
     * password
     * pc_role
     * 均不可为空
     * 用户管理权限核心由管理员分配，注册只允许注册普通用户权限！
     * =>数据库默认值，同时后端处理也会强制修改为用户权限对应值
     *
     * @param userStr User对应的JSON字符串
     * @return 注册提示信息
     */
    @PostMapping("/register")
    public ResultMap register(@RequestParam("user") String userStr, @RequestParam(value = "icon", required = false) MultipartFile file) {
        //判空，防止抛出异常
        if (userStr == null || "".equals(userStr)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        //JSON转换对象
        User user = JSON.parseObject(userStr, User.class);
        //强制配置权限
        user.setRole(1);
        //默认用户昵称
        if (user.getPetname() == null || "".equals(user.getPetname())) {
            user.setPetname("TP用户" + new Random().nextInt(1000));
        }
        //UUID为键，存储在Redis中，供激活使用
        String userUUID = UUID.randomUUID().toString();
        user.setActiveuuid(userUUID);
        //密码加密处理
        String salt = Md5Encoding.md5RandomSaltGenerate();
        user.setSalt(salt);
        user.setPassword(Md5Encoding.md5RanSaltEncode(user.getPassword(), salt));
        System.out.println(user);
        boolean insert = false;
        //判断是否有用户头像的输入
        if (file != null) {
            user.setIcon(MinioUtil.getInstance().upLoadMultipartFile(file));
            //插入操作
            insert = userService.save(user);
        } else {
            //插入操作
            insert = userService.save(user);
        }
        //注册成功后的激活邮箱发送逻辑
        if (insert) {
            System.out.println(user.getIdTbUser());
            //Reds保存(UUID，userID)键值对
            redisUtil.set(userUUID, user.getIdTbUser(), 60 * 60 * 24);
            //异步提交激活邮件发送请求
            mailTask.activeMailTask(userUUID,user.getEmail());
            return resultMap.success().message("注册成功！请稍后前往邮箱进行激活！");
        }
        //所有方法均为匹配，特殊处理返回
        return resultMap.fail().code(40010).message("服务器内部错误");
    }

    /**
     * 表单校验支持接口 —— username
     *
     * @param username 用户名
     * @return 是否可用 => 200 可用 => 201 不可用
     */
    @GetMapping("/verifyUsername/{username}")
    public ResultMap verifyUsername(@PathVariable String username) {
        if (userService.count(new QueryWrapper<User>().eq("username", username)) < 1) {
            return resultMap.success().message("可用");
        }
        return resultMap.success().code(201).message("已被使用");
    }

    /**
     * 表单校验支持接口 —— email
     *
     * @param email 用户名
     * @return 是否可用 => 200 可用 => 201 不可用
     */
    @GetMapping("/verifyEmail/{email}")
    public ResultMap verifyEmail(@PathVariable String email) {
        if (userService.count(new QueryWrapper<User>().eq("email", email)) < 1) {
            return resultMap.success().message("可用");
        }
        return resultMap.success().code(201).message("已被使用");
    }

    /**
     * 表单校验支持接口 —— phone
     *
     * @param phone 用户名
     * @return 是否可用 => 200 可用 => 201 不可用
     */
    @GetMapping("/verifyPhone/{phone}")
    public ResultMap verifyPhone(@PathVariable String phone) {
        if (userService.count(new QueryWrapper<User>().eq("phone", phone)) < 1) {
            return resultMap.success().message("可用");
        }
        return resultMap.success().code(201).message("已被使用");
    }
}
