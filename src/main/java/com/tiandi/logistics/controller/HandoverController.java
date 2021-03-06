package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.front.AddHandover;
import com.tiandi.logistics.entity.pojo.Distribution;
import com.tiandi.logistics.entity.pojo.HandoverPackage;
import com.tiandi.logistics.entity.pojo.HandoverSlip;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.DistributionService;
import com.tiandi.logistics.service.HandoverPackageService;
import com.tiandi.logistics.service.HandoverSlipService;
import com.tiandi.logistics.utils.JWTUtil;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author SiSong Li
 * @version 1.0
 * @since 2020年12月07日, 0007 08:55
 */
@RestController
@RequestMapping("/handOver")
@Api(tags = "交接单管理")
public class HandoverController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private HandoverSlipService handoverSlipService;
    @Autowired
    private HandoverPackageService handoverPackageService;

    @GetMapping("/getAllHandover/{name_distribution}")
    @ApiOperation(value = "交接单信息接口",notes = "根据身份权限获取交接单的具体信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nameDistribution", value = "所属公司", paramType = "query", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "交接单信息查询成功！"),
            @ApiResponse(code = 50011, message = "交接单信息查询失败，请重试！")
    })
    public ResultMap getAllHandover(@RequestHeader String token,@PathVariable(value = "name_distribution",required = false) String name_distribution){
        //获取身份权限
        String permission = JWTUtil.getUserPermission(token);
        String role = JWTUtil.getUserRole(token);
        //总公司管理员
        if ("root".equals(permission) && "admin".equals(role)){
            List<HandoverSlip> handoverSlips = handoverSlipService.getBaseMapper().selectList(new QueryWrapper<HandoverSlip>());
            resultMap.success().message("查询交接单成功！").addElement("data",handoverSlips);
            return resultMap;
        }else if ("distribution".equals(role) && name_distribution != null){//省公司管理员
            List<HandoverSlip> handover = handoverSlipService.getHandover(name_distribution);
            if (handover != null){
                return resultMap.success().message("查询交接单成功！").addElement("data",handover);
            }else {
                return resultMap.fail().message("查询交接单失败！");
            }
        }
        return resultMap.fail();
    }
    @GetMapping("/getHandoverByidPackage/{idPackage}/{name_distribution}")
    public ResultMap getHandoverByID(@RequestHeader String token, @PathVariable("idPackage")String idPackage,
                                     @PathVariable(value = "name_distribution",required = false) String name_distribution){
        String permission = JWTUtil.getUserPermission(token);
        String role = JWTUtil.getUserRole(token);
        System.out.println("123");
        if ("distribution".equals(role) && idPackage != null) {
            List<HandoverSlip> handoverByID = handoverSlipService.getHandoverByID(idPackage,name_distribution);
            if (handoverByID != null){
                return resultMap.success().message("查询交接单成功！").addElement("data",handoverByID);
            }else {
                return resultMap.fail().message("查询交接单失败！");
            }
        }
        return resultMap.fail();
    }

    @PostMapping("/updateHandover")
    @ControllerLogAnnotation(remark = "交接单更新功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "交接单更新接口", notes = "根据交接单编码更新交接单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "handover",value = "交接单对象",required = true,paramType = "query",dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "交接单更新成功！"),
            @ApiResponse(code = 50011, message = "交接单更新失败，请重试！")
    })
    public ResultMap updateHandover(@RequestParam("handover") String handover){
        if (handover == null || "".equals(handover)){
            resultMap.fail().code(40010).message("服务器内部错误!");
        }
        HandoverSlip handoverSlip = JSON.parseObject(handover, HandoverSlip.class);
        int i = handoverSlipService.updateHandover(handoverSlip);
        if (i == 1){
            return resultMap.success().message("交接单更新成功！");
        }else {
            return resultMap.fail().message("交接单更新失败！");
        }
    }

    @PostMapping("/addHandover")
    @ControllerLogAnnotation(remark = "添加交接单点功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.ADD)
    @ApiOperation(value = "添加交接单接口", notes = "根据交接单对象添加交接单")
    @ApiImplicitParam(name = "addHandover",value = "交接单添加对象",required = true,dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "添加交接单成功！"),
            @ApiResponse(code = 50011, message = "添加交接单失败，请重试！")
    })
    public ResultMap addHandover(@RequestParam("addHandover") String addHandover){
        if (addHandover == null || "".equals(addHandover)){
            resultMap.fail().code(40010).message("服务器内部错误!");
        }
        HandoverSlip addHandover1 = JSON.parseObject(addHandover, HandoverSlip.class);
        HandoverPackage addHandover3 = JSON.parseObject(addHandover, HandoverPackage.class);
        int addHandover2 = handoverSlipService.getBaseMapper().insert(addHandover1);
        int insert = handoverPackageService.getBaseMapper().insert(addHandover3);
        if (addHandover2 == 1 && insert == 1){
            return resultMap.success().message("交接单添加成功！");
        }else {
            return resultMap.fail().message("交接单更新失败！");
        }
    }

    @GetMapping("/deteleHandover/{idHandoverOrder}")
    @ControllerLogAnnotation(remark = "删除交接单功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.DELETE)
    @ApiOperation(value = "删除交接单接口",notes = "根据交接单编码删除交接单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idHandoverOrder",value = "交接单编码",required = true,dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "删除交接单成功！"),
            @ApiResponse(code = 50011, message = "删除交接单失败，请重试！")
    })
    public ResultMap deteleHandover(@PathVariable("idHandoverOrder") String id_handover_order){
        if (id_handover_order == null || "".equals(id_handover_order)){
            return resultMap.fail().code(40010).message("服务器内部错误!");
        }
        int id_handover_order1 = handoverSlipService.deleteHandover(id_handover_order);
        if (id_handover_order1 == 1){
            resultMap.success().message("删除成功！");
        }else {
            resultMap.fail().message("删除失败！");
        }
        return resultMap;
    }
}
