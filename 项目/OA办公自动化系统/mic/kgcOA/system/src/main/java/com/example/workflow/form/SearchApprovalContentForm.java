package com.example.workflow.form;

import lombok.Data;

/**
 * @Author: zhonger250
 * @Date: 2024/4/17 12:11
 * @Project: mic
 * @Description: 查询审批任务的详情需要传递的参数
 */
@Data
public class SearchApprovalContentForm {

    private String instanceId;
    private String type;
    private String status;
}
