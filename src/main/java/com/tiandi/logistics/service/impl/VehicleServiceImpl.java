package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.front.VehicleResult;
import com.tiandi.logistics.entity.pojo.Distribution;
import com.tiandi.logistics.entity.pojo.Vehicle;
import com.tiandi.logistics.mapper.VehicleMapper;
import com.tiandi.logistics.service.DistributionService;
import com.tiandi.logistics.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 车辆管理 服务实现类
 * </p>
 *
 * @author Feng Chen
 * @since 2020-11-25
 */
@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements VehicleService {
    /**
     * 注入依赖
     */
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private DistributionService distributionService;

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
    @Override
    public IPage getAllVehicle(String page, String limit, String id_license, String type, String create_time, String state, String org) {
        //创建分页对象
        IPage<Vehicle> VehiclePage = new Page<>(Long.parseLong(page), Long.parseLong(limit));

        Integer idDistribution = distributionService.getOne(new QueryWrapper<Distribution>().select("id_distribution")
                .eq("name_distribution", org)).getIdDistribution();

        final QueryWrapper condition = new QueryWrapper<>();

        condition.eq("id_distribution", idDistribution);

        //判断查询条件是否为空
        if (id_license != null && !"undefined".equals(id_license) && id_license.length() > 1) {
            condition.eq("id_license", id_license);
        }
        if (type != null && !"undefined".equals(type) && type.length() > 1) {
            condition.eq("type", type);
        }
        if (create_time != null && !"undefined".equals(create_time)) {
            condition.eq("create_time", create_time);
        }
        if (state != null && !"undefined".equals(state) && state.length() > 0) {
            condition.eq("state", state);
        }
        //根据条件查询出车辆列表信息并分页
        vehicleMapper.selectPage(VehiclePage, condition);
        //返回分页的信息
        return VehiclePage;
    }

    /**
     * 添加车辆功能的服务实现类
     *
     * @param vehicle 车辆实体类
     * @return
     */
    @Override
    public int addVehicle(Vehicle vehicle) {
        int insert = vehicleMapper.insert(vehicle);
        return insert;
    }

    /**
     * 修改车辆功能的服务实现类
     *
     * @param vehicle 车辆实体类
     * @return
     */
    @Override
    public int updateVehicle(Vehicle vehicle) {
        //先按条件进行查询到要修改的车辆
        QueryWrapper condition = new QueryWrapper<>();
        //判断条件是什么
        condition.eq("id_license", vehicle.getIdLicense());
        int update = vehicleMapper.update(vehicle, condition);
        //修改车辆信息
        return update;
    }

    /**
     * 删除车辆的服务实现类
     *
     * @param idLicense
     * @return
     */
    @Override
    public int deleteVehicle(String idLicense) {
        //创建查询对象
        QueryWrapper condition = new QueryWrapper<>();
        //匹配对象中条件相等
        condition.eq("id_license", idLicense);
        //一行受影响，删除对象
        int delete = vehicleMapper.delete(condition);
        return delete;
    }

    @Override
    public Page<VehicleResult> getVehicleByOrg(Long current, Long size, String org) {
        Page<VehicleResult> resultPage = new Page<>();
        vehicleMapper.getVehicleByOrg(resultPage, org);
        return resultPage;
    }


}
