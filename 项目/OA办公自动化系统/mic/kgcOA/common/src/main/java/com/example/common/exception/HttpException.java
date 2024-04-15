package com.example.common.exception;

import com.example.common.constant.ResultConstant;

/**
 * @author Dxr
 * 异常:
 * 运行时异常(RuntimeException): 某行代码可能产生运行时异常, 不强制我们必须对运行时异常进行处理
 * 检查异常(CheckedException): 如果某行代码可能产生检查异常, 对异常进行处理.
 *
 * 如何自定义异常:
 * 1.继承的时RuntimeException, 创建的自定义异常就是一个运行时异常
 * 2.继承Exception, 创建的自定义异常就是一个检查异常
 *
 * 继承RuntimeException, 创建自定义运行时异常. 无论时运行时异常还是检查异常, 都需要在程序中进行异常处理.
 *
 * 创建的自定义异常的都必须是HttpException的子类.
 */
public class HttpException extends RuntimeException{
    /**
     * 异常码
     */
    private int code;

    public HttpException(ResultConstant resultConstant){
        super(resultConstant.getMessage());
        this.code=resultConstant.getCode();
    }

    public int getCode(){
        return this.code;
    }

}
