package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.front.VehicleResult;
import com.tiandi.logistics.entity.pojo.Distribution;
import com.tiandi.logistics.entity.pojo.Vehicle;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.DistributionService;
import com.tiandi.logistics.service.VehicleService;
import com.tiandi.logistics.utils.JWTUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Feng Chen
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/2 14:56
 */
@RestController
@RequestMapping("/Vehicle_auth")
@Api(tags = "车辆管理")
public class VehicleController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DistributionService distributionService;

    @GetMapping("/getVehicleByOrg/{org}/{current}/{size}")
    @ApiOperation(value = "根据组织获取车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "org", value = "组织名", required = true, paramType = "path"),
            @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "页容量", required = true, paramType = "path")
    })
    public ResultMap getVehicleByOrg(@PathVariable String org,
                                     @PathVariable String current,
                                     @PathVariable String size) {

        Page<VehicleResult> page = vehicleService.getVehicleByOrg(Long.parseLong(current), Long.parseLong(size), org);

        return resultMap.success().message("获取对应的" + org + "所属的车辆").addElement("vehicleList", page.getRecords()).addElement("total", page.getTotal())
                .addElement("totalPageNumber", page.getTotal() / Long.parseLong(size));
    }


    //查询所有的车辆信息
    @PostMapping("/getAllVehicle")
    @ApiOperation(value = "车辆获取接口", notes = "根据车牌号、购买日期、车辆型号、车辆状态多条件进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id_license", value = "车牌号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "车辆型号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "create_time", value = "购置时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "state", value = "车辆状态", required = false, paramType = "query", dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "车辆查询成功"),
            @ApiResponse(code = 50010, message = "查询失败请重试")
    })
    public ResultMap getAllVehicle(@RequestParam(value = "page", required = true) String page,
                                   @RequestParam(value = "limit", required = true) String limit,
                                   @RequestParam(value = "id_license", required = false) String id_license,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "create_time", required = false) String create_time,
                                   @RequestParam(value = "state", required = false) String state,
                                   @RequestParam(value = "org") String org
    ) {
        //调用vehicleService中的方法
        final IPage allVehicle = vehicleService.getAllVehicle(page, limit, id_license, type, create_time, state, org);
        //调用resultMap中的addElement方法
        resultMap.addElement("date", allVehicle.getRecords());
        resultMap.addElement("total", allVehicle.getTotal());
        resultMap.success().message("查询车辆信息成功");

        return resultMap;
    }

    /**
     * 添加车辆功能
     *
     * @param vehicle
     * @return
     */
    @PostMapping("/addVehicle")
    @ControllerLogAnnotation(remark = "新增车辆功能", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.ADD)
    @ApiOperation(value = "车辆添加接口", notes = "根据车辆对象添加车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vehicle", value = "车辆实体", required = true, paramType = "query", dataType = "String")

    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "车辆查询成功！"),
            @ApiResponse(code = 50011, message = "车辆查询失败，请重试！")
    })
    public ResultMap addVehicle(
            @RequestHeader String token,
            @RequestParam(value = "vehicle", required = false) String vehicle,
            @RequestParam(value = "org") String org) {
        //判断前端输入信息是否正确
        if (vehicle == null || "".equals(vehicle)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        //将前端传过的参数反编译成java文件
        Vehicle vehicle1 = JSON.parseObject(vehicle, Vehicle.class);

        Integer idDistribution = distributionService.getOne(new QueryWrapper<Distribution>().select("id_distribution")
                .eq("name_distribution", org)).getIdDistribution();

        vehicle1.setIdDistribution(idDistribution.toString());

        boolean save = vehicleService.save(vehicle1);
        if (save) {
            resultMap.success().message(JWTUtil.getUsername(token) + "添加" + vehicle1.getIdLicense() +"车辆成功");
        } else {
            resultMap.fail().message("添加车辆失败");
        }

        return resultMap;
    }

    /**
     * 修改车辆信息
     *
     * @param vehicle
     * @return
     */
    @PostMapping("/updateVehicle")
    @ControllerLogAnnotation(remark = "修改车辆功能", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "车辆修改接口", notes = "根据车辆对象修改车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vehicle", value = "车辆实体", required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400000, message = "车辆查询成功"),
            @ApiResponse(code = 500010, message = "车辆查询失败")
    })
    public ResultMap updateVehicle(@RequestParam String vehicle) {
        if (vehicle == null || "".equals(vehicle)) {
            resultMap.fail().code(400011).message("服务器内部错误");
        }
        //将前端获取的参数信息转换成java对象
        Vehicle vehicle1 = JSON.parseObject(vehicle, Vehicle.class);
        //调用mapper层的update方法
        int update = vehicleService.updateVehicle(vehicle1);
        //判断什么情况下修改成功什么情况下修改失败
        if (update == 1) {
            resultMap.success().message("修改车辆数据成功");
        } else {
            resultMap.fail().message("修改车辆数据失败");
        }
        return resultMap;
    }

    @PostMapping("/deleteVehicle")
    @ControllerLogAnnotation(remark = "删除车辆功能", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.DELETE)
    @ApiOperation(value = "车辆删除接口", notes = "根据车牌号删除车辆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idLicense", value = "车牌号", required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200000, message = "车辆删除成功"),
            @ApiResponse(code = 500010, message = "车辆删除失败")
    })
    public ResultMap deleteVehicle(@RequestParam("idLicense") String idLicense) {
        //防止抛异常
        if (idLicense == null || "".equals(idLicense)) {
            resultMap.fail().code(400011).message("服务器内部错误");
        }
        int delete = vehicleService.deleteVehicle(idLicense);
        if (delete == 1) {
            resultMap.success().message("删除车辆成功");
        } else {
            resultMap.fail().message("删除车辆失败");
        }
        return resultMap;
    }
}
