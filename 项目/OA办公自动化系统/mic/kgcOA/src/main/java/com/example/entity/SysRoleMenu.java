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
 * @Date: 2024-04-02 09:32:03
 * @Description: (SysRoleMenu)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "sys_role_menu")
@SuppressWarnings("serial")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysRoleMenu extends Model<SysRoleMenu> implements Serializable {


    /**
     * 角色ID
     */
    @TableField(value = "rid")
    private Integer rid;


    /**
     * 菜单ID
     */
    @TableField(value = "mid")
    private Integer mid;

}
