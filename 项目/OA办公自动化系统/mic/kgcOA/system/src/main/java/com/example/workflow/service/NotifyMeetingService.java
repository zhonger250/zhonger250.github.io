package com.example.workflow.service;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:03
 * @Project: mic
 * @Description: 会议通知类
 * <br>
 * notifyMeetingService
 */
@Component
@Slf4j
public class NotifyMeetingService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        log.debug("发送定义通知");
    }
}
