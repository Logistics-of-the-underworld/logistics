package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.mapper.UserMapper;
import com.tiandi.logistics.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 16:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
