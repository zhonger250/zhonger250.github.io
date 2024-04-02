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
 * @Date: 2024-04-02 09:32:03
 * @Description: (SysRoleMenu)表实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@SuppressWarnings("serial")
public class SysRoleMenuAddDTO implements Serializable {


    /**
     * 角色ID
     */
    @ApiModelProperty(name = "rid", value = "角色ID", dataType = "java.lang.Integer")
    private Integer rid;


    /**
     * 菜单ID
     */
    @ApiModelProperty(name = "mid", value = "菜单ID", dataType = "java.lang.Integer")
    private Integer mid;

}
