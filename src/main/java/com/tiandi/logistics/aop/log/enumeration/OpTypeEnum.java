package com.tiandi.logistics.aop.log.enumeration;

/**
 * 操作类型枚举类，供日志注解使用
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 10:21
 */
public enum OpTypeEnum {

    /**
     * 登陆
     */
    LOGIN("登陆",0),

    /**
     * 新增
     */
    ADD("新增",1),

    /**
     * 修改
     */
    UPDATE("修改",2),

    /**
     * 删除
     */
    DELETE("删除",3),

    /**
     * 查询
     */
    SELECT("查询",4);

    private String opType;
    private int opCode;

    OpTypeEnum(String opType,int opCode) {
        this.opType = opType;
        this.opCode = opCode;
    }

    public static String getMessage(int opCode){
        //通过enum.values()获取所有的枚举值
        for(OpTypeEnum logOperateTypeEnum : OpTypeEnum.values()){
            //通过enum.get获取字段值
            if(logOperateTypeEnum.getOpCode() == opCode){
                return logOperateTypeEnum.opType;
            }
        }
        return null;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }
}
