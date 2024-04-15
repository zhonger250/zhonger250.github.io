package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@TableName(value = "sys_dept")
@SuppressWarnings("serial")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysDept extends Model<SysDept> implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;


    /**
     * 乐观锁
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;


}
