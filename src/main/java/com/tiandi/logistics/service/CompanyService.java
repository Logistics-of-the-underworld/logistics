package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.Company;

/**
 * <p>
 * 公司管理 服务类
 * </p>
 *
 * @author ZhanTianYi
 * @since 2020-11-25
 */
public interface CompanyService extends IService<Company> {

    /**
     * 分页查询
     * @param page  页码
     * @param limit  页数
     * @param idCompany  公司编号
     * @param nameCompany  公司名
     * @return
     */
    IPage getAllCompany(int page, int limit, String idCompany, String nameCompany);

    /**
     * 更新公司信息
     * @param company  公司实体
     * @return
     */
    int updateCompany(Company company);

    /**
     * 添加公司
     * @param company  公司实体
     * @return
     */
    int addCompany(Company company);

    /**
     * 删除公司
     * @param idCompany  公司实体
     * @return
     */
    int deleteCompany(String idCompany);

    Integer getMaxID();
}
