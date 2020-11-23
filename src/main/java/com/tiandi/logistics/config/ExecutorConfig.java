package com.tiandi.logistics.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * 线程池配置类，用于通用线程池配置
 *
 * 	自动注解方法：
 * 		@Resource(name = "createThreadPool")
 *       private ExecutorService getInfoPool;
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:31
 */
@Slf4j
@Configuration
@EnableAsync
public class ExecutorConfig {
    /**
     * 核心线程数
     */
    private int corePoolSize = 5;
    /**
     * 最大线程数
     */
    private int maxPoolSize = Integer.MAX_VALUE;
    /**
     * 线程销毁时间
     */
    private Long keepAliveTime = 60L;
    /**
     * 任务队列
     */
    private SynchronousQueue synchronousQueue = new SynchronousQueue<>();
    /**
     * 线程名称
     */
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("datas-thread-%d").build();

    @Bean
    @Scope("prototype")
    public ExecutorService createThreadPool() {
        log.info("线程池创建===>开始");
        /**
         * 创建线程池
         * Runtime.getRuntime().availableProcessors()
         */
        ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, synchronousQueue, namedThreadFactory);
        log.info("线程池创建===>结束");
        return threadPool;
    }
}
