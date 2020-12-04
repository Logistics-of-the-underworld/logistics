package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.Company;

/**
 * <p>
 * 公司管理 服务类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface CompanyService extends IService<Company> {

    /**
     * 分页查询
     * @param page
     * @param limit
     * @param idCompany
     * @param nameCompany
     * @return
     */
    IPage getAllCompany(int page, int limit, String idCompany, String nameCompany);
}
