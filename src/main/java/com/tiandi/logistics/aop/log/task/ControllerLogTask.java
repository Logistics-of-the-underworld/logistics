package com.tiandi.logistics.aop.log.task;

import com.tiandi.logistics.entity.pojo.BusinessLog;
import com.tiandi.logistics.service.LogService;
import com.tiandi.logistics.utils.LogQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 启动queue处理，定时将控制层日志批量插入到数据库
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 11:20
 */
@Slf4j
@Component
public class ControllerLogTask {

    @Autowired
    private LogQueue logQueue;
    @Autowired
    private LogService logService;

    private volatile List<BusinessLog> operationBusinessLogs = Collections.synchronizedList(new ArrayList<>());

    @Scheduled(fixedDelay = 500)
    public void logFixDelay(){
        //获取日志信息
        while (true){
            BusinessLog operBusinessLog = logQueue.poll();
            if(null== operBusinessLog){
                break;
            }

            operationBusinessLogs.add(operBusinessLog);
        }
        if(operationBusinessLogs.size()>0){
            try{
                log.info("================> 批量添加系统日志 "+ operationBusinessLogs.size() + " 条");
                logService.saveBatch(operationBusinessLogs);
            }catch (Exception e){
                log.error("批量添加系统日志异常：",e);
                operationBusinessLogs.clear();
            }
            operationBusinessLogs.clear();
        }
    }
}
