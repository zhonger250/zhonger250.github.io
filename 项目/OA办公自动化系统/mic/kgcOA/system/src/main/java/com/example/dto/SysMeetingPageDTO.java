package com.example.dto;

import lombok.Data;

/**
 * @Author: zhonger250
 * @Date: 2024/4/18 15:38
 * @Project: mic
 * @Description: 会议的分页查询参数
 */
@Data
public class SysMeetingPageDTO {
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 每页显示的记录数
     */
    private int pageSize;
    /**
     * 我的会议还全部会议
     */
    private String model;
    /**
     * 会议的日期
     */
    private String date;
}
