package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.DistributionMenuDTO;
import com.example.entity.SysRoleMenu;

/**
 * @Author: zhonger250
 * @Date: 2024-04-02 09:32:03
 * @Description: (SysRoleMenu)表服务接口
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 给角色分配菜单权限
     * @param menuDTO 角色ID及菜单ID
     * @return 是否分配成功
     */
    boolean distributionMenu(DistributionMenuDTO menuDTO);
}
