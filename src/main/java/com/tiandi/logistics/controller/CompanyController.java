package com.tiandi.logistics.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.async.AsyncMailTask;
import com.tiandi.logistics.entity.pojo.Company;
import com.tiandi.logistics.entity.pojo.OrganizationRelation;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.CompanyService;
import com.tiandi.logistics.service.OrganizationRelationService;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.JWTUtil;
import com.tiandi.logistics.utils.Md5Encoding;
import com.tiandi.logistics.utils.RedisUtil;
import com.tiandi.logistics.utils.ToPinInUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

/**
 * @author ZhanTianYi
 * @version 1.0
 * @since 2020/12/1 18:55
 */
@RestController
@RequestMapping("/company")
@Api(tags = "公司管理")
public class CompanyController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private OrganizationRelationService relationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ToPinInUtil pinInUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AsyncMailTask mailTask;

    @PostMapping("/getAllCompany")
    @ControllerLogAnnotation(remark = "公司获取", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.SELECT)
    @ApiOperation(value = "公司获取", notes = "通过页码、页数、公司编号、公司名称条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCompany", value = "公司编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "nameCompany", value = "公司名", required = false, paramType = "query", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "公司查询成功！"),
            @ApiResponse(code = 50011, message = "公司查询失败，请重试！")
    })
    public ResultMap getAllCompany(@RequestParam(value = "page", required = true) int page,
                                   @RequestParam(value = "limit", required = true) int limit,
                                   @RequestParam(value = "idCompany", required = false) String idCompany,
                                   @RequestParam(value = "nameCompany", required = false) String nameCompany) {
        final IPage allCompany = companyService.getAllCompany(page, limit, idCompany, nameCompany);
        resultMap.addElement("data", allCompany.getRecords());
        resultMap.addElement("total", allCompany.getTotal());
        resultMap.success().message("查询成功");
        return resultMap;
    }


    @PostMapping("updateCompany")
    @ControllerLogAnnotation(remark = "公司更新", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "公司更新", notes = "根据公司ID更新公司信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "公司对象", required = false, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "公司更新成功！"),
            @ApiResponse(code = 50011, message = "公司更新失败，请重试！")
    })
    public ResultMap updateCompany(@RequestParam(value = "company") String companyStr) {
        //判空，防止抛出异常
        if (companyStr == null || "".equals(companyStr)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        Company company = JSON.parseObject(companyStr, Company.class);
        int update = companyService.updateCompany(company);
        if (update == 1) {
            resultMap.success().message("更新成功");
        } else {
            resultMap.fail().message("更新失败");
        }
        return resultMap;
    }


    @PostMapping("/addCompany")
    @ControllerLogAnnotation(remark = "公司添加", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.ADD)
    @ApiOperation(value = "添加公司", notes = "添加一个公司·")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "公司·对象", required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40000, message = "公司添加成功！"),
            @ApiResponse(code = 50011, message = "公司添加失败，请重试！")
    })
    public ResultMap addCompany(@RequestParam(value = "company", required = false) String companyStr,
                                @RequestParam(value = "userStr") String userStr) {

        //判空，防止抛出异常
        if (companyStr == null || "".equals(companyStr)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }

        Company company = JSON.parseObject(companyStr, Company.class);
        User user = JSON.parseObject(userStr, User.class);
        String name = user.getUsername();
        user.setRole(7);
        user.setPetname("TP用户" + new Random().nextInt(1000));
        String salt = Md5Encoding.md5RandomSaltGenerate();
        user.setSalt(salt);
        user.setPassword(Md5Encoding.md5RanSaltEncode(user.getPassword(), salt));
        //UUID为键，存储在Redis中，供激活使用
        String userUUID = UUID.randomUUID().toString();
        user.setActiveuuid(userUUID);
        user.setUsername(pinInUtil.toPinyin(user.getUsername()).replace(" ", ""));

        userService.save(user);

        OrganizationRelation relation = new OrganizationRelation();
        relation.setOrganization(company.getNameCompany());
        relation.setUser_id(user.getIdTbUser());
        relationService.save(relation);
        Integer integer = companyService.getMaxID() + 1;
        company.setIdCompany(integer.toString());
        int save = companyService.addCompany(company);
        if (save == 1) {
            //Reds保存(UUID，userID)键值对
            redisUtil.set(userUUID, user.getIdTbUser(), 60 * 60 * 24);
            //异步提交激活邮件发送请求
            mailTask.activeMailTask(userUUID, user.getEmail(), company.getNameCompany(), name);
            return resultMap.success().message("添加成功！请通知其前往邮箱进行激活！");
        } else {
            resultMap.fail().message("添加失败");
        }
        return resultMap;
    }


    @GetMapping("/deleteCompany/{idCompany}")
    @ControllerLogAnnotation(remark = "公司删除", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.DELETE)
    @ApiOperation(value = "删除公司", notes = "根据公司编号删除订单")
    @ApiImplicitParam(name = "id_company", value = "公司ID", required = true, paramType = "query", dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 40000, message = "公司删除成功！"),
            @ApiResponse(code = 50011, message = "公司删除失败，请重试！")
    })
    public ResultMap deleteCompany(@PathVariable String idCompany) {
        if (idCompany == null || "".equals(idCompany)) {
            return resultMap.fail().code(40010).message("服务器内部错误");
        }
        int delete = companyService.deleteCompany(idCompany);
        if (delete == 1) {
            resultMap.success().message("删除成功");
        } else {
            resultMap.fail().message("删除失败");
        }
        return resultMap;
    }

    @PostMapping("/verifyCompanyName")
    public ResultMap verifyCompanyName(@RequestParam("companyName") String companyName) {
        int count = companyService.count(new QueryWrapper<Company>().eq("name_company", companyName));
        return resultMap.success().message(count <= 0);
    }

}
