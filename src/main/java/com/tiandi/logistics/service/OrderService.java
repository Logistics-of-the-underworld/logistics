package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.front.PutOrder;
import com.tiandi.logistics.entity.pojo.Order;
import com.tiandi.logistics.entity.pojo.OrderGoods;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 订单管理 服务类
 * </p>
 *
 * @author
 * @since 2020-11-25
 */
public interface OrderService extends IService<Order> {

    /**
     * 分页查询
     * @param page 页码
     * @param limit 页数
     * @param receiver_address 收件人地址
     * @param senderAddress 配送地
     * @param receiver_name 客户姓名
     * @param state_order 订单状态
     * @return
     */
    IPage getAllOrder(int page, int limit, String receiver_address, String senderAddress, String receiver_name, Integer state_order);

    /**
     * 用户的分页查询
     * @param page
     * @param limit
     * @param receiverAddress
     * @param importance
     * @param stateOrder
     * @return
     */
    IPage getAllOrder(int page, int limit, String receiverAddress, Integer importance, Integer stateOrder, String username);


    /**
     * 添加订单
     * @param order 订单实体类
     * @return
     */
    int addOrder(PutOrder order);


    /**
     * 更新订单
     * @param order 订单实体类
     * @return
     */
    int updateOrder(Order order);

    /**
     * 删除订单
     * @param id_order 订单实体类
     * @return
     */
    int deleteOrder(String id_order);

    /**
     * 确认订单
     * @return
     */
    List<Order> getStateOrder();

    /**
     * 通过订单ID获取订单
     * @return
     */
    OrderGoods getOrderById(String idOrder );
}

