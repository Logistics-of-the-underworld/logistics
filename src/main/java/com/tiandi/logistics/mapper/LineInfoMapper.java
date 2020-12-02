package com.tiandi.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiandi.logistics.entity.front.LineInfoFront;
import com.tiandi.logistics.entity.pojo.LineInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author kotori
 * @version 1.0
 * @date 2020/12/2 09:48
 * @description
 */
public interface LineInfoMapper extends BaseMapper<LineInfo> {
    List<LineInfoFront> getRoadMap();
}
