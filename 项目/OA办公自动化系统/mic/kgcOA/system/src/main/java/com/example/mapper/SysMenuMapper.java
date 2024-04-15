package com.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:27
 * @Description: (SysMenu)表数据库访问层
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
