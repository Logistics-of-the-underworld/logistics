package com.tiandi.logistics.async;

import com.tiandi.logistics.config.ServerConfig;
import com.tiandi.logistics.config.mail.MailService;
import com.tiandi.logistics.entity.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

/**
 * 异步请求 —— 邮件发送异步类
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/25 16:54
 */
@Component
@Slf4j
public class AsyncMailTask {

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private MailService mailService;

    /**
     * 激活邮件异步请求方法体
     *
     * @param uuid   标识号
     * @param sendTo 发送目标邮箱
     */
    @Async
    public void activeMailTask(String uuid, String sendTo, String org, String name) {
        //邮箱验证发送整体逻辑
        Context context = new Context();
        context.setVariable("company", org);
        context.setVariable("userId", uuid);
        context.setVariable("url", serverConfig.getUrl());
        context.setVariable("name", name);
        context.setVariable("email", sendTo);
        context.setVariable("createTime", LocalDateTime.now().plusDays(7).toString());
        Mail mail = mailService.prepareMail(context, sendTo);
        log.info("发送往" + sendTo + "的邮件正在发送=======>");
        mailService.sendActiveMail(mail);
        log.info("发送往" + sendTo + "的邮件--已送达--");
    }
}
