package com.example.service;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:41
 * @Project: mic
 * @Description: 会议的业务层
 */
public interface TbMeetingService {
    /**
     * 更改会议的状态
     * @param uuid
     * @param status
     * @return
     */
    boolean updateMeetingStatus(String uuid,int status);
}
