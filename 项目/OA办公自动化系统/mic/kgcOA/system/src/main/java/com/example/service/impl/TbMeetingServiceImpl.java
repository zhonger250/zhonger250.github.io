package com.example.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.example.mapper.TbMeetingMapper;
import com.example.service.TbMeetingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:42
 * @Project: mic
 * @Description: 业务实现类
 */
@Service("tbMeetingService")
public class TbMeetingServiceImpl implements TbMeetingService {
    @Resource
    private TbMeetingMapper tbMeetingMapper;

    @Override
    public boolean updateMeetingStatus(String uuid, int status) {
        return tbMeetingMapper.updateMeetingStatus(uuid, status) > 0;
    }

    @Override
    public HashMap searchMeetingByInstanceId(String instanceId) {
        // 数据库中会议的信息
        HashMap hashMap = tbMeetingMapper.searchMeetingByInstanceId(instanceId);
        // 计算会议的时长, 将会议的时长保存到会议信息中
        String date = MapUtil.getStr(hashMap, "date");
        String start = MapUtil.getStr(hashMap, "start");
        String end = MapUtil.getStr(hashMap, "end");

        DateTime s = DateUtil.parse(date + " " + start, "yyyy-MM-dd HH:mm");
        DateTime e = DateUtil.parse(date + " " + end, "yyyy-MM-dd HH:mm");
        // 计算时长
        long between = DateUtil.between(s, e, DateUnit.MINUTE);

        hashMap.put("minutes", between);
        // 返回会议信息
        return hashMap;
    }

    @Override
    public HashMap searchMeetingByUUID(String uuid) {
        return tbMeetingMapper.searchMeetingByUUID(uuid);
    }

    /**
     * 删除会议
     *
     * @param uuid
     * @return
     */
    @Override
    public boolean deleteMeeting(String uuid) {
        return tbMeetingMapper.deleteMeeting(uuid) > 0;
    }
}
