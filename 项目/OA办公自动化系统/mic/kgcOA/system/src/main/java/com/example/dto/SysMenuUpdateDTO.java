package com.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:28
 * @Description: (SysMenu)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@SuppressWarnings("serial")
public class SysMenuUpdateDTO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name = "id", value = "主键", dataType = "int")
    private String id;


    /**
     * 父级ID
     */
    @ApiModelProperty(name = "parent_id", value = "父级ID", dataType = "java.lang.Integer")
    private Integer parentId;


    /**
     * 标题
     */
    @ApiModelProperty(name = "title", value = "标题", dataType = "java.lang.String")
    private String title;


    /**
     * 请求路径
     */
    @ApiModelProperty(name = "path", value = "请求路径", dataType = "java.lang.String")
    private String path;


    /**
     * 组件
     */
    @ApiModelProperty(name = "component", value = "组件", dataType = "java.lang.String")
    private String component;


    /**
     * 图标
     */
    @ApiModelProperty(name = "icon", value = "图标", dataType = "java.lang.String")
    private String icon;


    /**
     * 排序
     */
    @ApiModelProperty(name = "sort", value = "排序", dataType = "java.lang.Integer")
    private Integer sort;


    /**
     * 类型
     */
    @ApiModelProperty(name = "type", value = "类型", dataType = "java.lang.Integer")
    private Integer type;


    /**
     * 权限标识
     */
    @ApiModelProperty(name = "permission", value = "权限标识", dataType = "java.lang.String")
    private String permission;


    /**
     * 版本
     */
    @ApiModelProperty(name = "version", value = "乐观锁", dataType = "int")
    private Integer version;
}
