package com.tiandi.logistics.utils;

import com.alibaba.fastjson.JSON;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 价格计算工具类
 * <p>
 * 价格计算逻辑备忘：
 * 物品快递费用分为两种计算方法，为避免密度过低物品的存在
 * 两种计费方式取最高值进行计算
 * <p>
 * 两种衡量数据说明：
 * 货物重量：货物的真实重量
 * 体积重量：反应包裹密度的一种方式
 * 固定公式：长度(cm) x 宽度(cm) x 高度(cm) ÷ 6,000
 * <p>
 * 首重及续重说明：
 * 首重： 0.5KG以内均按照首重计算
 * 续重: 每增加0.5KG为一个续重，不足则向上取整
 * <p>
 * 价格说明：
 * 首重：10元
 * 续重：4元
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/8 16:22
 */
@Component
public class LogisticsPriseCountUntil {

    public BigDecimal price() {
        return null;
    }

}
