package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.Distribution;
import com.tiandi.logistics.mapper.DistributionMapper;
import com.tiandi.logistics.service.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 配送点管理 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class DistributionServiceImpl extends ServiceImpl<DistributionMapper, Distribution> implements DistributionService {
    @Autowired
    private DistributionMapper distributionMapper;
    @Override
    public int updatedistribution(Distribution distribution) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id_distribution",distribution.getIdDistribution());
        int update = distributionMapper.update(distribution, queryWrapper);
        return update;
    }
}
