package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.SysRole;

import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:57:08
 * @Description: (SysRole)表服务接口
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据角色id删除角色信息以及角色对应的菜单信息
     *
     * @param id 角色ID
     * @return
     */
    boolean removeById(int id);

    /**
     * 根据角色id删除角色信息以及角色对应的菜单信息
     *
     * @param ids 角色IDs
     * @return
     */
    boolean removeByIds(List<Integer> ids);
}
