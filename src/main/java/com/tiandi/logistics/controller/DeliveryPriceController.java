package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.pojo.*;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.mapper.DeliveryPriceMapper;
import com.tiandi.logistics.service.*;
import com.tiandi.logistics.utils.JWTUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author SiSong Li
 * @version 1.0
 * @since 2020年12月04日, 0004 09:41
 */
@RestController
@RequestMapping("/deliveryPrice")
@Api(tags = "配送价格管理")
public class DeliveryPriceController {
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private DeliveryPriceService deliveryPriceService;
    @Autowired
    private DistributionService distributionService;
    @Autowired
    private DistributionRangeService distributionRangeService;
    @Autowired
    private DeliveryPriceRangeService deliveryPriceRangeService;
    @Autowired
    DeliveryPriceMapper deliveryPriceMapper;
    @Autowired
    DeliveryPriceSpotService deliveryPriceSpotService;


    @GetMapping("/getAllDeliveryPrice/{nameCompany}")
    @ControllerLogAnnotation(remark = "查询配送价格所有信息",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.SELECT)
    @ApiOperation(value = "配送价格信息获取接口",notes = "根据登录的用户身份权限来获取相应的配送价格信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nameCompany", value = "所属公司", paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "配送价格信息查询成功！"),
            @ApiResponse(code = 50011, message = "配送价格信息查询失败，请重试！")
    })
    public ResultMap getAllDeliveryPrice(@RequestHeader String token,@PathVariable(value = "nameCompany",required = false) String name_company){
        /*获取前端登录的身份及其权限*/
        String permission = JWTUtil.getUserPermission(token);
        String role = JWTUtil.getUserRole(token);

        /*登录的身份是总公司管理员时*/
        if ("root".equals(permission) && "admin".equals(role)){
            /*查询所有的配送价格信息*/
            List<DeliveryPrice> deliveryPrices = deliveryPriceService.getBaseMapper().selectList(new QueryWrapper<DeliveryPrice>());
            String deliveryPricesList = JSON.toJSONString(deliveryPrices);
            resultMap.success().message("配送价格信息查询成功！").addElement("data",deliveryPricesList);
            return resultMap;
        }else if ("admin".equals(permission) && "headCompany".equals(role) && name_company != null){
            /*查询省公司下所有配送点的信息*/
            List<Distribution> distributionList1 = distributionService.getBaseMapper().selectList(new QueryWrapper<Distribution>().eq("name_company", name_company));
            List<DeliveryPrice> deliveryPriceList = new ArrayList<>();

            /*一一把省公司下的配送点的配送点编码添加到集合中并且获得该配送点的配送范围ID*/
            Iterator<Distribution> iterator = distributionList1.iterator();
            while (iterator.hasNext()){
                /*逐一获取每一个配送点的信息*/
                Distribution distribution = iterator.next();
                /*逐一获取每一个配送点的编码*/
                Integer idDistribution = distribution.getIdDistribution();

                String id = String.valueOf(idDistribution);

                /*根据配送点编码获取配送价格编码*/
                DeliveryPriceSpot id_distribution2 = deliveryPriceSpotService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPriceSpot>().eq("id_distribution", id));
                String idDeliveryPrice = id_distribution2.getIdDeliveryPrice();
                /*根据配送点编码获取配点价格信息*/
                DeliveryPrice id_delivery_price = deliveryPriceService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPrice>().eq("id_delivery_price", idDeliveryPrice));
                /*把配送价格信息添加到集合*/
                deliveryPriceList.add(id_delivery_price);

                /*根据配送点编码获取配送范围编码*/
                DistributionRange id_distribution1 = distributionRangeService.getBaseMapper().selectOne(new QueryWrapper<DistributionRange>().eq("id_distribution", idDistribution));
                Integer idRange = id_distribution1.getIdRange();
                /*根据配送范围编码获取配送价格编码*/
                DeliveryPriceRange id_range = deliveryPriceRangeService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPriceRange>().eq("id_range", idRange));
                /*根据配送范围编码获取配送价格信息*/
                DeliveryPrice id_delivery_price1 = deliveryPriceService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPrice>().eq("id_delivery_price", id_range));
                /*把配送价格信息添加到集合*/
                deliveryPriceList.add(id_delivery_price1);
            }
            resultMap.success().message("配送价格信息查询成功！").addElement("data",deliveryPriceList);
            return resultMap;
        }
        return resultMap.fail();
    }

    @GetMapping("/getDeliveryPriceByID/{idDistribution}")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "配送价格信息查询成功！"),
            @ApiResponse(code = 50011, message = "配送价格信息查询失败，请重试！")
    })
    public ResultMap getDeliveryPriceByID(@RequestHeader String token,@PathVariable(name = "idDistribution") String id_distribution){
        /*获取前端登录的身份及其权限*/
        String permission = JWTUtil.getUserPermission(token);
        String role = JWTUtil.getUserRole(token);
        /*登录的身份是配送点管理员时*/
        if (permission.equals("admin") && role.equals("distribution") && id_distribution != null){
            /*根据配送点编码获取配送范围编码*/
            DistributionRange id_distribution1 = distributionRangeService.getBaseMapper().selectOne(new QueryWrapper<DistributionRange>().eq("id_distribution",id_distribution));
            Integer idRange = id_distribution1.getIdRange();
            System.out.println("配送范围编码："+idRange);//成功
            /*根据配送点编码获取配送价格编码*/
            DeliveryPriceSpot id_distribution2 = deliveryPriceSpotService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPriceSpot>().eq("id_distribution",id_distribution));
            String idDeliveryPrice = id_distribution2.getIdDeliveryPrice();
            System.out.println("配送价格编码1："+idDeliveryPrice);//成功

            String id = String.valueOf(idRange);
            /*根据配送范围编码获取配送价格编码*/
            DeliveryPriceRange id_range = deliveryPriceRangeService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPriceRange>().eq("id_range", id));
            String idDeliveryPrice1 = id_range.getIdDeliveryPrice();
            System.out.println("配送价格编码2："+idDeliveryPrice1);

            /*根据配送价格编码获取配送价格*/
            DeliveryPrice deliveryPrice = deliveryPriceService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPrice>().eq("id_delivery_price", idDeliveryPrice).eq("spot_or_range",0));
            DeliveryPrice deliveryPrice1 = deliveryPriceService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPrice>().eq("id_delivery_price", idDeliveryPrice1).eq("spot_or_range",1));
            /*把配送点的配送价格和配送范围的配送价格添加到集合*/
            List<DeliveryPrice> list = new ArrayList<>();
            if (deliveryPrice != null) {
                list.add(deliveryPrice);
            }
            if (deliveryPrice1 != null) {
                list.add(deliveryPrice1);
            }

            String string = JSON.toJSONString(list);
            resultMap.success().message("配送价格信息查询成功！").addElement("data",string);

            return resultMap;
        }
        return resultMap.fail();
    }

    @PostMapping("/updateDeliveryPrice")
    @ControllerLogAnnotation(remark = "配送价格更新功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "配送价格更新接口", notes = "根据配送价格编码更新配送价格信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deliveryPrice",value = "配送价格对象",required = true,paramType = "query",dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "配送价格更新成功！"),
            @ApiResponse(code = 50011, message = "配送价格更新失败，请重试！")
    })
    public ResultMap updateDeliveryPrice(@RequestParam("deliveryPrice") String deliveryPrice){
        if (deliveryPrice == null || "".equals(deliveryPrice)){
            resultMap.fail().code(40010).message("服务器内部错误!");
            return resultMap;
        }
        DeliveryPrice deliveryPrice1 = JSON.parseObject(deliveryPrice, DeliveryPrice.class);

        int i = deliveryPriceService.updateDeliveryPrice(deliveryPrice1);
        if (i == 1){
            resultMap.success().message("配送价格更新成功!");
        }else {
            resultMap.fail().message("配送价格更新失败!");
        }
        return resultMap;
    }

    @GetMapping("/deteleDeliveryPrice/{idDeliveryPrice}")
    @ControllerLogAnnotation(remark = "删除配送价格功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.DELETE)
    @ApiOperation(value = "删除配送价格接口",notes = "根据配送价格编码删除配送价格信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idDeliveryPrice",value = "配送价格编码",required = true,dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "删除配送价格成功！"),
            @ApiResponse(code = 50011, message = "删除配送价格失败，请重试！")
    })
    public ResultMap deteleDeliveryPrice(@PathVariable("idDeliveryPrice") String id_delivery_price){
        if (id_delivery_price == null || "".equals(id_delivery_price)){
            return resultMap.fail().code(40010).message("服务器内部错误!");
        }

        QueryWrapper<DeliveryPrice> deliveryPriceQueryWrapper = new QueryWrapper<>();
        QueryWrapper<DeliveryPrice> id_delivery_price1 = deliveryPriceQueryWrapper.in("id_delivery_price", id_delivery_price);
        int delete1 = deliveryPriceMapper.delete(id_delivery_price1);

        if (delete1 == 1){
            resultMap.success().message("删除成功！");
        }else {
            resultMap.fail().message("删除失败！");
        }

        return resultMap;
    }
}
