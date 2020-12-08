package com.tiandi.logistics.config.start_init;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.constant.SystemConstant;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;
import com.tiandi.logistics.service.DeliveryPriceService;
import com.tiandi.logistics.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/8 16:49
 */
@Component
@Slf4j
public class PriceCacheConfig {

    @Autowired
    private DeliveryPriceService priceService;
    @Autowired
    private RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        log.info("配送价格开始缓存");
        DeliveryPrice price = priceService.getOne(new QueryWrapper<DeliveryPrice>().eq("id_delivery_price", "123"));
        redisUtil.set(SystemConstant.TP_PRICE, JSON.toJSON(price));
        log.info("配送价格缓存成功");
    }
}
