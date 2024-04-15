package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

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
