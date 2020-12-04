package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.Role;
import com.tiandi.logistics.entity.result.ResultMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 用户权限表 服务类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface RoleService extends IService<Role> {

    /**
     * 修改用户权限接口
     *
     * @param roleId 修改后的权限ID
     * @param targetUser 修改的目标用户ID
     * @return
     */
    boolean changeRole(String roleId, String targetUser);

}
