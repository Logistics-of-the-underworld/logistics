package com.tiandi.logistics.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.front.PutOrder;
import com.tiandi.logistics.entity.pojo.Order;
import com.tiandi.logistics.entity.pojo.OrderGoods;
import com.tiandi.logistics.mapper.OrderGoodsMapper;
import com.tiandi.logistics.mapper.OrderMapper;
import com.tiandi.logistics.service.OrderService;
import com.tiandi.logistics.utils.BarCodeUtil;
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
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;
    @Autowired
    private BarCodeUtil barCodeUtil;

    @Override
    public IPage getAllOrder(int page, int limit, String receiver_address, String senderAddress, String receiver_name, Integer state_order) {
        IPage<Order> OrderPage = new Page<>(page, limit);
        final QueryWrapper condition = new QueryWrapper();
        if (receiver_address != null){
            condition.eq("receiver_address",receiver_address);
        }
        if (senderAddress != null){
            condition.eq("sender_address",senderAddress);
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
    public IPage getAllOrder(int page, int limit, String receiverAddress, Integer importance, Integer stateOrder, String username) {
        IPage<Order> OrderPage = new Page<>(page, limit);
        final QueryWrapper condition = new QueryWrapper();
        if (receiverAddress != null && !"".equals(receiverAddress)){
            condition.eq("receiver_address",receiverAddress);
        }
        if (importance != null && !"".equals(importance)){
            condition.eq("importance",importance);
        }
        if (stateOrder != null && !"".equals(stateOrder)){
            condition.eq("state_order",stateOrder);
        }
        condition.eq("username",username);
        final IPage<Order> orderIPage = orderMapper.selectPage(OrderPage, condition);
        return orderIPage;
    }

    @Override
    public int addOrder(PutOrder order) {
        int insert = orderMapper.CreateOrder(order);
        return insert;
    }

    @Override
    public int updateOrder(Order order){
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_order",order.getIdOrder());
        int update = orderMapper.update(order, condition);
        return update;
    }

    @Override
    public int updateOrder(Order order, Double heavy) {
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_order",order.getIdOrder());
        if (order.getStateOrder() <= 1 && order.getIdBarCode() != null){
            OrderGoods orderGoods = orderGoodsMapper.selectOne(condition);
            String id_bar_code = ""+order.getIdDistribution()+order.getIdOrder()+orderGoods.getIdSortGoods();
            String barCodeUrl = barCodeUtil.generateBarCode128(id_bar_code);
            order.setBarCodeUrl(barCodeUrl);
            order.setIdBarCode(id_bar_code);
            if (heavy != null ){
                orderGoods.setHeavy(heavy);
                orderGoodsMapper.update(orderGoods,condition);
            }
        }
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
        return null;
    }


//    @Override
//    public List<Order> getStateOrder() {
//        List<Order> roadsList = orderMapper.getStateOrder();
//        return roadsList;
//    }


    @Override
    public OrderGoods getOrderById(String idOrder) {
        OrderGoods orderGoods=orderMapper.getOrderById(idOrder);
        return orderGoods;
    }

}
