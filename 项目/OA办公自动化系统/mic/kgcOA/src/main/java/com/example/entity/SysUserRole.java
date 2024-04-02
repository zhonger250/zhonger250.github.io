package com.example.entity;

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
 * @Date: 2024-03-26 14:58:24
 * @Description: (SysUserRole)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "sys_user_role")
@SuppressWarnings("serial")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysUserRole extends Model<SysUserRole> implements Serializable {


    /**
     * 用户ID
     */
    @TableField(value = "uid")
    private Integer uid;


    /**
     * 角色ID
     */
    @TableField(value = "rid")
    private Integer rid;

}
