package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.SysMenu;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:28
 * @Description: (SysMenu)表服务接口
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获得菜单下的子菜单的数量
     * @param menuParentId 父级菜单ID
     * @return 数量
     */
    int count(int menuParentId);

}
