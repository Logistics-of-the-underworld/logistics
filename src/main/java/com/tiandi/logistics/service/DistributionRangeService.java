package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.DistributionRange;

/**
 * <p>
 * 配送范围管理 服务类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface DistributionRangeService extends IService<DistributionRange> {

    int updatedistributionRange(DistributionRange distributionRange);

}
