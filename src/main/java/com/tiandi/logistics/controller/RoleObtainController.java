package com.tiandi.logistics.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.pojo.Role;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.service.RoleService;
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

import java.util.List;

/**
 * 权限管理 - 权限获取
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/2 16:43
 */
@RestController
@RequestMapping("/auth/role")
@Api(tags = "权限管理 - 权限获取")
public class RoleObtainController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private ResultMap resultMap;

    /**
     * 获取所有的权限列表
     * 表述中的全部权限不包括系统用户的两种权限
     *
     * @return
     */
    @GetMapping("/getRole")
    @ApiOperation(value = "获取所有的权限列表", notes = "表述中的全部权限不包括系统用户的两种权限")
    @RequiresRoles("admin")
    public ResultMap getAll() {

        List<Role> roleList = roleService.list(new QueryWrapper<Role>().ne("permission", "root").ne("role", "admin"));

        return resultMap.success().message("获取所有的权限列表").addElement("role", roleList);
    }

    /**
     * 根据身份获取权限
     * 不同的身份角色可以授予新增用户的不同权限
     * 此接口集成分公司、总公司、管理员三种权限进行权限获取
     *
     * @param token 凭证
     * @return
     */
    @GetMapping("/getRoleByIdentity")
    @ApiOperation(value = "根据身份获取权限")
    @ApiImplicitParam(name = "token", value = "凭证", required = true, paramType = "header")
    @RequiresRoles(logical = Logical.OR, value = {"headCompany", "admin", "branchCompany"})
    public ResultMap getRoleByIdentity(@RequestHeader String token) {

        String userRole = JWTUtil.getUserRole(token);

        if ("branchCompany".equals(userRole)) {
            resultMap.success().message("获取权限成功！")
                    .addElement("role", roleService.list(new QueryWrapper<Role>()
                            .eq("role", "branchCompany")));
        } else if ("headCompany".equals(userRole)) {
            resultMap.success().message("获取权限成功！")
                    .addElement("role", roleService.list(new QueryWrapper<Role>().eq("role", "branchCompany")));
        } else {
            resultMap.success().message("获取权限成功")
                    .addElement("role", roleService.list(new QueryWrapper<Role>()
                            .eq("role", "branchCompany").or().eq("role", "headCompany")));
        }

        return resultMap;
    }

    /**
     * 修改用户权限接口
     *
     * @param token 操作者凭证
     * @param roleId 身份ID
     * @param targetUser 修改该目标用户ID
     * @return
     */
    @PostMapping("/changeRole")
    @ControllerLogAnnotation(remark = "修改用户权限", sysType = SysTypeEnum.ADMIN, opType = OpTypeEnum.UPDATE)
    @ApiOperation(value = "修改身份权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true, paramType = "header"),
            @ApiImplicitParam(name = "roleId", value = "权限ID", required = true, paramType = "query")
    })
    @RequiresRoles(logical = Logical.OR, value = {"headCompany", "admin", "branchCompany"})
    @RequiresPermissions(logical = Logical.OR, value = {"admin","normal","root"})
    public ResultMap changeRole(@RequestHeader String token, @RequestParam("roleId") String roleId,
                                @RequestParam("targetUser") String targetUser) {

        if (roleService.changeRole(roleId, targetUser)) {
            resultMap.success().message(JWTUtil.getUsername(token) + "管理员将" + targetUser + "用户的权限设置为" + roleId);
        } else {
            resultMap.fail().message("服务器内部错误");
        }

        return resultMap;
    }

}
