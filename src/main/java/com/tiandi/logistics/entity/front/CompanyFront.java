package com.tiandi.logistics.entity.front;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/8 13:03
 */
@Data
@ApiModel(value="CompanyFront对象", description="前端返回实体类")
public class CompanyFront {

    @ApiModelProperty(value = "公司表主键")
    private Integer idTpCompany;

    @ApiModelProperty(value = "公司编号")
    private String idCompany;

    @ApiModelProperty(value = "公司名")
    private String nameCompany;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "配送点数量")
    private Integer disTotal;
}
