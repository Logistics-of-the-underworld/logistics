package com.tiandi.logistics.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Shiro继承JWT依赖类
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:42
 */
public class JWTToken implements AuthenticationToken {

    private static final long serialVersionUID = 2852380064996424434L;

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
