package com.example.common.vo;

import com.example.common.constant.ResultConstant;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SuppressWarnings("serial")
public class Result<T> implements Serializable {

    private int code;            //返回的编码
    private String message;      //返回的信息
    private T data;              //返回的数据

    //处理成功时返回的Result对象
    public static Result success() {
        return Result.builder().code(ResultConstant.SUCCESS.getCode()).message(ResultConstant.SUCCESS.getMessage()).build();
    }

    //处理成功时返回的Result对象
    public static Result success(Object data) {
        return Result.builder().code(ResultConstant.SUCCESS.getCode())
                .message(ResultConstant.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    //处理失败返回的结果
    public static Result fail() {
        return Result.builder().code(ResultConstant.FAIL.getCode())
                .message(ResultConstant.FAIL.getMessage())
                .build();
    }
}

