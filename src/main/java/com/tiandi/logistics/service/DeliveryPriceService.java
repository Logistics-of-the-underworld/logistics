package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;

/**
 * <p>
 * 配送价格管理 服务类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface DeliveryPriceService extends IService<DeliveryPrice> {

    int updateDeliveryPrice(DeliveryPrice deliveryPrice);
}
