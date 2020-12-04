package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.DistributionRange;
import com.tiandi.logistics.mapper.DistributionRangeMapper;
import com.tiandi.logistics.service.DistributionRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配送范围管理 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class DistributionRangeServiceImpl extends ServiceImpl<DistributionRangeMapper, DistributionRange> implements DistributionRangeService {

    @Autowired
    private DistributionRangeMapper distributionRangeMapper;

    @Override
    public int updatedistributionRange(DistributionRange distributionRange){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id_range",distributionRange.getIdDistribution());
        int update = distributionRangeMapper.update(distributionRange, queryWrapper);
        return update;
    }
}
