package com.tiandi.logistics.config.mail.impl;

import com.tiandi.logistics.config.mail.MailService;
import com.tiandi.logistics.entity.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

/**
 * 邮箱接口实现类
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/25 15:43
 */
@Slf4j
@Component
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送邮箱准备接口
     * @param context 邮箱内部数据
     * @param sendTo 发送对象
     * @return
     */
    @Override
    public Mail prepareMail(Context context, String sendTo) {
        String emailContent = templateEngine.process("senderTemplate", context);
        Mail mail = new Mail();
        mail.setFrom("thulium0601@163.com");
        mail.setTo(sendTo);
        mail.setSubject("Please active account click under emial...");
        mail.setMailContent(emailContent);
        return mail;
    }

    /**
     * 发送邮件核心方法
     *
     * @param mail 发送邮件需要的基本参数，具体参照Mail定义
     */
    @Override
    public void sendActiveMail(@NotNull Mail mail) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mail.getFrom());
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getMailContent(), true);
            javaMailSender.send(message);
            log.info("激活验证邮件发送成功...");
        } catch (MessagingException e) {
            log.error("验证邮件发送失败...", e);
        }
    }
}
