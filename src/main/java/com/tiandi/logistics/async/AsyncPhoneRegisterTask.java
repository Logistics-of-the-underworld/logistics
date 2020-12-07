package com.tiandi.logistics.async;

import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.Md5Encoding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * 第一次使用手机号登陆的用户，自动注册为平台用户
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/7 19:12
 */
@Component
@Slf4j
public class AsyncPhoneRegisterTask {

    @Autowired
    private UserService userService;

    @Async
    public void phoneRegister(String phone) {
        log.info("新手机用户注册异步任务开始");
        User user = new User();
        user.setUsername("P" + phone);
        user.setPhone(phone);
        CharSequence sequence = phone.subSequence(phone.length() - 6, phone.length());
        user.setSalt(UUID.randomUUID().toString());
        user.setPassword(Md5Encoding.md5RanSaltEncode(sequence.toString(), user.getSalt()));
        user.setRole(1);
        user.setBan(0);
        user.setPetname("TP用户" + new Random().nextInt(1000));
        boolean save = userService.save(user);

        if (save) {
            log.info(phone + "用户注册成功！");
        }
    }
}
