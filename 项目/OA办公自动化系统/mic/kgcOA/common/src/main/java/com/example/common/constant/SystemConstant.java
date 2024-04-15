package com.example.common.constant;

import lombok.Getter;

/**
 * @Author: zhonger250
 * @Date: 2024/3/26 16:49
 * @Description: 整个系统的常量
 */
public class SystemConstant {

    @Getter
    public enum SysUserStatus{
        /**
         * 用户状态
         */
        ENABLE(1,"启用"),
        DISABLE(2,"禁用");
        SysUserStatus(int code, String desc){
            this.code = code;
            this.desc = desc;
        }

        private final int code;
        private final String desc;
    }

    @Getter
    public enum SysMenuStatus{
        /**
         * 菜单状态
         */
        ENABLE(1,"启用"),
        DISABLE(2,"禁用");
        SysMenuStatus(int code, String desc){
            this.code = code;
            this.desc = desc;
        }

        private final int code;
        private final String desc;
    }

    @Getter
    public enum MenuType{
        /**
         * 菜单类型
         */
        CATALOG_MENU(0,"目录菜单"),
        COMMON_MENU(1,"普通菜单"),
        BUTTON(2,"按钮菜单");
        MenuType(int code, String desc){
            this.code = code;
            this.desc = desc;
        }

        private final int code;
        private final String desc;
    }
}
