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
 * @Date: 2024-03-22 16:13:41
 * @Description: (SysRole)表实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@SuppressWarnings("serial")
public class SysRoleAddDTO implements Serializable {


    /**
     * 角色名称
     */
    @ApiModelProperty(name = "role_name", value = "角色名称", dataType = "java.lang.String")
    private String roleName;


    /**
     * 角色描述信息
     */
    @ApiModelProperty(name = "description", value = "角色描述信息", dataType = "java.lang.String")
    private String description;
}
