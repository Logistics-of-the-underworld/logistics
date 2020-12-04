package com.tiandi.logistics.entity.front;

import lombok.Data;

/**
 * @author kotori
 * @version 1.0
 * @date 2020/12/2 16:09
 * @description
 * 前端路线实体类
 */
@Data
public class LineInfoFront {
    /**
     * 起点名称
     */
    private String startDistribution;
    /**
     * 起点名称
     */
    private String endDistribution;
    /**
     * 起点经度
     */
    private Double startLongitude;
    /**
     * 起点纬度
     */
    private Double startLatitude;
    /**
     * 终点经度
     */
    private Double endLongitude;
    /**
     * 终点纬度
     */
    private Double endLatitude;
}
