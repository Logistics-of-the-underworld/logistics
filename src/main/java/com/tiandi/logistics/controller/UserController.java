package com.tiandi.logistics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:59
 */
@RestController
public class UserController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
