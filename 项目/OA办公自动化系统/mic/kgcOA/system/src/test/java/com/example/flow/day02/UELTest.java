package com.example.flow.day02;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 16:52
 * @Project: mic
 * @Description: UEL表达式
 */
@SpringBootTest
public class UELTest {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    /**
     * 部署流程文件
     */
    @Test
    public void deployment() {
        Deployment deploy = repositoryService.createDeployment().
                addClasspathResource("bpmn/demo5.bpmn20.xml").deploy();
        System.out.println("流程部署ID: " + deploy.getId());
    }

    /**
     * 查询流程定义
     */
    @Test
    public void processDefinitionTest() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance processInstance : list) {
            System.out.println("流程定义的key:  " + processInstance.getId());
        }
    }


    /**
     * 启动流程实例, 给assignee1变量, days变量赋值
     */
    @Test
    public void startProcessInstance() {
        Map<String, Object> map = new HashMap<>();
        Leave leave = new Leave();
        leave.setAssignee1("张三");
        leave.setDays(5);
        map.put("leave", leave);
        runtimeService.startProcessInstanceByKey("demo5", map);

    }

    /**
     * 请假申请人看到自己的待办任务
     */
    @Test
    public void zsShowTask() {
        List<Task> list = taskService.createTaskQuery().taskAssignee("张三").list();
        for (Task task : list) {
            System.out.println("张三的待办任务ID:" + task.getId());
        }
    }

    /**
     * 请假申请人完成自己的任务, 同时给assignee2(部门经理), assignee3(公司经理)变量赋值
     */
    @Test
    public void zsCompleteTask() {
        Map<String, Object> map = new HashMap<>();
        Leave leave = new Leave();
        leave.setAssignee2("李四");
        leave.setAssignee3("王五");
        leave.setDays(5);
        // ${leave.assignee2}
        map.put("leave", leave);
        taskService.complete("5ac9ec78-f7e8-11ee-9020-b445069b0fbc", map);
    }

    /**
     * 部门经理或者公司经理看到自己的代办任务
     */
    @Test
    public void showTask() {
        List<Task> list = taskService.createTaskQuery().taskAssignee("李四").list();
        for (Task task : list) {
            System.out.println("李四的待办任务:  " + task.getId());
            System.out.println("李四的待办任务的名字:  " + task.getName());
        }

        List<Task> list2 = taskService.createTaskQuery().taskAssignee("王五").list();
        for (Task task : list2) {
            System.out.println("王五的待办任务:  " + task.getId());
            System.out.println("王五的待办任务的名字:  " + task.getName());
        }

    }

    /**
     * 部门经理或部门经理, 完成自己的待办任务
     */
    @Test
    public void completeTask() {
        taskService.complete("0095b039-f7e7-11ee-81b7-b445069b0fbc");
    }
}
