package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SysRole;
import com.example.entity.SysRoleMenu;
import com.example.mapper.SysRoleMapper;
import com.example.service.SysRoleMenuService;
import com.example.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:57:08
 * @Description: (SysRole)表服务实现类
 */
@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(int id) {
        // 根据角色Id删除角色菜单中间表
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRid, id);
        sysRoleMenuService.remove(queryWrapper);
        // 根据角色ID删除角色信息

        this.baseMapper.deleteById(id);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Integer> ids) {

        for (int id : ids) {
            removeById(id);
        }
        return true;
    }
}
