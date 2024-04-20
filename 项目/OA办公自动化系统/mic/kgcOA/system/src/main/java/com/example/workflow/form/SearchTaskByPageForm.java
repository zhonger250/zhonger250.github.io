package com.example.workflow.form;

import lombok.Data;

/**
 * @Author: zhonger250
 * @Date: 2024/4/16 16:44
 * @Project: mic
 * @Description: 查询任务参数表单
 */

@Data
public class SearchTaskByPageForm {
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 每页显示的记录数
     */
    private Integer length;
    /**
     * 任务类型
     */
    private String type;
    /**
     * 任务状态
     */
    private String status;
    /**
     * 创建人名称
     */
    private String creatorName;
    /**
     * 流程实例ID
     */
    private String instanceId;
}
