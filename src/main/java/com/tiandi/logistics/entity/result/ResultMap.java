package com.tiandi.logistics.entity.result;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:59
 */
@Component
@RequestScope
public class ResultMap extends HashMap<String, Object> {

    public ResultMap() {
    }

    public ResultMap success() {
        this.put("status", "success");
        this.code(20000);
        return this;
    }

    public ResultMap fail() {
        this.put("status", "fail");
        return this;
    }

    public ResultMap code(int code) {
        this.put("code", code);
        return this;
    }

    public ResultMap message(Object message) {
        this.put("message", message);
        return this;
    }

    public ResultMap addElement(String key,Object message) {
        this.put(key, message);
        return this;
    }
}
