package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.front.AddHandover;
import com.tiandi.logistics.entity.pojo.HandoverSlip;
import com.tiandi.logistics.mapper.HandoverSlipMapper;
import com.tiandi.logistics.service.HandoverSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 交接单管理 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class HandoverSlipServiceImpl extends ServiceImpl<HandoverSlipMapper, HandoverSlip> implements HandoverSlipService {

    @Autowired
    private HandoverSlipMapper handoverSlipMapper;

    @Override
    public List<HandoverSlip> getHandover(String name_company) {
        List<HandoverSlip> handover = handoverSlipMapper.getHandover(name_company);
        return handover;
    }

    @Override
    public List<HandoverSlip> getHandoverByID(String name_distribution) {
        List<HandoverSlip> handoverByID = handoverSlipMapper.getHandoverByID(name_distribution);
        return handoverByID;
    }

    @Override
    public int updateHandover(HandoverSlip handoverSlip) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id_order",handoverSlip.getIdHandoverOrder());
        int update = handoverSlipMapper.update(handoverSlip, queryWrapper);
        return update;
    }


    @Override
    public int deleteHandover(String id_handover_order) {
        int i = handoverSlipMapper.deleteHandover(id_handover_order);
        return i;
    }
}
