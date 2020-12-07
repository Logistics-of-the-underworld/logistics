package com.tiandi.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiandi.logistics.entity.front.VehicleResult;
import com.tiandi.logistics.entity.pojo.Vehicle;

import java.util.List;

/**
 * <p>
 * 车辆管理 Mapper 接口
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface VehicleMapper extends BaseMapper<Vehicle> {

    /**
     * 根据组织名获取车辆
     *
     * @param org 组织名
     * @param page 分页
     * @return
     */
    Page<VehicleResult> getVehicleByOrg(Page<?> page, String org);

}
