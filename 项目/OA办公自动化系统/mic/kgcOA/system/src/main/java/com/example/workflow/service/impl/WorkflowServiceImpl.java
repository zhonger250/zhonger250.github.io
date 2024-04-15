package com.example.workflow.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.example.common.utils.QuartzUtil;
import com.example.workflow.job.MeetingWorkflowJob;
import com.example.workflow.service.WorkflowService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:49
 * @Project: mic
 * @Description: 工作流的接口实现
 */
@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private QuartzUtil quartzUtil;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;

    @Override
    public String startProcessInstance(Map param) {
        // 启动流程实例(使用param参数启动流程实例的参数) 获得流程实例的ID
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("meeting", param);
        String instanceId = processInstance.getProcessInstanceId();
        // 获得会议的ID
        String uuid = MapUtil.getStr(param, "uuid");
        // 获得会议的开始时间 年月日
        String date = MapUtil.getStr(param, "date");
        // 会议的开始时间 时分秒
        String time = MapUtil.getStr(param, "time");
        // 拼接会议开始执行的时间 yyyy-MM-dd HH:mm:ss
        DateTime startTime = DateUtil.parse(date + " " + time, "yyyy-MM-dd HH:mm:ss");
        // 在会议的开始时间创建一个定时任务, 判断此会议对应的流程实例是否存在, 如果会议开始时间到了, 流程实例还存在,
        // 说明会议没有审批通过.
        JobDetail jobDetail = JobBuilder.newJob(MeetingWorkflowJob.class).build();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("uuid", uuid);
        jobDataMap.put("instanceId", instanceId);
        quartzUtil.addJob(jobDetail, uuid, "会议通过流程", startTime);
        return null;
    }

    @Override
    public HashMap searchTaskByPage(Map param) {
        // 任务的局部变量(每个任务的所有局部变量对应的就是一个Map对象, Map对象中保存的是任务的局部变量)
        List<Map> listMap = new ArrayList<>();
        // 查询userId对应的任务 待处理任务
        Integer userId = MapUtil.getInt(param, "userId");
        // 当前页
        Integer page = MapUtil.getInt(param, "page");
        // 记录数
        Integer length = MapUtil.getInt(param, "length");
        // 从第几条开始
        Integer start = MapUtil.getInt(param, "start");
        // 查询任务的状态
        String status = MapUtil.getStr(param, "status");
        // 获得任务的类型
        String type = MapUtil.getStr(param, "type");
        // 流程实例的ID
        String instanceId = MapUtil.getStr(param, "instanceId");
        // 任务的创建者的名字
        String creatorName = MapUtil.getStr(param, "creator");
        // 获得角色待处理的任务
        JSONArray jsonArray = (JSONArray) param.get("role");

        // 任务的待处理人
        List<String> assigneeIds = new ArrayList<>();
        assigneeIds.add(String.valueOf(userId));
        // 添加任务的待处理角色
        jsonArray.forEach(role -> {
            assigneeIds.add(role.toString());
        });

        // 查询满足条件的总数
        long totalCount = 0;
        // 查询的是待审批的任务
        if ("待审批".equals(status)) {
            TaskQuery taskQuery = taskService.createTaskQuery().orderByTaskCreateTime().desc()
                    .includeTaskLocalVariables().includeProcessVariables().taskAssigneeIds(assigneeIds);
            // 如果类型不为空
            if (StrUtil.isNotBlank(type)){
                // 查询任务时, 就按照任务进行筛选
                taskQuery.processVariableValueEquals("type",type);
            }
            // 如果创建人的名字不为空,
            if (StrUtil.isNotBlank(creatorName)){
                // 查询任务时, 就按照创建人的名字进行筛选
                taskQuery.processVariableValueEquals("creatorName",creatorName);
            }
            // 如果流程实例不未空, 按照流程实例进行查询
            if (StrUtil.isNotBlank(instanceId)){
                // 按照流程实例及逆行查询
                taskQuery.processVariableValueEquals("instanceId",instanceId);
            }
            // 查询到待审批的任务
            List<Task> list = taskQuery.listPage(start,length);
            // 查询已审批的总记录数
            totalCount = taskQuery.count();
            // 遍历任务
            for (Task task : list) {
                // 拷贝任务中的局部变量, 为了防止后面改这些局部变量, 影响到原来任务中的信息
                Map<String, Object> map = new HashMap<>(task.getTaskLocalVariables());
                map.put("taskId",task.getId());
                map.put("processId",task.getProcessInstanceId());
                map.put("status",status);
                listMap.add(map);
            }
        } else if("已审批".equals(status)){
            HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().orderByTaskCreateTime()
                    .includeProcessVariables().includeTaskLocalVariables().orderByTaskCreateTime().finished()
                    .processUnfinished();
            // 如果类型不为空
            if (StrUtil.isNotBlank(type)){
                // 查询任务时, 就按照任务进行筛选
                taskQuery.processVariableValueEquals("type",type);
            }
            // 如果创建人的名字不为空,
            if (StrUtil.isNotBlank(creatorName)){
                // 查询任务时, 就按照创建人的名字进行筛选
                taskQuery.processVariableValueEquals("creatorName",creatorName);
            }
            // 如果流程实例不未空, 按照流程实例进行查询
            if (StrUtil.isNotBlank(instanceId)){
                // 按照流程实例及逆行查询
                taskQuery.processVariableValueEquals("instanceId",instanceId);
            }
            // 查询已审批的任务
            List<HistoricTaskInstance> list = taskQuery.listPage(start, length);
            // 查询待审批的总记录数
            totalCount = taskQuery.count();
            // 遍历任务
            for (HistoricTaskInstance task : list) {
                // 拷贝任务中的局部变量, 为了防止后面改这些局部变量, 影响到原来任务中的信息
                Map<String, Object> map = new HashMap<>(task.getTaskLocalVariables());
                map.put("taskId",task.getId());
                map.put("processId",task.getProcessInstanceId());
                map.put("status",status);
                listMap.add(map);
            }
        } else if ("已结束".equals(status)) {
            HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().orderByTaskCreateTime()
                    .includeProcessVariables().includeTaskLocalVariables().orderByTaskCreateTime().finished()
                    .processFinished();
            // 如果类型不为空
            if (StrUtil.isNotBlank(type)){
                // 查询任务时, 就按照任务进行筛选
                taskQuery.processVariableValueEquals("type",type);
            }
            // 如果创建人的名字不为空,
            if (StrUtil.isNotBlank(creatorName)){
                // 查询任务时, 就按照创建人的名字进行筛选
                taskQuery.processVariableValueEquals("creatorName",creatorName);
            }
            // 如果流程实例不未空, 按照流程实例进行查询
            if (StrUtil.isNotBlank(instanceId)){
                // 按照流程实例及逆行查询
                taskQuery.processVariableValueEquals("instanceId",instanceId);
            }
            // 查询已审批的任务
            List<HistoricTaskInstance> list = taskQuery.listPage(start, length);
            // 查询待审批的总记录数
            totalCount = taskQuery.count();
            // 遍历任务
            for (HistoricTaskInstance task : list) {
                // 拷贝任务中的局部变量, 为了防止后面改这些局部变量, 影响到原来任务中的信息
                Map<String, Object> map = new HashMap<>(task.getTaskLocalVariables());
                map.put("taskId",task.getId());
                map.put("processId",task.getProcessInstanceId());
                map.put("status",status);
                listMap.add(map);
            }
        }
        // 最终返回的数据
        HashMap<String,Object> map = new HashMap<>();
        // 封装的每个任务的局部变量, (这是我们前端需要展示的一些数据, 并不是前端展示任务的集合)
        map.put("list",listMap);
        map.put("totalCount",totalCount);
        map.put("pageIndex",page);
        map.put("pageSize",length);
        map.put("start",start);

        return map;
    }
}
