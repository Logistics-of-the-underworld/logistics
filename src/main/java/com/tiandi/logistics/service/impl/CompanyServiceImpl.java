package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.Company;
import com.tiandi.logistics.mapper.CompanyMapper;
import com.tiandi.logistics.service.CompanyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司管理 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
