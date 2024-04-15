package com.example.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhonger250
 * @Date: 2024-03-26 14:58:06
 * @Description: (SysUser)表实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@SuppressWarnings("serial")
public class SysUserAddDTO implements Serializable {


    /**
     * 账号
     */
    @ApiModelProperty(name = "account", value = "账号", dataType = "java.lang.String")
    private String account;


    /**
     * 部门编号
     */
    @ApiModelProperty(name = "dept_id", value = "部门编号", dataType = "java.lang.Integer")
    private Integer deptId;


    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone", value = "手机号", dataType = "java.lang.String")
    private String phone;


//    /**
//     * 头像
//     */
//    @ApiModelProperty(name = "avatar", value = "头像", dataType = "java.lang.String")
//    private String avatar;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;
}
