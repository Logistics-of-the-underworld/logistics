package com.tiandi.logistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.CompanyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhanTianYi
 * @version 1.0
 * @since 2020/12/1 18:55
 */
@RestController
@RequestMapping("/")
@Api(tags = "公司管理")
public class CompanyController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private CompanyService companyService;


    @PostMapping("/getAllCompany")
    @ControllerLogAnnotation(remark = "公司获取",sysType = SysTypeEnum.ADMIN,opType = OpTypeEnum.SELECT)
    @ApiOperation(value = "公司获取",notes = "通过页码、页数、公司编号、公司名称条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCompany", value = "公司编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "nameCompany", value = "公司名", required = true, paramType = "query", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "公司查询成功！"),
            @ApiResponse(code = 50011, message = "公司查询失败，请重试！")
    })
    public ResultMap getAllCompany(@RequestParam(value = "page", required = true) int page,
                                 @RequestParam(value = "limit", required = true) int limit,
                                 @RequestParam(value = "idCompany",required = true) String idCompany,
                                 @RequestParam(value = "nameCompany",required = true) String nameCompany){
        final IPage allCompany = companyService.getAllCompany(page, limit, idCompany, nameCompany);
        resultMap.addElement("data",allCompany.getRecords());
        resultMap.addElement("total",allCompany.getTotal());
        resultMap.success().message("查询成功");
        return resultMap;
    }


}
