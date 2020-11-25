package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.Vehicle;
import com.tiandi.logistics.mapper.VehicleMapper;
import com.tiandi.logistics.service.VehicleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆管理 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements VehicleService {

}
