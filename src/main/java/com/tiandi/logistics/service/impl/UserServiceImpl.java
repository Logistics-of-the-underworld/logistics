package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.entity.front.AuthManageEntity;
import com.tiandi.logistics.mapper.UserMapper;
import com.tiandi.logistics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 16:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<AuthManageEntity> getAllAuth(Page<?> page, Integer... roleId) {
        return userMapper.getAllAuth(page, roleId);
    }

    @Override
    public IPage<AuthManageEntity> getAuthByOrganization(Page<?> page, String organization, List<Integer> role, String delete) {
        return userMapper.getAuthByOrganization(page, organization, role, delete);
    }

    @Override
    public IPage<AuthManageEntity> getAuthByOrganization(Page<?> page, String organization, List<Integer> role, String delete, String ban) {
        return userMapper.getAuthByOrganizationStandBy(page, organization, role, delete, ban);
    }

    @Override
    public IPage<AuthManageEntity> getAuthByOrganizationOnType(Page<?> page, String organization, List<Integer> role, String type, String delete) {
        return userMapper.getAuthByOrganizationOnType(page, organization, role, type, delete);
    }

    @Override
    public IPage<AuthManageEntity> getAllConsumerAuth(Page<?> page) {
        return userMapper.getAllConsumerAuth(page);
    }
}
