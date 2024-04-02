package com.example.common.advice;

import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import com.example.common.vo.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author 丁祥瑞
 * 控制器的异常的处理: 使用@ExceptionHandler注解处理异常
 * <p>
 * 使用的时@ExceptionHandler注解处理异常
 * <p>
 * 面试题:
 * SpringBoot程序如何去处理异常?
 * 使用ExceptionHandler注解将异常信息封装成Result对象返回给前端.
 */
//@ControllerAdvice("com.example.controller")
public class ExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 1.响应编码 200
        HttpStatus httpStatus = HttpStatus.resolve(HttpStatus.OK.value());
        // 2.封装信息
        // 从异常对象中获取每个参数错误信息
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        //遍历所有的错误信息
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            errorMessage.append(fieldError.getDefaultMessage()).append(",");
        }
        // 如果字段上,没有错误,直接获得异常信息
        if (StringUtils.isEmpty(errorMessage.toString())){
            errorMessage = new StringBuilder(e.getMessage());
            String[] msg = errorMessage.toString().split(";");
            errorMessage = new StringBuilder(msg[msg.length - 1]);
        }

        // 封装Result信息
        Result result = Result.builder()
                .code(ResultConstant.PARAM_ERROR.getCode())
                .message(errorMessage.toString()).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity responseEntity = new ResponseEntity(result,headers,httpStatus);
        return responseEntity;
    }






    /**
     * 处理JSR303参数验证失败的异常
     * @param e JSR303验证失败异常
     * @return 响应信息
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {

        HttpStatus httpStatus = HttpStatus.resolve(HttpStatus.OK.value());
        // 封装错误的Result信息
        String message = e.getMessage();
        String[] strs = message.split(":");
        String paramName = strs[0].split("\\.")[1];
        String errorMessage = strs[1];
        // 返回的错误信息
        String  resultMessage = "参数验证失败, 参数名: " + paramName + ", 错误信息: " + errorMessage;
        Result result = Result.builder().code(ResultConstant.PARAM_ERROR.getCode())
                .message(resultMessage).build();
        // 响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 封装返回的响应实体
        ResponseEntity responseEntity = new ResponseEntity(result, headers, httpStatus);
        return responseEntity;
    }









    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity handleHttpException(HttpException httpException) {
        // 获取异常状态码? 状态码给多少? 不能直接使用HttpException中的状态码, HttpException转状态码的值超出HttpStatus的枚举数
        // 给默认值数: HttpStatus.OK(200)   后端将请求处理完毕, 返回错误信息.
        HttpStatus httpStatus = HttpStatus.OK;

        // 将异常信息封装到Result对象中
        Result result = Result.builder().code(httpException.getCode())
                .message(httpException.getMessage()).build();

        // 响应头: 返回数据格式
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 响应(服务器返回数据)实体
        // 封装返回的响应实体
        ResponseEntity responseEntity = new ResponseEntity(result, headers, httpStatus);

        return responseEntity;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleHttpException(Exception exception) {
        // 获取异常状态码? 状态码给多少? 不能直接使用HttpException中的状态码, HttpException转状态码的值超出HttpStatus的枚举数
        // 给默认值数: HttpStatus.OK(200)   后端将请求处理完毕, 返回错误信息.
        HttpStatus httpStatus = HttpStatus.OK;

        int code = ResultConstant.ERROR.getCode();
        String message = ResultConstant.ERROR.getMessage();
        // 将异常信息封装到Result对象中
        Result result = Result.builder().code(code).message(message).build();

        // 响应头: 返回数据格式
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 响应(服务器返回数据)实体
        // 封装返回的响应实体
        ResponseEntity responseEntity = new ResponseEntity(result, headers, httpStatus);

        return responseEntity;
    }
}
