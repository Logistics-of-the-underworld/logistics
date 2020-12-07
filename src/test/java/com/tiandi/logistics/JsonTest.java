package com.tiandi.logistics;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class JsonTest {

//    @Autowired
//    UserService userService;

    @Test
    public void test() {
//        User user = userService.getOne(new QueryWrapper<User>().eq("username", "root"));
//
//        String string = JSON.toJSONString(user);
//
//        User user2 = JSON.parseObject(string, User.class);

        String json = "{\"createTime\":1606437629000,\"email\":\"135119707019@qq.com\",\"icon\":\"http://182.92.208.18:9000/project/20201127/888f47b0-3604-4d6b-b3ec-748cd1212090\",\"idTbUser\":1,\"isDelete\":0,\"password\":\"cbad7321374d3c708b79c2c98c26ce98\",\"petname\":\"TP用户793\",\"phone\":\"14789632147\",\"role\":1,\"salt\":\"654e67b3-a5a7-4f78-89d4-65ac676ad731\",\"updateTime\":1606437629000,\"username\":\"root\"}";

//        System.out.println(string);
        System.out.println(JSON.parseObject(json,User.class));
    }
}
