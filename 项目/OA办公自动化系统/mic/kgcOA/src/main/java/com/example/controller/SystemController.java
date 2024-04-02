package com.example.controller;

import cn.hutool.core.lang.UUID;
import com.example.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/3/22 17:26
 * @Description: 系统控制器
 */
@RestController
@RequestMapping("/vue-admin/system")
@Api(tags = "系统角色")
@Validated
@Slf4j
public class SystemController {

    @PostMapping("/user/login")
    @ApiOperation(value = "登录接口")
    @CrossOrigin
    public Result login(Object o){
        Map<String,String> map = new HashMap<>();
        map.put("token","admin-token");

        return Result.builder().code(20000).data(map).message("ok").build();
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息接口")
    @CrossOrigin
    public Result info(String token){
        Map<String,String> map = new HashMap<>();
//        roles: ['admin'],
//        introduction: 'I am a super administrator',
//        avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
        // name: 'Super Admin'
        map.put("roles","['admin']");
        map.put("introduction","'I am a super administrator'");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","'Super Admin'");
        return Result.builder().code(20000).data(map).build();
    }

    @PostMapping("/user/logout")
    @CrossOrigin
    @ApiOperation(value = "退出登录接口")
    public Result logout(){
        return Result.builder().code(20000).message("success").build();
    }

    @GetMapping("/get/token")
    @ApiOperation(value = "获得令牌")
    @CrossOrigin
    public Result<String> getToken(){
        String string = UUID.fastUUID().toString();
        return Result.<String>builder().code(2000).data(string).build();
    }
}
