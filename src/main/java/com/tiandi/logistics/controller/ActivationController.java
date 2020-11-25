package com.tiandi.logistics.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tiandi.logistics.async.AsyncMailTask;
import com.tiandi.logistics.config.ServerConfig;
import com.tiandi.logistics.config.mail.MailService;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.JWTUtil;
import com.tiandi.logistics.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 账户激活相关接口，包含激活以及重新发送激活码方法
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/25 17:06
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class ActivationController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private UserService userService;
    @Autowired
    private AsyncMailTask mailTask;

    /**
     * 激活后返回的界面地址
     * 与配置文件中的挂钩，方便进行动态修改
     */
    @Value("${font.url}")
    private String url;

    /**
     * 激活接口，激活后跳转到制定的登录界面
     *
     * @param response 响应
     * @param userID 用户主键
     * @return
     */
    @GetMapping("/activation/{userID}")
    public ResultMap activation(HttpServletResponse response, @PathVariable("userID") String userID) {
        boolean exist = redisUtil.hasKey(userID);
        if (!exist) {
            return resultMap.fail().message("验证码已过期，请重新登录获取激活邮件");
        } else {
            Integer pc_id = (Integer) redisUtil.get(userID);
            if (userService.update(new UpdateWrapper<User>().set("ban",0).eq("id_tb_user",pc_id))) {
                redisUtil.del(userID);
                //重定向到登录页面
                try {
                    System.out.println(url);
                    response.sendRedirect(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                return resultMap.fail().message("服务器内部错误");
            }
        }
    }

    /**
     * 预留接口，二次登录后重新发送激活邮件
     * @return
     */
    @PostMapping("/activation/resend")
    public ResultMap reSend() {
        return null;
    }

    /**
     * 激活时选择改变所绑定邮箱
     *      待完善开发接口
     *
     * @param username 用户名
     * @param email 邮箱
     * @return
     */
    @PostMapping("/activation/changeEmail")
    public ResultMap changeEmail(@RequestParam("username") String username, @RequestParam("email") String email) {
        User user = userService.getOne(new QueryWrapper<User>().select("id_tb_user", "activeUUID").eq("username",username));
        String uuid = user.getActiveuuid();
        Integer userID = user.getIdTbUser();
        //判断Redis中当前是否存在缓存
        if (!redisUtil.hasKey(uuid)) {
            redisUtil.set(uuid, userID, 60 * 60 * 24);
        }
        //修改数据库中的字段
        if (!userService.update(new UpdateWrapper<User>().set("email",email).eq("id_tb_user",userID))) {
            return resultMap.fail().message("服务器内部错误");
        }
        //异步提交激活邮件发送请求
        mailTask.activeMailTask(uuid,email);
        return resultMap.success().message("修改成功！请稍后前往邮箱进行激活！");
    }
}
