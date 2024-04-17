package com.example.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.entity.CustomerUser;
import com.example.entity.SysUserRole;
import com.example.service.SysUserRoleService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/17 12:16
 * @Project: mic
 * @Description: 权限工具类
 */
@Component
public class SecurityUtil {


    @Resource
    private SysUserRoleService sysUserRoleService;

    public String getLoginUserId() {
        // 获取登录用户ID
        CustomerUser customerUser = (CustomerUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 登录用户ID
        Integer userId = customerUser.getSysUser().getId();
        return String.valueOf(userId);
    }

    public String[] getRoleId(String userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUid,userId);
        // 根据用户ID获得角色ID集合
        List<SysUserRole> list = sysUserRoleService.list(queryWrapper);
        return list.stream().map(SysUserRole::getRid).toArray(String[]::new);
    }
}
