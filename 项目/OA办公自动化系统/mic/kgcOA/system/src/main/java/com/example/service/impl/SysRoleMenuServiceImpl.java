package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SysRoleMenu;
import com.example.mapper.SysRoleMenuMapper;
import com.example.service.SysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: zhonger250
 * @Date: 2024-04-02 09:32:03
 * @Description: (SysRoleMenu)表服务实现类
 */
@Slf4j
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

}
