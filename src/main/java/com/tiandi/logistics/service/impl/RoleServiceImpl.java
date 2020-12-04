package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.Role;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.mapper.RoleMapper;
import com.tiandi.logistics.service.RoleService;
import com.tiandi.logistics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限表 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserService userService;

    @Override
    public boolean changeRole(String roleId, String targetUser) {
        return userService.update(new UpdateWrapper<User>().eq("id_tb_user", targetUser).set("role", roleId));
    }
}
