package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.pojo.DistributionRange;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.DistributionRangeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SiSong Li
 * @version 1.0
 * @since 2020年12月03日, 0003 15:17
 */
@RestController
@RequestMapping("/distributionRange")
@Api(tags = "配送范围管理")
public class DistributionRangeController {
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private DistributionRangeService distributionRangeService;

    @PostMapping("/getAllDistributionRange")
    @ControllerLogAnnotation(remark = "查询所有配送范围信息",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.SELECT)
    @ApiOperation(value = "获取配送范围信息接口",notes = "当前配送范围的配送范围编码、配送点编码、范围名称、创建时间、备注等信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name_company", value = "所属公司", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "role",value = "身份",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "permission",value = "权限",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "id_distribution",value = "配送点编码",paramType = "query",dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "配送范围信息查询成功！"),
            @ApiResponse(code = 50011, message = "配送范围信息查询失败，请重试！")
    })
    public ResultMap getAllDistributionRange(@RequestParam(value = "name_company",required = false) String name_company,
                                             @RequestParam("role") String role,
                                             @RequestParam String permission,
                                             @RequestParam(value = "id_distribution",required = false) String id_distribution){
        if (permission.equals("root") && role.equals("admin")){
            List<DistributionRange> distributionRanges = distributionRangeService.getBaseMapper().selectList(new QueryWrapper<DistributionRange>());
            String distributionRangesList = JSON.toJSONString(distributionRanges);
            resultMap.success().message("查询配送范围成功！").addElement("distributionRangesList",distributionRangesList);
        }else if (permission.equals("admin") && role.equals("distribution") && name_company != null){
            List<DistributionRange> distributionRanges = distributionRangeService.getBaseMapper().selectList(new QueryWrapper<DistributionRange>().eq("name_company", name_company));
            String distributionRangesList = JSON.toJSONString(distributionRanges);
            resultMap.success().message("查询配送范围成功！").addElement("distributionRangesList",distributionRangesList);
        }else if (permission.equals("admin") && role.equals("distribution") && id_distribution != null){
            List<DistributionRange> distributionRanges = distributionRangeService.getBaseMapper().selectList(new QueryWrapper<DistributionRange>().eq("id_distribution", id_distribution));
            String distributionRangesList = JSON.toJSONString(distributionRanges);
            resultMap.success().message("查询配送范围成功！").addElement("distributionRangesList",distributionRangesList);
        }else if (name_company == null && id_distribution == null){
            resultMap.fail().code(50011).message("查询配送范围失败!");
        }else {
            resultMap.fail().code(50011).message("查询配送范围失败!");
        }
        return resultMap;
    }

    @PostMapping("/updateDistributionRange")
    @ControllerLogAnnotation(remark = "配送范围更新功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "配送范围更新接口", notes = "根据配送范围编码更新路线信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id_range",value = "配送范围编码",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "distributionRange",value = "配送范围对象",required = true,paramType = "query",dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "配送范围更新成功！"),
            @ApiResponse(code = 50011, message = "配送范围更新失败，请重试！")
    })
    public ResultMap updateDistributionRange(@RequestParam("id_range") String id_range,
                                             @RequestParam("distributionRange") String distributionRange){
        if (distributionRange == null || "".equals(distributionRange)){
            resultMap.fail().code(40010).message("服务器内部错误!");
        }
        DistributionRange distributionRange1 = JSON.parseObject(distributionRange, DistributionRange.class);
        distributionRange1.setIdDistribution(Integer.valueOf(id_range));
        int i = distributionRangeService.updatedistributionRange(distributionRange1);
        if (i == 1){
            resultMap.success().message("配送范围更新成功！");
        }else {
            resultMap.fail().message("配送范围更新失败！");
        }
        return resultMap;
    }

    @GetMapping("/deleteDistributionRange")
    @ControllerLogAnnotation(remark = "配送范围删除功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.DELETE)
    @ApiOperation(value = "配送范围删除接口",notes = "根据配送范围编码删除配送范围")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id_range",value = "配送范围编码",required = true,dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "删除配送范围成功！"),
            @ApiResponse(code = 50011, message = "删除配送范围失败，请重试！")
    })
    public ResultMap deleteDistributionRange(@RequestParam("id_range") String id_range){
        if (id_range == null || "".equals(id_range)){
            return resultMap.fail().code(40010).message("服务器内部错误!");
        }
        int id_range1 = distributionRangeService.getBaseMapper().delete(new QueryWrapper<DistributionRange>().eq("id_range", id_range));
        if (id_range1 == 1){
            resultMap.success().message("删除成功！");
        }else {
            resultMap.fail().message("删除失败！");
        }
        return resultMap;
    }
}
