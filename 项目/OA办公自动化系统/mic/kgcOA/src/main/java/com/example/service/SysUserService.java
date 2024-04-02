package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.SysUser;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:58:06
 * @Description: (SysUser)表服务接口
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户编号删除用户信息
     * @param id 用户编号
     * @return 是否成功
     */
    boolean removeById(Integer id);
}
