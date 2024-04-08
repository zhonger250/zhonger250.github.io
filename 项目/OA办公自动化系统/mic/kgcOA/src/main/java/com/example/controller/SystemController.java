package com.example.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import com.example.common.utils.JwtUtil;
import com.example.common.vo.Result;
import com.example.dto.LoginDTO;
import com.example.entity.RouterVO;
import com.example.entity.SysUser;
import com.example.service.SysMenuService;
import com.example.service.SysRoleMenuService;
import com.example.service.SysUserRoleService;
import com.example.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysUserService sysUserService;

    public static void main(String[] args) {
        String s = SecureUtil.md5("123456");
        System.out.println(s);
    }


    @PostMapping("/user/login")
    @ApiOperation(value = "登录接口")
    @CrossOrigin
    public Result login(@RequestBody LoginDTO loginDTO) {
        log.debug("登录" + loginDTO);
        // 密码
        String password = SecureUtil.md5(loginDTO.getPassword());

        // 查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, loginDTO.getAccount());
        queryWrapper.eq(SysUser::getPassword, password);
        SysUser one = sysUserService.getOne(queryWrapper);
        if (one == null) {
            // 如果没有找到, 就抛出登录异常
            throw new HttpException(ResultConstant.LOGIN_ERROR);
        }
        // 登录成功, 返回token
        String token = JwtUtil.createToken(String.valueOf(one.getId()), one.getAccount());


        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return Result.builder().code(20000).data(map).message("ok").build();
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息接口")
    @ApiImplicitParam(name = "token",value = "令牌", paramType = "header",dataType = "string")
    @CrossOrigin
    public Result info(@RequestHeader String token) {
        // 从请求头中获得令牌信息
//        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            throw new HttpException(ResultConstant.NO_LOGIN);
        }
        // 从令牌中获得用户ID
        String userId = JwtUtil.getUserId(token);
        // 获得用户的真实名字
        SysUser sysUser = sysUserService.getById(userId);
        // 根据用户传入的令牌, 获得此用户拥有的菜单组成菜单结构(进阶:组装成路由结构)  启用的
        List<RouterVO> routerVOList = null;
        if (userId != null) {
            routerVOList = sysMenuService.getMenuRouterVO(Integer.parseInt(userId));
        }


        // 根据用户传入的令牌, 获得此用户拥有的按钮上的权限表示符     启用的
        List<String> buttonPermission = null;
        if (userId != null) {
            buttonPermission = sysMenuService.getButtonPermission(Integer.parseInt(userId));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("roles", "['admin']");
        map.put("introduction", "'I am a super administrator'");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", sysUser.getRealName());
        map.put("routers",routerVOList);
        map.put("buttons",buttonPermission);

        //
        // 得到用户Id


        // map.put();


        return Result.builder().code(20000).data(map).build();
    }

    @PostMapping("/user/logout")
    @CrossOrigin
    @ApiOperation(value = "退出登录接口")
    public Result logout() {
        return Result.builder().code(20000).message("success").build();
    }

    @GetMapping("/get/token")
    @ApiOperation(value = "获得令牌")
    @CrossOrigin
    public Result<String> getToken() {
        String string = UUID.fastUUID().toString();
        return Result.<String>builder().code(2000).data(string).build();
    }
}
