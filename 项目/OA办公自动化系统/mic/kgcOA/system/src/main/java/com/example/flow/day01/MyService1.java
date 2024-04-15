package com.example.flow.day01;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @Author: zhonger250
 * @Date: 2024/4/10 16:29
 * @Project: mic
 * @Description:
 */
public class MyService1 implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("任务1");
    }
}
