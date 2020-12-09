package com.tiandi.logistics.entity.front;

import com.tiandi.logistics.entity.pojo.BaseEntity;
import lombok.Data;

/**
 * @author SiSong Li
 * @version 1.0
 * @since 2020年12月09日, 0009 16:08
 */
@Data
public class AddHandover extends BaseEntity {
    private Integer idTpHandoverSlip;
    private String idHandoverOrder;
    private String idPackage;
    private String statSpot;
    private String handoverSpot;
    private String receiverName;
    private String idOrder;
    private Integer isDelete;

}
