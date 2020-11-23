package com.tiandi.logistics.controller.exception;

import com.tiandi.logistics.entity.result.ResultMap;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理（权限认证及Token认证）
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 16:02
 */
@RestControllerAdvice
public class ExceptionAdviceController {

    private final ResultMap resultMap;

    @Autowired
    public ExceptionAdviceController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * 捕捉shiro的异常
     * @return
     */
    @ExceptionHandler(ShiroException.class)
    public ResultMap handle401() {
        resultMap.clear();
        return resultMap.fail().code(401).message("您没有权限访问！");
    }

    /**
     * 捕捉其他所有异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultMap globalException(HttpServletRequest request, Throwable ex) {
        resultMap.clear();
        return resultMap.fail()
                .code(getStatus(request).value())
                .message("访问出错，无法访问: " + ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
