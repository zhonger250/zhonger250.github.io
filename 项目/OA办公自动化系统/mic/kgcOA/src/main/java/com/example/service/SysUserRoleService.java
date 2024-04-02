package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.DistributionRoleDTO;
import com.example.entity.SysUserRole;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:58:24
 * @Description: (SysUserRole)表服务接口
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 给用户分配角色
     * @param roleDTO 用户ID及角色ID
     * @return 是否分配成功
     */
    boolean distributionRole(DistributionRoleDTO roleDTO);

}
