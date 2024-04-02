package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.constant.SystemConstant;
import com.example.common.utils.MenuUtil;
import com.example.dto.RoleMenuDTO;
import com.example.entity.SysMenu;
import com.example.entity.SysRoleMenu;
import com.example.mapper.SysMenuMapper;
import com.example.service.SysMenuService;
import com.example.service.SysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:28
 * @Description: (SysMenu)表服务实现类
 */
@Slf4j
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public int count(int menuParentId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, menuParentId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<SysMenu> getMenuListByRoleID(int roleId) {
        // 查询所有菜单信息
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getStatus, SystemConstant.SysMenuStatus.ENABLE.getCode());
        List<SysMenu> sysMenuList = baseMapper.selectList(queryWrapper);
        // 根据角色ID 查询角色对应的菜单信息(sys_role_menu)
        LambdaQueryWrapper<SysRoleMenu> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(SysRoleMenu::getRid, roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(queryWrapper2);
        // 遍历角色菜单信息集合, 如果角色拥有某个菜单, 所有菜单集合中的对应的菜单就应该被选中
        for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
            for (SysMenu sysMenu : sysMenuList) {
                if (Objects.equals(sysMenu.getId(), sysRoleMenu.getMid())) {
                    sysMenu.setChecked(true);
                }
            }
        }
        // 将处理后的菜单信息树化

        return MenuUtil.tree(sysMenuList);
    }

    @Transactional()
    @Override
    public boolean doAssignMenu(RoleMenuDTO roleMenuDTO) {
        // 根据角色Id删除角色原有的菜单信息
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRid, roleMenuDTO.getRoleId());
        sysRoleMenuService.remove(queryWrapper);

        // 添加角色对应的菜单的信息
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();
        List<Integer> menuIds = roleMenuDTO.getMenuIds();
        for (Integer menuId : menuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMid(menuId);
            sysRoleMenu.setRid(roleMenuDTO.getRoleId());
            sysRoleMenuList.add(sysRoleMenu);
        }
        return sysRoleMenuService.saveBatch(sysRoleMenuList);
    }
}
