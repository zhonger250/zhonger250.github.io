package com.example.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: zhonger250
 * @Date: 2024/2/27 11:17
 * @Description: 数据导出状态常量
 */
@Getter
@AllArgsConstructor
public enum DataExportTypeConstant {
    /**
     * 导出成功
     */
    NO_EXPORT(0,"未导出"),
    /**
     * 正在导出
     */
    RUN_EXPORT(1,"正在导出"),
    /**
     * 导出成功
     */
    SUCCESS_EXPORT(2,"导出成功"),
    /**
     * 导出失败
     */
    FAIL_EXPORT(3,"导出失败");


    private final int code;
    private final String msg;


}
