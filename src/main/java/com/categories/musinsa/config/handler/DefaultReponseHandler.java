package com.categories.musinsa.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;


/*.*.* Success *.*.*/
@Slf4j
@ControllerAdvice("com.categories.musinsa.controller")
public class DefaultReponseHandler implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // response common
        Map responseData = new HashMap();
        responseData.put("code" , 1);
        responseData.put("message" , "success");
        responseData.put("contents" , body);

        return responseData;
    }
}
