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
 * @Date: 2024-03-26 14:58:24
 * @Description: (SysUserRole)表实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@SuppressWarnings("serial")
public class SysUserRoleAddDTO implements Serializable {


    /**
     * 用户ID
     */
    @ApiModelProperty(name = "uid", value = "用户ID", dataType = "java.lang.Integer")
    private Integer uid;


    /**
     * 角色ID
     */
    @ApiModelProperty(name = "rid", value = "角色ID", dataType = "java.lang.Integer")
    private Integer rid;

}
