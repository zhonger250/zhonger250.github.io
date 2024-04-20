package com.example.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import com.example.dto.SysMeetingInsertDTO;
import com.example.dto.SysMeetingPageDTO;
import com.example.service.TbMeetingService;
import com.example.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/4/18 15:35
 * @Project: mic
 * @Description:
 */
@RestController
@RequestMapping("/meeting")
@Api(tags = "会议web接口")
public class SysMeetingController {
    @Resource
    private TbMeetingService tbMeetingService;
    @Resource
    private SecurityUtil securityUtil;

    @PostMapping("/searchOfflineMeetingByPage")
    @ApiOperation("分页查询某天的线下会议时间")
    public Page searchOfflineMeetingByPage(@RequestBody SysMeetingPageDTO sysMeetingPageDTO) {
        // 如果没有传入时间, 默认就是当天
        if (StrUtil.isBlank(sysMeetingPageDTO.getDate())){
            sysMeetingPageDTO.setDate(DateUtil.today());
        }
        // 获得登录的用户Id
        String loginUserId = securityUtil.getLoginUserId();
        // 转为Map
        HashMap params = (HashMap) BeanUtil.beanToMap(sysMeetingPageDTO);
        // map中封装ID
        params.put("uid",loginUserId);
        return tbMeetingService.searchOfflineMeetingByPage(params);
    }


    @PostMapping("/insertOfflineMeeting")
    @ApiOperation(value = "线下会议申请")
    public boolean insertOfflineMeeting(@RequestBody SysMeetingInsertDTO sysMeetingInsertDTO){
        DateTime startTime = DateUtil.parse(sysMeetingInsertDTO.getDate() + " " + sysMeetingInsertDTO.getStart(),"yyyy-MM-dd HH:mm");
        // 获得当前时间
        Date now = new Date();
        if (startTime.isBefore(now)){
            throw new HttpException(ResultConstant.MEETING_ERROR);
        }
        HashMap<String,Object> params = (HashMap<String, Object>) BeanUtil.beanToMap(sysMeetingInsertDTO);
        // 生成会议的唯一ID
        String uuid = UUID.randomUUID().toString();
        // 会议的创建人
        String creatorId = securityUtil.getLoginUserId();
        // 会议的类型
        int type = 2;
        // 会议状态是申请中
        int status = 1;
        // 创建时间
        String createTime = DateUtil.now();
        // 补充新增的参数
        params.put("uuid",uuid);
        params.put("creatorId",creatorId);
        params.put("type",type);
        params.put("status",status);
        params.put("createTime",createTime);

        return tbMeetingService.addOfflineMeeting(params);
    }

}
