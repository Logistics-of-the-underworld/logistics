package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SiSong Li
 * @version 1.0
 * @since 2020年12月08日, 0008 21:53
 */
@Data
@TableName("tp_package")
@ApiModel(value="HandoverPackage对象", description="封装货号")
public class HandoverPackage{
    private static final long serialVersionUID = 1L;
    @TableId(value = "id_tp_package", type = IdType.AUTO)
    private Integer idTpPackage;
    @ApiModelProperty(value = "封装贷号编码")
    private String idPackage;
    @ApiModelProperty(value = "订单编号")
    private String idOrder;
    @ApiModelProperty(value = "交接单号")
    private String idHandoverOrder;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete = 0;
}
