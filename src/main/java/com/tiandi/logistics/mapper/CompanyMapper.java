package com.tiandi.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiandi.logistics.entity.front.CompanyFront;
import com.tiandi.logistics.entity.pojo.Company;

/**
 * <p>
 * 公司管理 Mapper 接口
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface CompanyMapper extends BaseMapper<Company> {

    Page<CompanyFront> getAllCompany(Page<?> page, String idCompany, String nameCompany);

    Integer getMaxID();
}
