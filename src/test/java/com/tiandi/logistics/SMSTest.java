package com.tiandi.logistics;

import com.tiandi.logistics.utils.RedisUtil;
import com.tiandi.logistics.utils.SMSSendingUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 14:29
 */
@SpringBootTest
public class SMSTest {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void test() throws Exception {
        redisUtil.set("17184030601",561505,60*5);
//        SMSSendingUtil.sendALiSms("15023421665","589641");
    }
}
