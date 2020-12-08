package com.tiandi.logistics.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.pojo.Company;
import com.tiandi.logistics.entity.pojo.Role;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.front.AuthManageEntity;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.CompanyService;
import com.tiandi.logistics.service.RoleService;
import com.tiandi.logistics.service.UserService;
import com.tiandi.logistics.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理模块实现
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/2 8:33
 */
@RestController
@Api(tags = "用户管理")
@RequestMapping("/auth/manage")
public class AuthManagementController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CompanyService companyService;

    /**
     * 对用户进行删除/开除/离职进行处理
     *
     * @param token          凭证
     * @param targetUsername 目标用户用户名
     * @param note           操作原因备注
     * @return
     */
    @PostMapping("/deleteAtuh")
    @ControllerLogAnnotation(remark = "对用户进行删除/开除/离职进行处理", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.DELETE)
    @ApiOperation(value = "对用户进行删除/开除/离职进行处理", notes = "该方法底层实现为逻辑删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetUsername", value = "修改的目标用户用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "note", value = "移除用户的原因备注", required = true, paramType = "query"),
            @ApiImplicitParam(name = "token", value = "凭证", required = true, paramType = "header")
    })
    @RequiresRoles(logical = Logical.OR, value = {"branchCompany", "headCompany", "admin", "distribution"})
    @RequiresPermissions(logical = Logical.OR, value = {"admin", "normal", "root"})
    public ResultMap deleteAuth(@RequestHeader String token, @RequestParam("targetUsername") String targetUsername,
                                @RequestParam("note") String note) {
        boolean remove = userService.remove(new QueryWrapper<User>().eq("username", targetUsername));

        if (remove) {
            resultMap.success().message(JWTUtil.getUsername(token) + " 将该 " + targetUsername + " 用户进行了" + note + "处理");
        } else {
            resultMap.fail().message("修改失败！服务器内部错误");
        }
        return resultMap;

    }

    /**
     * 进行工作权限委派
     *
     * @param token          凭证
     * @param targetUsername 目标用户
     * @param changeRole     修改权限
     * @return
     */
    @PostMapping("/changeAuthRole")
    @ControllerLogAnnotation(remark = "用户权限委派", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "进行工作权限委派", notes = "总公司对分公司管理员进行分配\n系统管理员对总公司管理员进行分配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetUsername", value = "修改的目标用户用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "changeRole", value = "修改的权限ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "token", value = "凭证", required = true, paramType = "header")
    })
    @RequiresRoles(logical = Logical.OR, value = {"admin", "headCompany"})
    @RequiresPermissions(logical = Logical.OR, value = {"admin", "normal", "root"})
    public ResultMap changeAuthRole(@RequestHeader String token,
                                    @RequestParam("targetUsername") String targetUsername,
                                    @RequestParam("changeRole") String changeRole) {

        boolean update = userService.update(new UpdateWrapper<User>().set("role", changeRole).eq("username", targetUsername));

        if (update) {
            resultMap.success().message(JWTUtil.getUsername(token) + " 将该 " + targetUsername + " 用户权限修改： " + changeRole);
        } else {
            resultMap.fail().message("修改失败！服务器内部错误");
        }
        return resultMap;

    }

    /**
     * 获取所有的用户信息接口，为第三方数据库拉取数据/胡乱查询提供支持
     *
     * @param token       请求用户的凭证
     * @param size        每页容量
     * @param pageCurrent 页码
     * @return
     */
    @GetMapping("/getAllAuth/{size}/{pageCurrent}")
    @ApiOperation(value = "获取所有的用户信息", notes = "拓展接口，项目中使用概率不高\n管理员层面接口，根据管理权限不同获取的用户的身份也不同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "每一页的数据数量", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageCurrent", value = "当前页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "token", value = "凭证", required = true, paramType = "header")
    })
    @RequiresRoles("admin")
    public ResultMap getAllAuth(@RequestHeader String token, @PathVariable String size,
                                @PathVariable String pageCurrent) {

        String role = JWTUtil.getUserRole(token);
        String permission = JWTUtil.getUserPermission(token);

        Integer roleId = roleService.getOne(new QueryWrapper<Role>().select("id_tb_role").eq("role", role).eq("permission", permission)).getIdTbRole();

        Page<AuthManageEntity> auth = null;

        if ("root".equals(role)) {
            auth = (Page<AuthManageEntity>) userService.getAllAuth(new Page<>(Long.parseLong(pageCurrent), Long.parseLong(size)), roleId);
        } else {
            auth = (Page<AuthManageEntity>) userService.getAllAuth(new Page<>(Long.parseLong(pageCurrent), Long.parseLong(size)), roleId, roleId + 1);
        }

        return resultMap.success().message("获取所有用户信息")
                .addElement("total", auth.getTotal())
                .addElement("userList", auth.getRecords())
                .addElement("totalPageNumber", auth.getTotal() / Long.parseLong(size));
    }

    /**
     * 获取所有消费者用户
     *
     * @param size        每页容量
     * @param pageCurrent 当前页
     * @return
     */
    @GetMapping("/getAllConsumerAuth/{size}/{pageCurrent}")
    @ApiOperation(value = "获取所有消费者用户", notes = "管理员端接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "每一页的数据数量", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageCurrent", value = "当前页码", required = true, paramType = "path")
    })
    @RequiresRoles("admin")
    public ResultMap getAllConsumerAuth(@PathVariable String size, @PathVariable String pageCurrent) {

        Page<AuthManageEntity> consumerAuth = (Page<AuthManageEntity>) userService.getAllConsumerAuth(
                new Page<AuthManageEntity>(Long.parseLong(pageCurrent), Long.parseLong(size)));

        return resultMap.success().message("获取消费者用户成功")
                .addElement("userList", consumerAuth.getRecords())
                .addElement("total", consumerAuth.getTotal())
                .addElement("totalPageNumber", consumerAuth.getTotal() / Long.parseLong(size));
    }

    /**
     * 通过组织来获取其对应的所属用户（员工）
     *
     * @param organization 组织名
     * @param size         页容量
     * @param pageCurrent  页数
     * @param delete       删除标志位： 0： 现存 1：删除
     * @param token        凭证
     * @return
     */
    @GetMapping("/getAuthByOrganization/{organization}/{size}/{pageCurrent}/{delete}")
    @ApiOperation(value = "通过组织获取用户", notes = "各个公司的管理员可以获取对应公司员工的接口\n系统管理员可以获取对应公司下的员工和管理员人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "organization", value = "组织名称", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页面容量", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "pageCurrent", value = "当前页", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "delete", value = "是否是已经辞退的用户", required = true, paramType = "path", dataType = "String")
    })
    @RequiresRoles(logical = Logical.OR, value = {"branchCompany", "headCompany", "admin", "distribution"})
    @RequiresPermissions(logical = Logical.OR, value = {"admin", "normal", "root"})
    public ResultMap getAuthByOrganization(@RequestHeader String token, @PathVariable String organization,
                                           @PathVariable String size, @PathVariable String pageCurrent,
                                           @PathVariable String delete) {

        List<Integer> roleId = new ArrayList<>();
        String userRole = JWTUtil.getUserRole(token);

        getRoleListTools(userRole, organization, roleId);

        Page<AuthManageEntity> auth = (Page<AuthManageEntity>) userService.getAuthByOrganization(
                new Page<>(Long.parseLong(pageCurrent), Long.parseLong(size)), organization, roleId, delete);

        return resultMap.success().message("获取某个组织所有用户信息")
                .addElement("total", auth.getTotal())
                .addElement("userList", auth.getRecords())
                .addElement("totalPageNumber", auth.getTotal() / Long.parseLong(size));
    }

    /**
     * 通过组织来获取其对应的所属用户（员工）
     *
     * @param organization 组织名
     * @param size         页容量
     * @param pageCurrent  页数
     * @param delete       删除标志位： 0： 现存 1：删除
     * @param token        凭证
     * @return
     */
    @GetMapping("/getAuthByOrganizationStandBy/{organization}/{size}/{pageCurrent}/{ban}/{delete}")
    @ApiOperation(value = "通过组织获取用户", notes = "各个公司的管理员可以获取对应公司员工的接口\n系统管理员可以获取对应公司下的员工和管理员人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "organization", value = "组织名称", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页面容量", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "pageCurrent", value = "当前页", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "delete", value = "是否是已经辞退的用户", required = true, paramType = "path", dataType = "String")
    })
    @RequiresRoles(logical = Logical.OR, value = {"branchCompany", "headCompany", "admin", "distribution"})
    @RequiresPermissions(logical = Logical.OR, value = {"admin", "normal", "root"})
    public ResultMap getAuthByOrganizationStandBy(@RequestHeader String token, @PathVariable String organization,
                                           @PathVariable String size, @PathVariable String pageCurrent,
                                           @PathVariable String delete, @PathVariable String ban) {

        List<Integer> roleId = new ArrayList<>();
        String userRole = JWTUtil.getUserRole(token);

        getRoleListTools(userRole, organization, roleId);

        Page<AuthManageEntity> auth = (Page<AuthManageEntity>) userService.getAuthByOrganization(
                new Page<>(Long.parseLong(pageCurrent), Long.parseLong(size)), organization, roleId, delete, ban);

        return resultMap.success().message("获取某个组织所有用户信息")
                .addElement("total", auth.getTotal())
                .addElement("userList", auth.getRecords())
                .addElement("totalPageNumber", auth.getTotal() / Long.parseLong(size));
    }

    /**
     * 通过组织来获取其对应的所属用户（员工）
     * 同时增加过滤筛选条件
     *
     * @param organization 组织名
     * @param type         用户的身份类别
     * @param size         页容量
     * @param pageCurrent  页数
     * @param delete       删除标志 0：现存 1：删除
     * @param token        凭证
     * @return
     */
    @GetMapping("/getAuthByOrganization/{organization}/{size}/{pageCurrent}/{type}/{delete}")
    @ApiOperation(value = "通过组织获取对应分类用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "organization", value = "组织名称", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页面容量", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "pageCurrent", value = "当前页", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "角色类别", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "delete", value = "是否是已经辞退的用户", required = true, paramType = "path", dataType = "String")
    })
    @RequiresRoles(logical = Logical.OR, value = {"branchCompany", "headCompany", "admin", "distribution"})
    @RequiresPermissions(logical = Logical.OR, value = {"admin", "normal", "root"})
    public ResultMap getAuthByOrganization(@RequestHeader String token, @PathVariable String organization,
                                           @PathVariable String type, @PathVariable String size,
                                           @PathVariable String pageCurrent, @PathVariable String delete) {
        List<Integer> roleId = new ArrayList<>();
        String userRole = JWTUtil.getUserRole(token);

        getRoleListTools(userRole, organization, roleId);

        Page<AuthManageEntity> auth = (Page<AuthManageEntity>) userService.getAuthByOrganizationOnType(
                new Page<>(Long.parseLong(pageCurrent), Long.parseLong(size)), organization, roleId, type, delete);

        return resultMap.success().message("获取所有用户信息")
                .addElement("total", auth.getTotal())
                .addElement("userList", auth.getRecords())
                .addElement("totalPageNumber", auth.getTotal() / Long.parseLong(size));
    }

    /**
     * 判断身份信息使用，用于获取用户管理界面中用户信息时筛选使用方法
     *
     * @param userRole     用户的当前权限
     * @param organization 所查询的用户组织
     * @param roleId       保存查询出来的权限集合
     */
    public void getRoleListTools(String userRole, String organization, List<Integer> roleId) {

        if ("branchCompany".equals(userRole)) {
            Integer idTbRole = roleService.getOne(new QueryWrapper<Role>()
                    .select("id_tb_role")
                    .eq("role", "branchCompany")
                    .eq("permission", "worker")).getIdTbRole();
            roleId.add(idTbRole);
        } else if ("headCompany".equals(userRole)) {
            Integer idTbRole = roleService.getOne(new QueryWrapper<Role>()
                    .select("id_tb_role")
                    .eq("role", "headCompany")
                    .eq("permission", "worker")).getIdTbRole();
            roleId.add(idTbRole);
        } else if ("distribution".equals(userRole)) {
            Integer idTbRole = roleService.getOne(new QueryWrapper<Role>()
                    .select("id_tb_role")
                    .eq("role", "distribution")
                    .eq("permission", "worker")).getIdTbRole();
            roleId.add(idTbRole);
        } else {
            Integer headCompany = companyService.getOne(new QueryWrapper<Company>()
                    .select("head_company")
                    .eq("name_company", organization)).getHeadCompany();
            if (headCompany != null && headCompany > 0) {
                List<Role> list = roleService.list(new QueryWrapper<Role>()
                        .select("id_tb_role")
                        .eq("role", "branchCompany"));
                for (Role r : list) {
                    roleId.add(r.getIdTbRole());
                }
            } else {
                List<Role> list = roleService.list(new QueryWrapper<Role>()
                        .select("id_tb_role")
                        .eq("role", "headCompany"));
                for (Role r : list) {
                    roleId.add(r.getIdTbRole());
                }
            }
        }

    }
}
