package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.front.VehicleResult;
import com.tiandi.logistics.entity.pojo.Vehicle;
import com.tiandi.logistics.entity.result.ResultMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 车辆管理 服务类
 * </p>
 *
 * @author Feng Chen
 * @since 2020-11-25
 */
public interface VehicleService extends IService<Vehicle> {
    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @param id_license
     * @param type
     * @param create_time
     * @param state
     * @return
     */
    IPage getAllVehicle(String page, String limit, String id_license, String type, String create_time, String state, String org);

    /**
     * 添加车辆
     * 一行受影响
     *
     * @param vehicle 车辆实体类
     * @return
     */
    int addVehicle(Vehicle vehicle);

    /**
     * 修改车辆信息
     *
     * @param vehicle 车辆实体类
     * @return
     */
    int updateVehicle(Vehicle vehicle);

    /**
     * 根据车牌号删除用户
     *
     * @param idLicense
     * @return
     */
    int deleteVehicle(String idLicense);

    /**
     * 根据组织名获取车辆
     *
     * @param current 当前页数
     * @param size 页容量
     * @param org  组织名
     * @return
     */
    Page<VehicleResult> getVehicleByOrg(Long current, Long size, String org);
}
