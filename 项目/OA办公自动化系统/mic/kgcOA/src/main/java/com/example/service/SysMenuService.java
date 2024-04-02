package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.RoleMenuDTO;
import com.example.entity.SysMenu;

import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:28
 * @Description: (SysMenu)表服务接口
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获得菜单下的子菜单的数量
     *
     * @param menuParentId 父级菜单ID
     * @return 数量
     */
    int count(int menuParentId);
    /**
     * 1.查询出所有的菜单信息,
     * 2.再根据角色ID查询角色对应的ID,
     * 3.如果角色拥有某个菜单,对应的所有菜单集合中的菜单的状态是被选中的
     * 4.组成一个树形结构的json格式的数据
     * @param roleId 角色ID
     */
    List<SysMenu> getMenuListByRoleID(int roleId);

    /**
     * 给角色分配菜单
     * @param roleMenuDTO 角色和菜单信息
     * @return 是否分配成功
     */
    boolean doAssignMenu(RoleMenuDTO roleMenuDTO);

}
