package com.example.flow.day02;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 15:50
 * @Project: mic
 * @Description: 任务拾取测试
 */
@SpringBootTest
public class TaskClaimTesT {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;
    @Test
    public void test(){
        // 部署流程
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("bpmn/demo4.bpmn20.xml").deploy();

        // 启动流程实例
        runtimeService.startProcessInstanceByKey("demo4");

        // bajie查询自己的待办任务
        System.out.println("bajie的代办任务");
        List<Task> list = taskService.createTaskQuery().taskAssignee("bajie").list();
        for (Task task : list) {
            System.out.println(task.getId()+"=="+task.getName());
        }
        System.out.println("=================================");
        // bajie执行自己的任务
        System.out.println("bajie执行第一任务");
        taskService.complete(list.get(0).getId());
        System.out.println("=================================");

        // 查询所有的待办任务
        System.out.println("所有当前的代办任务");
        List<Task> taskList = taskService.createTaskQuery().list();
        for (Task task : taskList) {
            System.out.println(task.getId()+"=="+task.getName());
        }
        System.out.println("=================================");

        // tangseng拾取审批任务
        System.out.println("tangseng拾取了当前代办任务中的第一个任务");
        taskService.claim(taskList.get(0).getId(),"tangseng");
        System.out.println("=================================");

        // tangseng放弃审批任务
        System.out.println("tansgeng放弃当前待办任务的第一个任务");
        taskService.unclaim(taskList.get(0).getId());
        System.out.println("=================================");

        // wukong拾取审批任务
        System.out.println("wukong拾取当前待办任务的第一个任务");
        taskService.claim(taskList.get(0).getId(),"wukong");
        System.out.println("=================================");

        // wukong执行审批任务
        System.out.println("wukong执行审批任务");
        taskService.complete(taskList.get(0).getId());
        System.out.println("=================================");

        // 查询wukong的历史任务
        for (HistoricTaskInstance historicTaskInstance : historyService.createHistoricTaskInstanceQuery().taskAssignee("wukong").list()) {
            System.out.println(historicTaskInstance.getId()+"=="+historicTaskInstance.getName());
        }

    }

}
