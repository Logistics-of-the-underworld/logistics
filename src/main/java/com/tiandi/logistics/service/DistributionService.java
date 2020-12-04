package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.Distribution;

/**
 * <p>
 * 配送点管理 服务类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface DistributionService extends IService<Distribution> {

    int updatedistribution(Distribution distribution);
}
