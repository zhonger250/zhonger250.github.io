package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import com.example.mapper.SysMenuMapper;
import com.example.entity.SysMenu;
import com.example.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:28
 * @Description: (SysMenu)表服务实现类
 */
@Slf4j
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public int count(int menuParentId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, menuParentId);
        return baseMapper.selectCount(queryWrapper);
    }
}
