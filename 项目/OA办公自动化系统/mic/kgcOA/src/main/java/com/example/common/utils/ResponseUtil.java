package com.example.common.utils;

import com.example.common.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zhonger250
 * @Date: 2024/4/8 16:38
 * @Description: 在任何方法中返回Result类的json格式的字符串(只要HttpServletResponse对象)
 */
public class ResponseUtil {

    /**
     * 将result对象的json格式数据 放入到响应体中
     *
     * @param response
     * @param result
     */
    public static void out(HttpServletResponse response, Result result) {
        // 服务器端正常返回数据
        response.setStatus(HttpStatus.OK.value());
        // 返回json格式数据
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 将Result对象转为json格式的数据, 将其放入到响应体中
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(response.getOutputStream(), result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
