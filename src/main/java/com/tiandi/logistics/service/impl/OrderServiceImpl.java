package com.tiandi.logistics.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.Order;
import com.tiandi.logistics.mapper.OrderMapper;
import com.tiandi.logistics.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public IPage getAllOrder(int page, int limit, String receiver_address, Integer id_distribution, String receiver_name, Integer state_order) {
        IPage<Order> OrderPage = new Page<>(page, limit);
        final QueryWrapper condition = new QueryWrapper();
        if (receiver_address != null){
            condition.eq("receiver_address",receiver_address);
        }
        if (id_distribution != null){
            condition.eq("id_distribution",id_distribution);
        }
        if (receiver_name != null){
            condition.eq("receiver_name",receiver_name);
        }
        if (state_order != null){
            condition.eq("state_order",state_order);
        }
        final IPage<Order> orderIPage = orderMapper.selectPage(OrderPage, condition);
        return OrderPage;
    }

    @Override
    public int addOrder(Order order) {
        int insert = orderMapper.insert(order);
        return insert;
    }

    @Override
    public int updateOrder(Order order) {
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_order",order.getIdOrder());
        int update = orderMapper.update(order, condition);
        return update;
    }

    @Override
    public int deleteOrder(String id_order) {
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_order",id_order);
        int delete = orderMapper.delete(condition);
        return delete;
    }

    @Override
    public List<Order> getStateOrder() {
        List<Order> roadsList = orderMapper.getStateOrder();
        return roadsList;
    }


}
