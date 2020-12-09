package com.tiandi.logistics;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.constant.SystemConstant;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonTest {

    //    @Autowired
//    UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test() {
//        User user = userService.getOne(new QueryWrapper<User>().eq("username", "root"));
//
//        String string = JSON.toJSONString(user);
//
//        User user2 = JSON.parseObject(string, User.class);

        String json = "{\"createTime\":1606437629000,\"email\":\"135119707019@qq.com\",\"icon\":\"http://182.92.208.18:9000/project/20201127/888f47b0-3604-4d6b-b3ec-748cd1212090\",\"idTbUser\":1,\"isDelete\":0,\"password\":\"cbad7321374d3c708b79c2c98c26ce98\",\"petname\":\"TP用户793\",\"phone\":\"14789632147\",\"role\":1,\"salt\":\"654e67b3-a5a7-4f78-89d4-65ac676ad731\",\"updateTime\":1606437629000,\"username\":\"root\"}";

//        System.out.println(string);
        System.out.println(JSON.parseObject(json, User.class));
    }

    @Test
    public void test2() {
        System.out.println(price("0.9"));
    }

    public String price(String weightStr) {

        double weight = Double.parseDouble(weightStr);

        String o = redisUtil.get(SystemConstant.TP_PRICE).toString();

        DeliveryPrice price1 = JSON.parseObject(o, DeliveryPrice.class);

        int first = (int) Math.ceil(price1.getFirstKilogram());
        int second = (int) Math.ceil(price1.getSecondKilogram());

        int w = (int) Math.ceil(weight);
        int price = first + second * (w - 1);
        return String.valueOf(price);
    }
}
