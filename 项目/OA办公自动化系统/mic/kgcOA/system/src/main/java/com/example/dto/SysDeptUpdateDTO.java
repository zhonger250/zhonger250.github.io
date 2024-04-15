package com.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:56:55
 * @Description: (SysDept)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@SuppressWarnings("serial")
public class SysDeptUpdateDTO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name = "id", value = "主键", dataType = "int")
    private String id;


    /**
     * 部门名称
     */
    @ApiModelProperty(name = "dept_name", value = "部门名称", dataType = "java.lang.String")
    private String deptName;

    /**
     * 版本
     */
    @ApiModelProperty(name = "version", value = "乐观锁", dataType = "int")
    private Integer version;


}
