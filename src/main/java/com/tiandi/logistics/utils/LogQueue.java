package com.tiandi.logistics.utils;

import com.tiandi.logistics.entity.pojo.BusinessLog;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 日志队列，用于实现定期向数据库中添加日志信息
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 10:49
 */
@Component
public final class LogQueue {

    /**
     * LinkedList实现了Queue接口，可以用LinkedList做一个队列,这里使用阻塞队列BlockingQueue
     */
    private volatile Queue<BusinessLog> dataQueue = new LinkedBlockingQueue<>();

    /**
     * 添加日志信息
     * @param logininfor 一条日志记录
     */
    public void add(BusinessLog logininfor) {
        dataQueue.add(logininfor);
    }

    /**
     * 获取日志信息，用于插入到数据库中
     *
     * @return 日志实体
     */
    public BusinessLog poll() {
        return dataQueue.poll();
    }
}
