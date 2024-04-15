package com.example.flow.day02;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 15:34
 * @Project: mic
 * @Description: 历史任务测试
 */
@SpringBootTest
public class HistoryTaskTest {

    @Resource
    private HistoryService historyService;

    @Test
    public void showBajieHistoryTask(){
        List<HistoricTaskInstance> bajie = historyService.createHistoricTaskInstanceQuery().taskAssignee("bajie").list();
        for (HistoricTaskInstance historicTaskInstance : bajie) {

            System.out.println("历史任务ID:  "+historicTaskInstance.getId());
            System.out.println("历史任务名称:  "+historicTaskInstance.getName());
            System.out.println("历史任务结束时间:  "+ DateUtil.format(historicTaskInstance.getEndTime(),"yyyy-MM-dd HH-mm-ss"));
            System.out.println("任务的执行人:  "+historicTaskInstance.getAssignee());
        }
    }
}
