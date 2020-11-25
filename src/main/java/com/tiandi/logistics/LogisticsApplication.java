package com.tiandi.logistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:25
 */
@SpringBootApplication
@MapperScan("com.tiandi.logistics.mapper")
@EnableAsync
public class LogisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsApplication.class, args);
    }

}
