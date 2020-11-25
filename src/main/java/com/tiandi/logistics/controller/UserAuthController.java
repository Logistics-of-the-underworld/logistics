package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.entity.pojo.Role;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.RoleService;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.JWTUtil;
import com.tiandi.logistics.utils.Md5Encoding;
import com.tiandi.logistics.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
public class UserAuthController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisUtil redisUtil;

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
                return resultMap.fail().message("账户未激活，请激活后进行登录!");
            }
            if (user.getIsDelete() == 1) {
                return resultMap.fail().message("账户已被封禁，请另行注册!");
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
            resultMap.success().message(token);
        } else {
            resultMap.fail().message("密码错误或账户不存在!");
        }

        return resultMap;
    }

    /**
     * 信息获取接口，前端动态授权支持接口
     *
     * @param token 凭证
     * @return
     */
    @GetMapping("/getInf")
    public ResultMap getInf(@RequestHeader String token) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", JWTUtil.getUsername(token));

        User user = userService.getOne(wrapper);

        String roleJSON = JWTUtil.getRoleJSON(token);
        String replace = roleJSON.replace("\"", "'");

        resultMap.success().addElement("username", user.getUsername()).addElement("email", user.getEmail())
                .addElement("phone", user.getPhone()).addElement("role", replace)
                .addElement("petName", user.getPetname());

        return resultMap;
    }
}
