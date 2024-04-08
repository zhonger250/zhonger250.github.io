package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.SysIncomingInspectionMapper;
import com.example.entity.SysIncomingInspection;
import com.example.service.SysIncomingInspectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhonger250
 * @Date: 2024-04-08 12:12:19
 * @Description: (SysIncomingInspection)表服务实现类
 */
@Slf4j
@Service("sysIncomingInspectionService")
public class SysIncomingInspectionServiceImpl extends ServiceImpl<SysIncomingInspectionMapper, SysIncomingInspection> implements SysIncomingInspectionService {

}
