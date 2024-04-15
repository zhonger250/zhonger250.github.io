package com.example.flow.day02;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 16:58
 * @Project: mic
 * @Description: 请假类
 */
@Data
public class Leave implements Serializable {
    /**
     * 申请人
     */
    private String assignee1;
    /**
     * 部门经理(项目经理)
     */
    private String assignee2;
    /**
     * 公司经理
     */
    private String assignee3;

    /**
     * 请假天数
     */
    private int days;

}
