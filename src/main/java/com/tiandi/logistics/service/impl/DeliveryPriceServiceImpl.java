package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;
import com.tiandi.logistics.entity.pojo.LineInfo;
import com.tiandi.logistics.mapper.DeliveryPriceMapper;
import com.tiandi.logistics.service.DeliveryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配送价格管理 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class DeliveryPriceServiceImpl extends ServiceImpl<DeliveryPriceMapper, DeliveryPrice> implements DeliveryPriceService {

    @Autowired
    private DeliveryPriceMapper deliveryPriceMapper;

    @Override
    public int updateDeliveryPrice(DeliveryPrice deliveryPrice) {
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_delivery_price",deliveryPrice.getIdDeliveryPrice());
        int update = deliveryPriceMapper.update(deliveryPrice, condition);
        return update;
    }
}
