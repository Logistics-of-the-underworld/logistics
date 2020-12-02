package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.pojo.LineInfo;

/**
 * <p>
 * 路线管理 服务类
 * </p>
 *
 * @author TP
 * @since 2020-12-2
 */
public interface LineInfoService extends IService<LineInfo> {
    IPage getAllRoute(int page, int limit, Integer start_distribution, Integer end_distribution, Integer id_line);
    int addRoute(LineInfo lineInfo);
    int updateRoute(LineInfo lineInfo);
    int deleteRoute(String idLine);
}
