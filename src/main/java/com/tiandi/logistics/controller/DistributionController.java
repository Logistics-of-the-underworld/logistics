package com.tiandi.logistics.controller;

/**
 * 配送点管理
 */


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.pojo.Distribution;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.DistributionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author SiSong Li
 * @version 1.0
 * @since 2020年12月02日, 0002 11:29
 */
@RestController
@RequestMapping("/distribution")
@Api(tags = "配送点管理")
public class DistributionController {
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private DistributionService distributionService;

    /**
     * 根据身份及权限查询配送点所有信息
     * @return
     */
    @PostMapping("/getAllDistribution")
    @ControllerLogAnnotation(remark = "查询配送点所有信息",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.SELECT)
    @ApiOperation(value = "获取配送点信息接口",notes = "当前配送点信息包括配送点的编码和名称、管理员代码和姓名、管理员\t口令、确认口令以及上行站点和下行站点等")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name_company", value = "所属公司", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "role",value = "身份",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "permission",value = "权限",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "id_distribution",value = "配送点编码",paramType = "query",dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "配送点信息查询成功！"),
            @ApiResponse(code = 50011, message = "配送点信息查询失败，请重试！")
    })
    public ResultMap getAllDistribution(@RequestParam(value = "name_company",required = false) String name_company, @RequestParam("role") String role, @RequestParam String permission, @RequestParam(value = "id_distribution",required = false) String id_distribution){
        if (permission.equals("root") && role.equals("admin")){
            List<Distribution> distributionList1 = distributionService.getBaseMapper().selectList(new QueryWrapper<Distribution>());
            for (Distribution x:distributionList1
                 ) {
                System.out.println(x);
            }
            String distributionList  = JSON.toJSONString(distributionList1);
            resultMap.success().message("配送点信息查询成功！").addElement("distributionList",distributionList);
        }else if (permission.equals("admin") && role.equals("distribution") && name_company != null){
            List<Distribution> distributionList1 = distributionService.getBaseMapper().selectList(new QueryWrapper<Distribution>().eq("name_company", name_company));
            for (Distribution x:distributionList1
            ) {
                System.out.println(x);
            }
            String distributionList  = JSON.toJSONString(distributionList1);
            resultMap.success().message("配送点信息查询成功！").addElement("distributionList",distributionList);
        }else if (permission.equals("admin") && role.equals("distribution") && id_distribution != null){
            List<Distribution> distributionList1 = distributionService.getBaseMapper().selectList(new QueryWrapper<Distribution>().eq("id_distribution", id_distribution));
            for (Distribution x:distributionList1
            ) {
                System.out.println(x);
            }
            String distributionList  = JSON.toJSONString(distributionList1);
            resultMap.success().message("配送点信息查询成功！").addElement("distributionList",distributionList);
        }else if (name_company == null && id_distribution == null){
            resultMap.fail().code(50011).message("密码错误或账户不存在!");
        }else {
            resultMap.fail().code(50011).message("密码错误或账户不存在!");
        }
        return resultMap;
    }

    @PostMapping("/updateDistribution")
    @ControllerLogAnnotation(remark = "配送点更新功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "配送点更新接口", notes = "根据配送点编码更新路线信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id_distribution",value = "配送点编码",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "distribution",value = "配送点对象",required = true,paramType = "query",dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "配送点更新成功！"),
            @ApiResponse(code = 50011, message = "配送点更新失败，请重试！")
    })
    public ResultMap updateDistribution(@RequestParam("id_distribution") String id_distribution,@RequestParam("distribution") String distribution){

        System.out.println(distribution.toString());
        if (distribution == null || "".equals(distribution)){
            resultMap.fail().code(40010).message("服务器内部错误!");
        }
        Distribution distribution1 = JSON.parseObject(distribution,Distribution.class);
        distribution1.setIdDistribution(Integer.valueOf(id_distribution));
        System.out.println(distribution1.toString());
        int updatedistribution = distributionService.updatedistribution(distribution1);
        if (updatedistribution == 1){
            resultMap.success().message("更新成功!");
        }else {
            resultMap.fail().message("更新失败!");
        }
        return resultMap;
    }
    @PostMapping("/addDistribution")
    @ControllerLogAnnotation(remark = "添加配送点功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.ADD)
    @ApiOperation(value = "添加配送点接口", notes = "根据配送点对象添加配送点")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "添加配送点成功！"),
            @ApiResponse(code = 50011, message = "添加配送点失败，请重试！"),
            @ApiResponse(code = 50012, message = "该配送编码已存在，请重新输入！")
    })
    protected ResultMap addDistribution(@RequestParam("distribution") String distribution){
        if (distribution == null || "".equals(distribution)) {
            return resultMap.fail().code(40010).message("服务器内部错误!");
        }
        Distribution distribution1 = JSON.parseObject(distribution, Distribution.class);
        Integer idDistribution = distribution1.getIdDistribution();
        Distribution distribution2 = distributionService.getBaseMapper().selectOne(new QueryWrapper<Distribution>().eq("id_distribution", idDistribution));

        if (distribution2 != null){
            resultMap.fail().message("该配送编码已存在，请重新输入！");
        }else {
            int insert = distributionService.getBaseMapper().insert(distribution1);
            if (insert == 1) {
                resultMap.success().message("添加成功!");
            } else {
                resultMap.fail().code(50012).message("添加失败!");
            }
        }
        return resultMap;
    }

    @GetMapping("/deleteDistribution")
    @ControllerLogAnnotation(remark = "删除配送点功能",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.DELETE)
    @ApiOperation(value = "删除配送点接口",notes = "根据配送点编码删除配送点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id_distribution",value = "配送点编码",required = true,dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "删除配送点成功！"),
            @ApiResponse(code = 50011, message = "删除配送点失败，请重试！")
    })
    public ResultMap deleteDistribution(@RequestParam("id_distribution") String id_distribution){
        if (id_distribution == null || "".equals(id_distribution)){
            return resultMap.fail().code(40010).message("服务器内部错误!");
        }
        int id_distribution1 = distributionService.getBaseMapper().delete(new QueryWrapper<Distribution>().eq("id_distribution", id_distribution));
        if (id_distribution1 == 1){
            resultMap.success().message("删除成功！");
        }else {
            resultMap.fail().message("删除失败！");
        }
        return resultMap;
    }
}
