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
 * @Date: 2024-03-26 14:58:06
 * @Description: (SysUser)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@SuppressWarnings("serial")
public class SysUserUpdateDTO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name = "id", value = "主键", dataType = "int")
    private String id;


    /**
     * 账号
     */
    @ApiModelProperty(name = "account", value = "账号", dataType = "java.lang.String")
    private String account;


    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码", dataType = "java.lang.String")
    private String password;


    /**
     * 部门编号
     */
    @ApiModelProperty(name = "dept_id", value = "部门编号", dataType = "java.lang.Integer")
    private Integer deptId;


    /**
     * 禁用/启用
     */
    @ApiModelProperty(name = "status", value = "禁用/启用", dataType = "java.lang.Integer")
    private Integer status;


    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone", value = "手机号", dataType = "java.lang.String")
    private String phone;


    /**
     * 头像
     */
    @ApiModelProperty(name = "avatar", value = "头像", dataType = "java.lang.String")
    private String avatar;


    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;


    /**
     * 姓名
     */
    @ApiModelProperty(name = "real_name", value = "姓名", dataType = "java.lang.String")
    private String realName;


    /**
     * 住址
     */
    @ApiModelProperty(name = "address", value = "住址", dataType = "java.lang.String")
    private String address;


    /**
     * 岗位
     */
    @ApiModelProperty(name = "job", value = "岗位", dataType = "java.lang.String")
    private String job;


    /**
     * 入职时间
     */
    @TableField(value = "hire_date")
    private Date hireDate;


    /**
     * 乐观锁
     */
    @ApiModelProperty(name = "version", value = "乐观锁", dataType = "int")
    private int version;
}
