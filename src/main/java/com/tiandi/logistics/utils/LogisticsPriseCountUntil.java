package com.tiandi.logistics.utils;

import com.alibaba.fastjson.JSON;
import com.tiandi.logistics.constant.SystemConstant;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 价格计算工具类
 * <p>
 * 价格计算逻辑备忘：<br>
 * 物品快递费用分为两种计算方法，为避免密度过低物品的存在<br>
 * 两种计费方式取最高值进行计算<br>
 * <p>
 * 两种衡量数据说明：<br>
 * 货物重量：货物的真实重量<br>
 * 体积重量：反应包裹密度的一种方式<br>
 * 固定公式：长度(cm) x 宽度(cm) x 高度(cm) ÷ 6,000<br>
 * <p>
 * 首重及续重说明：<br>
 * 首重： 1KG以内均按照首重计算<br>
 * 续重: 每增加1KG为一个续重，不足则向上取整<br>
 * <p>
 * 价格说明：<br>
 * 首重：10元<br>
 * 续重：4元<br>
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/8 16:22
 */
@Component
public final class LogisticsPriseCountUntil {

    @Autowired
    private RedisUtil redisUtil;

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
