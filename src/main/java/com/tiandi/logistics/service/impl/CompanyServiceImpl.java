package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.front.CompanyFront;
import com.tiandi.logistics.entity.pojo.Company;
import com.tiandi.logistics.mapper.CompanyMapper;
import com.tiandi.logistics.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司管理 服务实现类
 * </p>
 *
 * @author ZhanTianYi
 * @since 2020-11-25
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public Page<CompanyFront> getAllCompany(int page, int limit, String idCompany, String nameCompany) {
        Page<Company> page1 = new Page<>(page, limit);
        return companyMapper.getAllCompany(page1, idCompany, nameCompany);
    }

    @Override
    public int updateCompany(Company company) {
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_company", company.getIdCompany());
        int update = companyMapper.update(company, condition);
        return update;
    }

    @Override
    public int addCompany(Company company) {
        int insert = companyMapper.insert(company);
        return insert;
    }

    @Override
    public int deleteCompany(String id_company) {
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_company", id_company);
        int delete = companyMapper.delete(condition);
        return delete;
    }

    @Override
    public Integer getMaxID() {
        return companyMapper.getMaxID();
    }


}
