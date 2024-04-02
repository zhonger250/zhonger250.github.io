package com.example.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:28
 * @Description: (SysMenu)表实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@SuppressWarnings("serial")
public class SysMenuAddDTO implements Serializable {


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
     * 权限标识
     */
    @ApiModelProperty(name = "permission", value = "权限标识", dataType = "java.lang.String")
    private String permission;
}
