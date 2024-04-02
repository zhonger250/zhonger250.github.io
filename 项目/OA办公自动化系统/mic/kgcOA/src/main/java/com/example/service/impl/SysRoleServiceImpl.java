package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.SysRoleMapper;
import com.example.entity.SysRole;
import com.example.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:57:08
 * @Description: (SysRole)表服务实现类
 */
@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
