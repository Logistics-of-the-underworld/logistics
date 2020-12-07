package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.Vehicle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * @param page
     * @param limit
     * @param id_license
     * @param type
     * @param create_time
     * @param state
     * @return
     */
    IPage getAllVehicle(int page, int limit, String id_license, String type, Date create_time, Integer state);

    /**
     * 添加车辆
     * 一行受影响
     * @param vehicle 车辆实体类
     * @return
     */
    int addVehicle(Vehicle vehicle);

    /**
     * 修改车辆信息
     * @param vehicle 车辆实体类
     * @return
     */
    int updateVehicle(Vehicle vehicle);

    /**
     * 根据车牌号删除用户
     * @param idLicense
     * @return
     */
    int deleteVehicle(String idLicense);
}
