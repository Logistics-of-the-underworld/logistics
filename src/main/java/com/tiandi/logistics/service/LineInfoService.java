package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.front.LineInfoFront;
import com.tiandi.logistics.entity.pojo.LineInfo;

import java.util.List;

/**
 * <p>
 * 路线管理 服务类
 * </p>
 *
 * @author TP
 * @since 2020-12-2
 */
public interface LineInfoService extends IService<LineInfo> {
    /**
     * 分页查询
     * @param page 页码
     * @param limit 页数
     * @param start_distribution 起始站点ID
     * @param end_distribution 终点站点ID
     * @param id_line 路线ID
     * @return
     */
    IPage getAllRoute(int page, int limit, Integer start_distribution, Integer end_distribution, Integer id_line);

    /**
     * 增加路线
     * @param lineInfo 路线实体类
     * @return
     */
    int addRoute(LineInfo lineInfo);

    /**
     * 更新路线
     * @param lineInfo 路线实体
     * @return
     */
    int updateRoute(LineInfo lineInfo);

    /**
     * 删除路线
     * @param idLine 路线实体
     * @return
     */
    int deleteRoute(String idLine);

    /**
     * 获取路线地图
     * @return
     */
    List<LineInfoFront> getRoadMap();
}
