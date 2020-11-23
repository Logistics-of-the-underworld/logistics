package com.tiandi.logistics.controller.exception;

import com.tiandi.logistics.entity.result.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:58
 */
@RestController
public class ExceptionController {

    @Autowired
    private ResultMap resultMap;

    @RequestMapping(path = "/unauthorized/{message}")
    public ResultMap unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        resultMap.clear();
        return resultMap.success().code(401).message(message);
    }

}
