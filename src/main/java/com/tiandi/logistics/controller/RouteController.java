package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.front.LineInfoFront;
import com.tiandi.logistics.entity.pojo.LineInfo;
import com.tiandi.logistics.entity.pojo.Vehicle;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.LineInfoService;
import com.tiandi.logistics.service.VehicleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kotori
 * @version 1.0
 * @date 2020/12/2 09:22
 * @description
 */
@RestController
@RequestMapping("/route")
@Api(tags = "路线管理")
public class RouteController {
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private LineInfoService lineInfoService;

    @PostMapping("/getAllRoute")
    @ApiOperation(value = "路线获取接口",notes = "通过页码、页数、起始站点、终点站点、路线ID多条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页数", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "start_distribution", value = "起始站点ID", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "end_distribution", value = "终点站点ID", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "id_line", value = "路线ID", required = false, paramType = "query", dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "路线查询成功！"),
            @ApiResponse(code = 50011, message = "路线查询失败，请重试！")
    })
    public ResultMap getAllRoute(@RequestParam(value = "page", required = true) int page,
                                 @RequestParam(value = "limit", required = true) int limit,
                                 @RequestParam(value = "start_distribution", required = false) Integer start_distribution,
                                 @RequestParam(value = "end_distribution", required = false) Integer end_distribution,
                                 @RequestParam(value = "id_line", required = false) Integer id_line){
        final IPage allRoute = lineInfoService.getAllRoute(page, limit, start_distribution, end_distribution, id_line);
        resultMap.addElement("data",allRoute.getRecords());
        resultMap.addElement("total",allRoute.getTotal());
        resultMap.success();
        return resultMap;
    }

    @PostMapping("/addRoute")
    @ControllerLogAnnotation(remark = "路线添加功能",sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.ADD)
    @ApiOperation(value = "路线添加接口", notes = "添加一条路线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "driver_name",value = "负责司机",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "lineInfo",value = "路线对象",required = true,paramType = "query",dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "路线查询成功！"),
            @ApiResponse(code = 50011, message = "路线查询失败，请重试！")
    })
    public ResultMap addRoute(@RequestParam(value = "lineInfo", required = true) String lineInfoStr){
        //判空，防止抛出异常
        if (lineInfoStr == null || "".equals(lineInfoStr)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        LineInfo lineInfo = JSON.parseObject(lineInfoStr, LineInfo.class);
        int save = lineInfoService.addRoute(lineInfo);
        if (save == 1){
            resultMap.success().message("添加成功");
        } else {
            resultMap.fail().message("添加失败");
        }
        return resultMap;
    }

    @PostMapping("/updateRoute")
    @ControllerLogAnnotation(remark = "路线更新功能",sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "路线更新接口", notes = "根据路线ID更新路线信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "driver_name",value = "负责司机",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "lineInfo",value = "路线对象",required = true,paramType = "query",dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "路线查询成功！"),
            @ApiResponse(code = 50011, message = "路线查询失败，请重试！")
    })
    public ResultMap updateRoute(@RequestParam(value = "driver_name", required = false) String driver_name,
                              @RequestParam(value = "lineInfo", required = false) String lineInfoStr){
        //判空，防止抛出异常
        if (lineInfoStr == null || "".equals(lineInfoStr)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        LineInfo lineInfo = JSON.parseObject(lineInfoStr, LineInfo.class);
        int update = lineInfoService.updateRoute(lineInfo);
        if (update == 1){
            resultMap.success().message("更新成功");
        } else {
            resultMap.fail().message("更新失败");
        }

        return resultMap;
    }

    @GetMapping("/deleteRoute/{idLine}")
    @ControllerLogAnnotation(remark = "路线删除功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.DELETE)
    @ApiImplicitParam(name = "idLine",value = "路线ID",required = true,paramType = "query",dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "路线查询成功！"),
            @ApiResponse(code = 50011, message = "路线查询失败，请重试！")
    })
    public ResultMap deleteRoute(@PathVariable String idLine){
        if (idLine == null || "".equals(idLine)){
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        int delete = lineInfoService.deleteRoute(idLine);
        if (delete == 1){
            resultMap.success().message("删除成功");
        } else {
            resultMap.fail().message("删除失败");
        }
        return resultMap;
    }

    @GetMapping("/getRoadMap")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "路线查询成功！"),
            @ApiResponse(code = 50011, message = "路线查询失败，请重试！")
    })
    public ResultMap getRoadMap(){
        List<LineInfoFront> roadMap = lineInfoService.getRoadMap();
        return resultMap.addElement("data",roadMap).success();
    }
}
