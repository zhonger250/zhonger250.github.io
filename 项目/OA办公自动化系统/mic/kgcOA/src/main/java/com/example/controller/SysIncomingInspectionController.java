package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.SysIncomingInspection;
import com.example.dto.SysIncomingInspectionAddDTO;
import com.example.dto.SysIncomingInspectionUpdateDTO;
import com.example.service.SysIncomingInspectionService;
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
import com.example.entity.SysIncomingInspection;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhonger250
 * @Date: 2024-04-08 12:12:18
 * @Description: (SysIncomingInspection)表控制层
 */
@RestController
@RequestMapping("/sysIncomingInspection")
@Api
@Validated
@Slf4j
public class SysIncomingInspectionController {
    /**
     * 服务对象
     */
    @Resource
    private SysIncomingInspectionService sysIncomingInspectionService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询所有数据")
    @CrossOrigin
    public List<SysIncomingInspection> selectAll() {
        return sysIncomingInspectionService.list();
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
    public IPage<SysIncomingInspection> selectPage(@RequestParam(defaultValue = "1") int currentPage,
                                                   @RequestParam(defaultValue = "5") int pageSize) {
        IPage<SysIncomingInspection> page = new Page<>(currentPage, pageSize);

        return sysIncomingInspectionService.page(page);
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
    public SysIncomingInspection selectOne(@PathVariable Serializable id) {
        return this.sysIncomingInspectionService.getById(id);
    }

    /**
     * 新增数据
     *
     * @param sysIncomingInspectionDTO 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    @ApiOperation(value = "新增数据")
    @CrossOrigin
    public boolean insert(@RequestBody SysIncomingInspectionAddDTO sysIncomingInspectionDTO) {
        SysIncomingInspection sysIncomingInspection = new SysIncomingInspection();
        BeanUtils.copyProperties(sysIncomingInspectionDTO, sysIncomingInspection);
        return this.sysIncomingInspectionService.save(sysIncomingInspection);
    }

    /**
     * 修改数据
     *
     * @param sysIncomingInspectionDTO 实体对象
     * @return 修改结果
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改数据")
    @CrossOrigin
    public boolean update(@RequestBody SysIncomingInspectionUpdateDTO sysIncomingInspectionDTO) {
        SysIncomingInspection sysIncomingInspection = this.sysIncomingInspectionService.getById(sysIncomingInspectionDTO.getId());
        BeanUtils.copyProperties(sysIncomingInspectionDTO, sysIncomingInspection);
        return this.sysIncomingInspectionService.updateById(sysIncomingInspection);
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
        return this.sysIncomingInspectionService.removeById(id);
    }
}
