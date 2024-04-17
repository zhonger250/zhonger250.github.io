package com.example.service;

import java.util.HashMap;

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

    /**
     * 根据实例Id查询会议信息(添加会议时长)
     * @param instanceId
     * @return
     */
    HashMap searchMeetingByInstanceId(String instanceId);

    /**
     * 根据会议的uudi获得会议的信息
     * @param uuid
     * @return
     */
    HashMap searchMeetingByUUID(String uuid);

    /**
     * 删除会议
     * @param uuid
     * @return
     */
    boolean deleteMeeting(String uuid);
}
