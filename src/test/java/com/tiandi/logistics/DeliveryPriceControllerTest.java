package com.tiandi.logistics;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;
import com.tiandi.logistics.entity.pojo.DeliveryPriceRange;
import com.tiandi.logistics.entity.pojo.DeliveryPriceSpot;
import com.tiandi.logistics.entity.pojo.DistributionRange;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.mapper.DeliveryPriceMapper;
import com.tiandi.logistics.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SiSong Li
 * @version 1.0
 * @since 2020年12月07日, 0007 14:35
 */
@SpringBootTest
public class DeliveryPriceControllerTest {
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private DeliveryPriceService deliveryPriceService;
    @Autowired
    private DistributionService distributionService;
    @Autowired
    private DistributionRangeService distributionRangeService;
    @Autowired
    private DeliveryPriceRangeService deliveryPriceRangeService;
    @Autowired
    DeliveryPriceMapper deliveryPriceMapper;
    @Autowired
    DeliveryPriceSpotService deliveryPriceSpotService;


    @Test
    public void show(){
        /*根据配送点编码获取配送范围编码*/
        DistributionRange id_distribution1 = distributionRangeService.getBaseMapper().selectOne(new QueryWrapper<DistributionRange>().eq("id_distribution", "103"));
        Integer idRange = id_distribution1.getIdRange();
        System.out.println("配送范围编码："+idRange);//成功
        /*根据配送点编码获取配送价格编码*/
        DeliveryPriceSpot id_distribution2 = deliveryPriceSpotService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPriceSpot>().eq("id_distribution", "103"));
        String idDeliveryPrice = id_distribution2.getIdDeliveryPrice();
        System.out.println("配送价格编码1："+idDeliveryPrice);//成功

        String id = String.valueOf(idRange);
        /*根据配送范围编码获取配送价格编码*/
        DeliveryPriceRange id_range = deliveryPriceRangeService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPriceRange>().eq("id_range", id));
        String idDeliveryPrice1 = id_range.getIdDeliveryPrice();
        System.out.println("配送价格编码2："+idDeliveryPrice1);

        /*根据配送价格编码获取配送价格*/
        DeliveryPrice deliveryPrice = deliveryPriceService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPrice>().eq("id_delivery_price", idDeliveryPrice));
        Double firstKilogram = deliveryPrice.getFirstKilogram();
        System.out.println(firstKilogram);
        DeliveryPrice deliveryPrice1 = deliveryPriceService.getBaseMapper().selectOne(new QueryWrapper<DeliveryPrice>().eq("id_delivery_price", idDeliveryPrice1));
        System.out.println(deliveryPrice1.getSecondKilogram());
    }
}
