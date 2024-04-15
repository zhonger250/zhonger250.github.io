package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SysDept;
import com.example.mapper.SysDeptMapper;
import com.example.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:56:55
 * @Description: (SysDept)表服务实现类
 */
@Slf4j
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

}
