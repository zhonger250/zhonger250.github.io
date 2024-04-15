package com.example.service.impl;

import com.example.mapper.TbMeetingMapper;
import com.example.service.TbMeetingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
