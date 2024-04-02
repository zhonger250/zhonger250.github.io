package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.SysDept;
import com.example.dto.SysDeptAddDTO;
import com.example.dto.SysDeptUpdateDTO;
import com.example.service.SysDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.SysDept;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:56:55
 * @Description: (SysDept)表控制层
 */
@RestController
@RequestMapping("/vue-admin/system/dept")
@Api
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
    public List<SysDept> selectAll() {
        List<SysDept> list = sysDeptService.list();
        list.add(0,SysDept.builder().id(0).deptName("请选择部门").build());
        return list;
    }

    /**
     * 分页查询所有数据
     *
     * @param currentPage 当前页
     * @param pageSize    每页显示条数
     * @return 分页的数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", defaultValue = "5", dataType = "int", paramType = "query")
    })
    @CrossOrigin
    public IPage<SysDept> selectPage(@RequestParam(defaultValue = "1") int currentPage,
                                     @RequestParam(defaultValue = "5") int pageSize) {
        IPage<SysDept> page = new Page<>(currentPage, pageSize);

        return sysDeptService.page(page);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    @CrossOrigin
    @ApiOperation(value = "查询单条数据")
    @ApiImplicitParam(name = "id", value = "主键", paramType = "path", dataType = "string")
    public SysDept selectOne(@PathVariable Serializable id) {
        return this.sysDeptService.getById(id);
    }

    /**
     * 新增数据
     *
     * @param sysDeptDTO 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    @ApiOperation(value = "新增数据")
    @CrossOrigin
    public boolean insert(@RequestBody SysDeptAddDTO sysDeptDTO) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(sysDeptDTO, sysDept);
        return this.sysDeptService.save(sysDept);
    }

    /**
     * 修改数据
     *
     * @param sysDeptDTO 实体对象
     * @return 修改结果
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改数据")
    @CrossOrigin
    public boolean update(@RequestBody SysDeptUpdateDTO sysDeptDTO) {
        SysDept sysDept = this.sysDeptService.getById(sysDeptDTO.getId());
        BeanUtils.copyProperties(sysDeptDTO, sysDept);
        return this.sysDeptService.updateById(sysDept);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "根据主键删除信息")
    @ApiImplicitParam(name = "id", value = "主键", paramType = "path", dataType = "string")
    @CrossOrigin
    public boolean delete(@PathVariable String id) {
        return this.sysDeptService.removeById(id);
    }
}
