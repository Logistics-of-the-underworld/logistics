package com.tiandi.logistics.enumeration;

import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;

/**
 * 车辆状态枚举类
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/7 14:31
 */
public enum  VehicleStateEnum {

    /**
     * 车辆状态 空闲
     */
    READY("0", "空闲"),

    /**
     * 车辆状态 空闲
     */
    BUSY("1", "繁忙"),

    /**
     * 车辆状态 空闲
     */
    STOP("2", "停用");

    private String stateCode;
    private String stateName;

    VehicleStateEnum(String stateCode,String stateName) {
        this.stateCode = stateCode;
        this.stateName = stateName;
    }

    public static String getMessage(String stateCode){
        //通过enum.values()获取所有的枚举值
        for(VehicleStateEnum vehicleStateEnum : VehicleStateEnum.values()){
            //通过enum.get获取字段值
            if(vehicleStateEnum.getStateCode().equals(stateCode)){
                return vehicleStateEnum.stateName;
            }
        }
        return null;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
