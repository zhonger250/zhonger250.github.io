package com.example.common.utils;

import com.example.entity.SysMenu;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: zhonger250
 * @Date: 2024/4/2 15:29
 * @Description: 菜单工具类
 */
public class MenuUtil {

    /**
     * 将菜单信息转为树形结构
     *
     * @param sysMenuList 菜单信息
     * @return 树形结构的菜单信息
     */
    public static List<SysMenu> tree(List<SysMenu> sysMenuList) {
        List<SysMenu> parentSysMenus = sysMenuList.stream().filter(sysMenu -> sysMenu.getParentId() == 0).collect(Collectors.toList());
        // 遍历上级的菜单, 找到父菜单的子菜单
        for (SysMenu parentSysMenu : parentSysMenus) {
            getChildren(parentSysMenu, sysMenuList);
        }
        return parentSysMenus;
    }

    /**
     * 从SysMenuList中找到parentMenu的子菜单
     *
     * @param parentMenu
     * @param sysMenuList
     */
    private static void getChildren(SysMenu parentMenu, List<SysMenu> sysMenuList) {
        for (SysMenu menu : sysMenuList) {
            // 如果菜单中的Id等于父菜单的ID
            if (Objects.equals(menu.getParentId(), parentMenu.getId())) {
                // 菜单就是父菜单的子菜单
                parentMenu.getChildren().add(menu);
                // 找子菜单的子菜单
                getChildren(menu, sysMenuList);
            }
        }
    }
}
