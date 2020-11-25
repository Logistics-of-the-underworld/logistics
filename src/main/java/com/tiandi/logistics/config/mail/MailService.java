package com.tiandi.logistics.config.mail;

import com.tiandi.logistics.entity.mail.Mail;
import org.thymeleaf.context.Context;

import javax.validation.constraints.NotNull;

/**
 * 邮箱业务支持接口
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/25 15:31
 */
public interface MailService {
    Mail prepareMail(Context context, String sendTo);

    /**
     * 发送激活邮件验证
     *
     * @param mail 发送邮件需要的基本参数，具体参照Mail定义
     */
    void sendActiveMail(@NotNull Mail mail);
}
