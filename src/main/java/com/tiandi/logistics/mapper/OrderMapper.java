package com.tiandi.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiandi.logistics.entity.front.LineInfoFront;
import com.tiandi.logistics.entity.front.PutOrder;
import com.tiandi.logistics.entity.pojo.Order;

import java.util.List;

/**
 * <p>
 * 订单管理 Mapper 接口
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface OrderMapper extends BaseMapper<Order> {
    int CreateOrder(PutOrder putOrder);
}
