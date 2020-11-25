package com.tiandi.logistics.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * Md5加密类，用于实现密码加密
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:49
 */
public final class Md5Encoding {

    private final static String salt="1x2j3x4y5w6t";

    /**
     * md5加盐加密算法
     * 固定盐值
     * @param password 密码
     * @return 加密后字符串
     */
    public static String md5FixSaltEncode(String password) {
        String str = "" + salt.charAt(3) + salt + salt.charAt(7) + password + salt.charAt(9);
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String md5RandomSaltGenerate() {
        return UUID.randomUUID().toString();
    }

    /**
     * 随机盐加密算法
     *
     * @param password 密码
     * @param salt 盐值
     * @return 加密后字符串
     */
    public static String md5RanSaltEncode(String password,String salt) {
        return DigestUtils.md5DigestAsHex((password + salt).getBytes());
    }
}
