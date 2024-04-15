package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.common.annotation.Desensitization;
import com.example.common.constant.CommonConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@TableName(value = "sys_user")
@SuppressWarnings("serial")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysUser extends Model<SysUser> implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;


    /**
     * 密码
     */
    @TableField(value = "password")
    @Desensitization(type = CommonConstant.DesensitizationType.PASSWORD)
    private String password;


    /**
     * 部门编号
     */
    @TableField(value = "dept_id")
    private Integer deptId;


    /**
     * 禁用/启用
     */
    @TableField(value = "status")
    private Integer status;


    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;


    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;


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
    @TableField(value = "real_name")
//    @Desensitization(type = CommonConstant.DesensitizationType.CHINESE_NAME)
    private String realName;


    /**
     * 住址
     */
    @TableField(value = "address")
    private String address;


    /**
     * 岗位
     */
    @TableField(value = "job")
    private String job;


    /**
     * 入职时间
     */
    @TableField(value = "hire_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;


    /**
     * 乐观锁
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;


    /**
     * 修改时间
     */
    @TableField(value = "modify_date", fill = FieldFill.UPDATE)
    @JsonIgnore
    private Date modifyDate;


    /**
     * 创建人ID
     */
    @TableField(value = "created_by")
    private Integer createdBy;


    /**
     * 修改人ID
     */
    @TableField(value = "modify_by")
    private Integer modifyBy;

    /**
     * 部门名字
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleNames;

}
