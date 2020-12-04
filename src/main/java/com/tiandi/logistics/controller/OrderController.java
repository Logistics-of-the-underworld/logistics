package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.pojo.Order;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.OrderService;
import com.tiandi.logistics.utils.JWTUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单管理控制层接口
 *
 * @author ZhanTianYi
 * @version 1.0
 * @since 2020/12/1 19:43
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理")
public class OrderController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private OrderService orderService;

    @PostMapping("/getAllOrder")
    @ControllerLogAnnotation(remark = "订单获取",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.SELECT)
    @ApiOperation(value = "订单获取",notes = "通过页码、页数、收寄地、配送地、客户姓名、订单状态、查询时段任一条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiverAddress", value = "收寄地", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "idDistribution", value = "配送地ID", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "receiverName", value = "客户姓名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "stateOrder", value = "订单状态", required = true, paramType = "query", dataType = "Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "订单查询成功！"),
            @ApiResponse(code = 50011, message = "订单查询失败，请重试！")
    })
    public ResultMap getAllOrder(@RequestParam(value = "page", required = true) int page,
                                 @RequestParam(value = "limit", required = true) int limit,
                                 @RequestParam(value = "receiverAddress",required = false) String receiverAddress,
                                 @RequestParam(value = "idDistribution",required = false) Integer idDistribution,
                                 @RequestParam(value = "receiverName",required = false) String receiverName,
                                 @RequestParam(value = "stateOrder",required = false) Integer stateOrder){
        final IPage allOrder = orderService.getAllOrder(page, limit, receiverAddress, idDistribution, receiverName, stateOrder);
        resultMap.addElement("data",allOrder.getRecords());
        resultMap.addElement("total",allOrder.getTotal());
        resultMap.success().message("查询成功");
        return resultMap;
    }


    @PostMapping("/addOrder")
    @ControllerLogAnnotation(remark = "订单添加",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.ADD)
    @ApiOperation(value = "添加订单", notes = "添加一个订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "订单对象", required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "订单添加成功！"),
            @ApiResponse(code = 50011, message = "订单添加失败，请重试！")
    })
    public ResultMap addOrder(@RequestParam(value = "order", required = false) String orderStr,
                              @RequestHeader String token){
        String userName = JWTUtil.getUsername(token);

        //判空，防止抛出异常
        if (orderStr == null || "".equals(orderStr)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }

        Order order = JSON.parseObject(orderStr, Order.class);

        int save = orderService.addOrder(order);
        if (save == 1){
            resultMap.success().message("添加成功");
        } else {
            resultMap.fail().message("添加失败");
        }
        return resultMap;
    }


    @PostMapping("/updateOrder")
    @ControllerLogAnnotation(remark = "订单更新",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "订单更新", notes = "根据订单ID更新订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "订单对象", required = false, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "订单更新成功！"),
            @ApiResponse(code = 50011, message = "订单更新失败，请重试！")
    })
    public ResultMap updateOrder(@RequestParam(value = "order") String orderStr){
        //判空，防止抛出异常
        if (orderStr == null || "".equals(orderStr)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        Order order = JSON.parseObject(orderStr, Order.class);
        int update = orderService.updateOrder(order);
        if (update == 1){
            resultMap.success().message("更新成功");
        } else {
            resultMap.fail().message("更新失败");
        }
        return resultMap;
    }


    @GetMapping("/deleteOrder/{idOrder}")
    @ControllerLogAnnotation(remark = "订单删除",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.DELETE)
    @ApiOperation(value = "删除订单", notes = "根据订单ID删除订单")
    @ApiImplicitParam(name = "id_order",value = "订单ID",required = true,paramType = "query",dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "订单删除成功！"),
            @ApiResponse(code = 50011, message = "订单删除失败，请重试！")
    })
    public ResultMap deleteOrder(@PathVariable String idOrder){
        if (idOrder == null || "".equals(idOrder)){
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        int delete = orderService.deleteOrder(idOrder);
        if (delete == 1){
            resultMap.success().message("删除成功");
        } else {
            resultMap.fail().message("删除失败");
        }
        return resultMap;
    }

    @PostMapping("/confirmOrder")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "订单确认成功！"),
            @ApiResponse(code = 50011, message = "订单确认失败，请重试！")
    })
    public ResultMap confirmOrder(){
        List<Order> stateOrder = orderService.getStateOrder();
        return resultMap.addElement("data",stateOrder).success();
    }


}
