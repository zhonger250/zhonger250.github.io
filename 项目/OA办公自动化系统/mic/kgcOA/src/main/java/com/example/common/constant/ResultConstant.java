package com.example.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dxr
 * 返回结果的系统常量
 */
@Getter
@AllArgsConstructor
public enum ResultConstant {
    /**
     * 处理成功
     */
    SUCCESS(20000,"success"),

    /**
     * 处理失败
     */
    FAIL(500,"fail"),
    /**
     * 服务器异常
     */
    ERROR(500,"服务器出现异常"),

    /**
     * 未登录
     */
    NO_LOGIN(403,"未登录"),
    /**
     * 登录失败
     */
    LOGIN_ERROR(401,"登录失败"),

    /**
     * 请求重复提交
     */
    REPEAT_COMMIT(1000,"重复提交"),

    /**
     * 服务器端参数验证失败异常信息
     */
    PARAM_ERROR(10001,"参数错误"),

    /**
     * 角色名不能为空
     */
    ROLE_NAME_NULL_ERROR(1001,"角色名不能为空"),
    /**
     * 角色描述不能为空
     */
    ROLE_DESCRIPTION_NULL_ERROR(1002,"角色描述不能为空"),
    /**
     * 角色不存在
     */
    ROLE_NOTE_EXISTS(1003,"角色不存在"),
    /**
     * 角色被使用
     */
    ROLE_USED(1004,"角色被使用"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(2001,"用户不存在"),
    /**
     * 菜单不存在
     */
    MENU_NOT_EXIST(3001,"菜单不存在"),
    /**
     * 菜单不能被禁用
     */
    MENU_CANNOT_BE_DISABLED(3002,"菜单下有子菜单, 菜单不能被禁用"),
    /**
     * 父级菜单不存在
     */
    MENU_PARENT_NOT_EXIST(3003,"父级菜单不存在"),
    /**
     * 菜单类型错误
     */
    MENU_TYPE_ERROR(3004,"菜单类型错误"),
    /**
     * 菜单被使用
     */
    MENU_USED(3005,"菜单被使用"),
    ;


    private final int code;
    private final String message;
}
