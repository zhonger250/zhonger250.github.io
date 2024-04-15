package com.example.dto;

import lombok.Data;

/**
 * @Author: zhonger250
 * @Date: 2024/4/5 14:10
 * @Description: 登录的参数
 */
@Data
public class LoginDTO {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
}
