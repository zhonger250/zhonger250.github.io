package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

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
    int updateMeetingStatus(@Param("uuid") String uuid,@Param("status") int status);

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


    /**
     * 查询线下会议室的数量
     * @return
     */
    long searchOfflineMeetingRoomCount();


    /**
     * 分页某一天所有会议室的所有会议信息
     * @param hashMap (startRow, pageSize, uid, date, model(我的会议还是所有会议))
     * @return
     */
    List<HashMap> searchOfflineMeetingByPage(HashMap hashMap);

    /**
     * 新增会议
     * @param map
     * @return
     */
    int insertMeeting(HashMap map);

    /**
     * 更新会议的流程实例的ID
     * @param uuid 会议ID
     * @param instanceId 流程实例的Id
     * @return
     */
    int updateMeetingInstanceId(@Param("uuid") String uuid, @Param("instanceId") String instanceId);
}
