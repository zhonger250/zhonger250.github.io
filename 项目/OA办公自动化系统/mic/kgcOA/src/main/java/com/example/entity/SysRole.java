package com.example.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:57:08
 * @Description: (SysRole)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "sys_role")
@SuppressWarnings("serial")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysRole extends Model<SysRole> implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;


    /**
     * 角色描述信息
     */
    @TableField(value = "description")
    private String description;


    /**
     * 创建时间
     */
    @TableField(value = "creation_date", fill = FieldFill.INSERT)
    @JsonIgnore
    private Date creationDate;


    /**
     * 创建人ID
     */
    @TableField(value = "created_by")
    private Integer createdBy;


    /**
     * 修改时间
     */
    @TableField(value = "modify_date", fill = FieldFill.UPDATE)
    @JsonIgnore
    private Date modifyDate;


    /**
     * 修改人ID
     */
    @TableField(value = "modify_by")
    private Integer modifyBy;


    /**
     * 乐观锁
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;


}
