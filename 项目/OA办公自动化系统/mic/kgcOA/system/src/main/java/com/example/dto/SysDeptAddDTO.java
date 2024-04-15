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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@SuppressWarnings("serial")
public class SysDeptAddDTO implements Serializable {


    /**
     * 部门名称
     */
    @ApiModelProperty(name = "dept_name", value = "部门名称", dataType = "java.lang.String")
    private String deptName;


}
