package com.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:56:55
 * @Description: (SysDept)表数据库访问层
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

}
