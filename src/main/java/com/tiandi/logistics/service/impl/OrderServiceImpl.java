package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.Order;
import com.tiandi.logistics.mapper.OrderMapper;
import com.tiandi.logistics.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单管理 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
