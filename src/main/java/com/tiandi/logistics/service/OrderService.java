package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.LineInfo;
import com.tiandi.logistics.entity.pojo.Order;

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
     * @param id_distribution 配送地ID
     * @param receiver_name 客户姓名
     * @param state_order 订单状态
     * @return
     */
    IPage getAllOrder(int page, int limit, String receiver_address, Integer id_distribution, String receiver_name, Integer state_order);


    /**
     * 添加订单
     * @param order 订单实体类
     * @return
     */
    int addOrder(Order order);


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
}

