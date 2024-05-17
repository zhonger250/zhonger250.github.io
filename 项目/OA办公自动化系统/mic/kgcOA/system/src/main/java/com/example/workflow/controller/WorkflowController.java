package com.example.workflow.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.utils.SecurityUtil;
import com.example.common.vo.Result;
import com.example.workflow.form.ApprovalTaskForm;
import com.example.workflow.form.DeleteProcessInstanceForm;
import com.example.workflow.form.SearchApprovalContentForm;
import com.example.workflow.form.SearchTaskByPageForm;
import com.example.workflow.service.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/4/16 16:48
 * @Project: mic
 * @Description: 工作流控制器
 */
@Api(tags = "工作流模块")
@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    @Resource
    private WorkflowService workflowService;


    @Resource
    private SecurityUtil securityUtil;

    @PostMapping("/approvalTask")
    @ApiOperation(value = "审批任务")
    public Result approvalTask(@RequestBody ApprovalTaskForm form){
        // 将参数转为Map类型
        HashMap map = (HashMap) BeanUtil.beanToMap(form);
        // 审批任务
        workflowService.approvalTask(map);
        // 返回结果
        return Result.success();
    }

    @PostMapping("/searchApprovalTask")
    @ApiOperation(value = "查询审批任务详情")
    public Map searchApprovalTask(@RequestBody SearchApprovalContentForm searchApprovalContentForm) {
        return workflowService.searchApprovalContent(
                searchApprovalContentForm.getInstanceId(),
                securityUtil.getLoginUserId(),
                securityUtil.getRoleId(securityUtil.getLoginUserId()),
                searchApprovalContentForm.getType(), searchApprovalContentForm.getType()
        );
    }


    @PostMapping("/page")
    @ApiOperation(value = "分页查询用户的任务信息")
    public Map<String, Object> searchPage(@RequestBody SearchTaskByPageForm searchTaskByPageForm) {
        // 获得登录ID
        String userId = securityUtil.getLoginUserId();
        // 根据用户ID获得角色ID
        String[] roleIds = securityUtil.getRoleId(userId);
        // 将对象转为mapsearchTaskByPageForm
        Map<String, Object> map = BeanUtil.beanToMap(searchTaskByPageForm);
        // 将角色ID和用户ID放到map
        map.put("userId", userId);
        map.put("role", roleIds);

        return workflowService.searchTaskByPage(map);
    }


    @DeleteMapping("/deleteProcessInstance")
    @ApiOperation(value = "删除流程实例")
    public Result deleteProcessInstance(@RequestBody DeleteProcessInstanceForm form){
        workflowService.deleteProcessInstance(form.getUuid(), form.getInstance(), form.getType(), form.getReason());

        return Result.success();
    }



}
