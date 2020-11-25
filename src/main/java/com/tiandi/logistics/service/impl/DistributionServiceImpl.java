package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.Distribution;
import com.tiandi.logistics.mapper.DistributionMapper;
import com.tiandi.logistics.service.DistributionService;
import org.springframework.stereotype.Service;

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

}
