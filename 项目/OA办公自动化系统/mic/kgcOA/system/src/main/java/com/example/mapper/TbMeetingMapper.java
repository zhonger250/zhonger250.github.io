package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

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
}
