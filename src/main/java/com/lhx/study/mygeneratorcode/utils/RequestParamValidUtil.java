package com.lhx.study.mygeneratorcode.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @Author: lhx
 * @Date: 2018/11/23 14:13
 * @Version 1.0
 */
public class RequestParamValidUtil {

    /**
     * 获取参数错误信息
     * @param bindingResult
     * @return
     */
    public static String getBindingResultMessage(BindingResult bindingResult){
        JSONObject json = new JSONObject();
        List<FieldError> allErrors = bindingResult.getFieldErrors();
        allErrors.forEach((x) -> {
            json.put(x.getField(), x.getDefaultMessage());
        });
        return json.toJSONString();
    }

}
