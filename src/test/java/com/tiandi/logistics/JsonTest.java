package com.tiandi.logistics;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.constant.SystemConstant;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.RedisUtil;
import com.tiandi.logistics.utils.SMSSendingUtil;
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
    public void test() throws Exception {
        SMSSendingUtil.sendOrderCode("BZX8111024", "第四个站点", "17184030601");
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
