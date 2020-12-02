package com.tiandi.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiandi.logistics.entity.front.LineInfoFront;
import com.tiandi.logistics.entity.pojo.LineInfo;
import com.tiandi.logistics.mapper.LineInfoMapper;
import com.tiandi.logistics.service.LineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 路线管理 服务实现类
 * </p>
 *
 * @author TP
 * @since 2020-12-2
 */
@Service
public class LineInfoServiceImpl extends ServiceImpl<LineInfoMapper, LineInfo> implements LineInfoService {
    @Autowired
    private LineInfoMapper lineInfoMapper;

    @Override
    public IPage getAllRoute(int page, int limit, Integer start_distribution, Integer end_distribution, Integer id_line) {
        IPage<LineInfo> LineInfoPage = new Page<>(page, limit);
        final QueryWrapper condition = new QueryWrapper();
        if (start_distribution != null){
            condition.eq("start_distribution",start_distribution);
        }
        if (end_distribution != null){
            condition.eq("end_distribution",end_distribution);
        }
        if (id_line != null){
            condition.eq("id_line",id_line);
        }
        final IPage<LineInfo> lineInfoIPage = lineInfoMapper.selectPage(LineInfoPage, condition);
        return lineInfoIPage;
    }

    @Override
    public int addRoute(LineInfo lineInfo) {
        int insert = lineInfoMapper.insert(lineInfo);
        return insert;
    }

    @Override
    public int updateRoute(LineInfo lineInfo) {
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_line",lineInfo.getIdLine());
        int update = lineInfoMapper.update(lineInfo, condition);
        return update;
    }

    @Override
    public int deleteRoute(String idLine) {
        QueryWrapper condition = new QueryWrapper();
        condition.eq("id_line",idLine);
        int delete = lineInfoMapper.delete(condition);
        return delete;
    }

    @Override
    public List<LineInfoFront> getRoadMap() {
        List<LineInfoFront> roadsList = lineInfoMapper.getRoadMap();
        return roadsList;
    }
}
