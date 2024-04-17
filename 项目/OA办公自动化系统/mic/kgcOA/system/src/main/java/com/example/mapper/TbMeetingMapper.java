package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:37
 * @Project: mic
 * @Description: 会议的数据访问层
 */
@Mapper
public interface TbMeetingMapper {
    /**
     * 更改会议的状态
     * @param uuid 会议的唯一表示
     * @param status 会议状态
     * @return
     */
    int updateMeetingStatus(String uuid,int status);

    /**
     * 根据项目实例Id查询会议信息,(在原来从数据库中查询到的会议信息添加会议时长)
     * @param instanceId
     * @return
     */
    HashMap searchMeetingByInstanceId(String instanceId);


    /**
     * 根据会议的唯一标识, 获得会议信息
     * @param uuid
     * @return
     */
    HashMap searchMeetingByUUID(String uuid);

    /**
     * 删除会议信息
     * @param uuid
     * @return
     */
    int deleteMeeting(String uuid);
}
