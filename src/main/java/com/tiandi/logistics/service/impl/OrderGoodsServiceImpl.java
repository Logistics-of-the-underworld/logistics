package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.OrderGoods;
import com.tiandi.logistics.mapper.OrderGoodsMapper;
import com.tiandi.logistics.service.OrderGoodsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 货物分表 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements OrderGoodsService {

}
