package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.pojo.Company;
import com.tiandi.logistics.entity.pojo.Order;
import com.tiandi.logistics.mapper.CompanyMapper;
import com.tiandi.logistics.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public IPage getAllCompany(int page, int limit, String idCompany, String nameCompany) {
        IPage<Company> CompanyPage = new Page<>(page, limit);
        final QueryWrapper condition = new QueryWrapper();
        if (idCompany != null){
            condition.eq("idCompany",idCompany);
        }
        if (nameCompany != null){
            condition.eq("nameCompany",nameCompany);
        }
        final IPage<Company> companyIPage = companyMapper.selectPage(CompanyPage, condition);
        return CompanyPage;
    }
}
