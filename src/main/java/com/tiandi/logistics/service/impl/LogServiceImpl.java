package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.BusinessLog;
import com.tiandi.logistics.mapper.LogMapper;
import com.tiandi.logistics.service.LogService;
import org.springframework.stereotype.Service;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 10:47
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, BusinessLog> implements LogService {
}
