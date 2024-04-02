package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.DistributionMenuDTO;
import com.example.mapper.SysRoleMenuMapper;
import com.example.entity.SysRoleMenu;
import com.example.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024-04-02 09:32:03
 * @Description: (SysRoleMenu)表服务实现类
 */
@Slf4j
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean distributionMenu(DistributionMenuDTO menuDTO) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        // 根据角色ID删除角色原来拥有的菜单
        queryWrapper.eq(SysRoleMenu::getRid,menuDTO.getRoleId());
        baseMapper.delete(queryWrapper);
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();
        // 添加角色拥有的最新菜单
        for (Integer menuId : menuDTO.getMenuIds()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRid(menuDTO.getRoleId());
            sysRoleMenu.setMid(menuId);
            sysRoleMenuList.add(sysRoleMenu);
        }
        saveBatch(sysRoleMenuList);
        return true;
    }
}
