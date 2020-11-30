package com.tiandi.logistics.aop.log.enumeration;

/**
 * 系统类型枚举类，供日志注解使用
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 10:09
 */
public enum SysTypeEnum {

    /**
     * 用户平台
     */
    CUSTOMER("客户平台", 0),

    /**
     * 配送点平台
     */
    ADMIN("管理平台",1),

    /**
     * 通用方法
     */
    NORMAL("通用方法",2);


    private String sysType;
    private int sysCode;

    SysTypeEnum(String sysType, int sysCode) {
        this.sysType = sysType;
        this.sysCode = sysCode;
    }

    public static String getMessage(int sysCode){
        //通过enum.values()获取所有的枚举值
        for(SysTypeEnum logOperateTypeEnum : SysTypeEnum.values()){
            //通过enum.get获取字段值
            if(logOperateTypeEnum.getSysCode() == sysCode){
                return logOperateTypeEnum.sysType;
            }
        }
        return null;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public int getSysCode() {
        return sysCode;
    }

    public void setSysCode(int sysCode) {
        this.sysCode = sysCode;
    }
}
