package com.example.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: zhonger250
 * @Date: 2024/3/22 14:32
 * @Description:
 */
public class CommonConstant {

    @Getter
    @AllArgsConstructor
    public enum LogType {

        /**
         * 日志类型
         */
        LOGIN(1, "登录日志"),
        LOGOUT(2, "登出日志"),
        OPERATION(3, "操作日志"),
        TIMER(4, "定时任务");


        private final int code;
        private final String msg;
    }

    @Getter
    @AllArgsConstructor
    public enum Module {
        /**
         * 系统中的模块
         */
        SYSTEM(1,"系统模块"),
        FACTORY_MODEl(2,"工厂模型模块"),
        DICT_DATA(3,"数据字典模块"),
        LOG(4,"日志模块");


        private final int code;
        private final String msg;
    }

    @Getter
    @AllArgsConstructor
    public enum OperatorType {
        /**
         * 操作类型
         */
        INSERT(1, "新增操作"),
        DELETE(2, "删除操作"),
        UPDATE(3, "更新操作"),
        SELECT(4, "查询操作"),
        OTHER(5, "其他操作");

        private final int code;
        private final String msg;
    }

    @AllArgsConstructor
    @Getter
    public enum DesensitizationType {
        /**
         * 脱敏数据的类型
         */
        ID_CARD(1,"身份证号"),
        CHINESE_NAME(2,"中文名称"),
        USER_ID(3,"用户ID"),
        FIXED_PHONE(4,"固定电话"),
        MOBILE_PHONE(5,"手机号"),
        ADDRESS(6,"地址"),
        EMAIL(7,"邮箱"),
        PASSWORD(8,"密码"),
        CAR_LICENSE(9,"车牌号"),
        BANK_CARD(10,"银行卡号"),
        CUSTOMIZE_RULE(11,"自定义脱敏规则");

        private int code;
        private String message;
    }

}
