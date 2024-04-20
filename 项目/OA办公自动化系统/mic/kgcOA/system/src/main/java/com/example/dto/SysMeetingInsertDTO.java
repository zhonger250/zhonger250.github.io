package com.example.dto;

import lombok.Data;

/**
 * @Author: zhonger250
 * @Date: 2024/4/18 17:13
 * @Project: mic
 * @Description: 新增会议参数
 */
@Data
public class SysMeetingInsertDTO {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String desc;
    /**
     * 时间(年月日
     */
    private String date;
    /**
     * 开始时间(HH:mm)
     */
    private String start;
    /**
     * 开始时间(HH:mm)
     */
    private String end;
    /**
     * 参会人员([1,2])
     */
    private String members;
    /**
     * 会议地址
     */
    private String place;
}
