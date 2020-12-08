package com.tiandi.logistics;

import com.tiandi.logistics.config.ServerConfig;
import com.tiandi.logistics.config.mail.MailService;
import com.tiandi.logistics.entity.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class LogisticsApplicationTests {

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private MailService mailService;

    @Test
    public void activeMailTask() {
        String sendTo = "17184030601@163.com";
        //邮箱验证发送整体逻辑
        Context context = new Context();
        context.setVariable("company", "TP物流");
        context.setVariable("userId", "adnausfuhusnubdfubasubfbsud");
        context.setVariable("url", serverConfig.getUrl());
        context.setVariable("name", "张涛涛");
        context.setVariable("email",sendTo);
        context.setVariable("createTime", LocalDateTime.now().plusDays(7).toString());
        Mail mail = mailService.prepareMail(context, sendTo);
        log.info("发送往"+ sendTo +"的邮件正在发送=======>");
        mailService.sendActiveMail(mail);
        log.info("发送往"+ sendTo +"的邮件--已送达--");
    }

}
