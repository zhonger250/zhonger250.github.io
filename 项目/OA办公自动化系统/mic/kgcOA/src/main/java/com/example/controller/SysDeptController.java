package com.example.controller;

import com.example.entity.SysDept;
import com.example.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:56:55
 * @Description: (SysDept)表控制层
 */
@RestController
@RequestMapping("/vue-admin/system/dept")
@Api(tags = "部门模块")
@Validated
@Slf4j
public class SysDeptController {
    /**
     * 服务对象
     */
    @Resource
    private SysDeptService sysDeptService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询所有数据")
    @CrossOrigin
    @PreAuthorize("hasAuthority('sysDept::list')")
    public List<SysDept> selectAll() {
        List<SysDept> list = sysDeptService.list();
        list.add(0,SysDept.builder().id(0).deptName("请选择部门").build());
        return list;
    }
}
