package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.front.AuthManageEntity;

import java.util.List;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 16:02
 */
public interface UserService extends IService<User> {

    /**
     * 获取所有用于，鉴别于管理员具备其权限细分，需要限定其获取的权限
     *
     * @param roleId 权限编号
     * @param page   页码
     * @return
     */
    IPage<AuthManageEntity> getAllAuth(Page<?> page, Integer... roleId);

    /**
     * 通过组织来获取其对应的所属用户（员工）
     *
     * @param page         分页
     * @param organization 组织名
     * @param role         权限
     * @return
     */
    IPage<AuthManageEntity> getAuthByOrganization(Page<?> page, String organization, List<Integer> role, String delete);

    /**
     * 通过组织来获取其对应的所属用户（员工）
     *
     * @param page         分页
     * @param organization 组织名
     * @param role         权限
     * @param type         角色类别（权限类别）
     * @return
     */
    IPage<AuthManageEntity> getAuthByOrganizationOnType(Page<?> page, String organization, List<Integer> role, String type, String delete);

    /**
     * 获取消费者用户
     *
     * @param page 分页
     * @return
     */
    IPage<AuthManageEntity> getAllConsumerAuth(Page<?> page);

}
