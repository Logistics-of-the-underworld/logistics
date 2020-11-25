package com.tiandi.logistics.entity.mail;

import lombok.Data;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/25 15:33
 */
@Data
public class Mail {
    /**
     * 邮件发送方
     */
    private String from;
    /**
     * 邮件接收方
     */
    private String to;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String mailContent;
}
