package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024-03-28 14:27:27
 * @Description: (SysMenu)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "sys_menu")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysMenu extends Model<SysMenu> implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 父级ID
     */
    @TableField(value = "parent_id")
    private Integer parentId;


    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;


    /**
     * 请求路径
     */
    @TableField(value = "path")
    private String path;


    /**
     * 组件
     */
    @TableField(value = "component")
    private String component;


    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;


    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;


    /**
     * 类型
     */
    @TableField(value = "type")
    private Integer type;


    /**
     * 权限标识
     */
    @TableField(value = "permission")
    private String permission;


    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;


    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Integer createdBy;


    /**
     * 修改人
     */
    @TableField(value = "modify_by")
    private Integer modifyBy;


    /**
     * 创建时间
     */
    @TableField(value = "creation_date", fill = FieldFill.INSERT)
    @JsonIgnore
    private Date creationDate;


    /**
     * 修改时间
     */
    @TableField(value = "modify_date", fill = FieldFill.UPDATE)
    @JsonIgnore
    private Date modifyDate;


    /**
     * 乐观锁
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 菜单下的子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

    /**
     * 菜单是否被选中
     */
    @TableField(exist = false)
    private boolean isChecked;


}
