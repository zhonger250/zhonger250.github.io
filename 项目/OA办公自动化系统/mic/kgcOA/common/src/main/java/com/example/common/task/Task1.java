package com.example.common.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author 丁祥瑞
 * 定时任务
 */
//@Component
public class Task1 {
    /**
     *  cron表达式:定时任务执行的频率
     *  cron表达式格式: [秒] [分] [小时] [日] [月] [周] [年]
     * 第一位，表示秒，取值0-59
     * 第二位，表示分，取值0-59
     * 第三位，表示小时，取值0-23
     * 第四位，日，取值1-31
     * 第五位，月， 取值1-12
     * 第六位，星期，取值1-7，1表示星期天，2表示星期一
     * 第七位，年，可以留空，取值1970-2099
     * <p>
     * 0 15 10 15 * ? 每月15日上午10:15触发
     * "?" 字符表示不关注该值 用在日和星期上
     * "*" 字符表示任意的值
     * "/" 字符表示步
     * <p>
     *
     * @Scheduled 注解修饰的方法, 表示该方法是一个定时任务, cron值是一个表达式, 任务执行的频率
     *
     * @Async 注解修饰方法, 表示式异步执行的.(创建一个线程执行)
     */
    @Scheduled(cron = "*/10 * * * * ?")
    @Async
    public void method() {
        System.out.println("定时任务1执行了");
    }
}
