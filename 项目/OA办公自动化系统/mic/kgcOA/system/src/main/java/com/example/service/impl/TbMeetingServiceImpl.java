package com.example.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.SysUser;
import com.example.mapper.SysUserMapper;
import com.example.mapper.TbMeetingMapper;
import com.example.service.TbMeetingService;
import com.example.utils.SecurityUtil;
import com.example.workflow.service.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:42
 * @Project: mic
 * @Description: 业务实现类
 */
@Service("tbMeetingService")
@Slf4j
public class TbMeetingServiceImpl implements TbMeetingService {
    @Resource
    private TbMeetingMapper tbMeetingMapper;

    @Resource
    private SecurityUtil securityUtil;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private WorkflowService workflowService;

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

    /**
     * 分页某一天所有会议室的所有会议信息
     *
     * @param map
     * @return
     */
    @Override
    public Page<HashMap> searchOfflineMeetingByPage(HashMap map) {

        // 会议室总记录数据
        long totalCount = tbMeetingMapper.searchOfflineMeetingRoomCount();
        // 获得当前页
        Integer currentPage = MapUtil.getInt(map, "currentPage");
        // 获得每页的记录数
        Integer pageSize = MapUtil.getInt(map, "pageSize");
        // 封装Page对象
        Page<HashMap> page = new Page<>(currentPage, pageSize, totalCount);
        // 每页开始的条数
        int startRow = (currentPage - 1) * pageSize;
        map.put("startRow", startRow);
        // 当前页数据
        List<HashMap> hashMaps = tbMeetingMapper.searchOfflineMeetingByPage(map);
        // 处理当前数据
        for (HashMap hashMap : hashMaps) {
            String meeting = MapUtil.getStr(hashMap, "meeting");
            JSONArray jsonArray = JSONUtil.parseArray(meeting);
            hashMap.put("meeting", jsonArray);
        }
        // 将其封装到page对象
        page.setRecords(hashMaps);
        return page;
    }

    /**
     * 申请线下会议
     *
     * @param map
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addOfflineMeeting(HashMap<String, Object> map) {

        // 1.向会议表中新增一条申请记录
        tbMeetingMapper.insertMeeting(map);

        // 2.获得当前登录用户的角色,
        String loginUserId = securityUtil.getLoginUserId();
        String[] roleId = securityUtil.getRoleId(loginUserId);

        // 流程需要的参数
        Map param = new HashMap();

        // 第一个分支的条件
        String identity = "";
        // 部门主管的用户Id
        String managerId = "";
        // 经理的Id
        String gmId = "";

        // 如果用户的角色是普通员工
        if (ArrayUtil.contains(roleId, "1")) {
            // 经理
            identity = "总经理";
        } else if (ArrayUtil.contains(roleId, "2")) {
            // 部门主管
            identity = "部门主管";
            managerId = loginUserId;
            gmId = "1";
        } else {
            // 普通员工
            identity = "普通员工";
            // 普通员工所在部门主管的用户Id
            managerId = String.valueOf(sysUserMapper.selectUserManagerId(Integer.parseInt(loginUserId)));
            gmId = "1";
        }
        param.put("identity", identity);
        param.put("managerId", managerId);
        param.put("gmId", gmId);
        param.put("uuid", map.get("uuid"));
        param.put("type", "会议申请");
        // 流程实例创建人名字
        SysUser sysUser = sysUserMapper.selectById(loginUserId);
        param.put("creatorName", sysUser.getRealName());
        param.put("date", map.get("date"));
        param.put("time", map.get("start"));
        // 如果是公司经理申请看开会, 流程就直接完成了, 结果是同意
        if (StrUtil.isBlank(gmId)) {
            param.put("result", "同意");
        }
        // 3.所有的参会人是否是一个同一部门的员工,
        boolean sameDept = true;
        JSONArray jsonArray = JSONUtil.parseArray(MapUtil.getStr(map, "members"));
        Set<Integer> set = new HashSet<>();
        for (Object o : jsonArray) {
            // 每个参会人员的Id
            int member = (int)o;
            // 获得每个参会人员的部门ID
            int deptId = sysUserMapper.selectById(member).getDeptId();
            set.add(deptId);
        }
        // 判断参会人员是否在同一部门
        if (set.size() > 1) {
            sameDept = false;
        }
        param.put("sameDept", sameDept);

        log.debug("流程实例的启动参数{}",JSONUtil.toJsonPrettyStr(param));



        // 4.启动流程实例
        String instanceId = workflowService.startProcessInstance(param);
        log.debug("流程实例的ID{}",instanceId);
        // 5.更新会议表中对应的记录的流程实例ID
        tbMeetingMapper.updateMeetingInstanceId(MapUtil.getStr(param,"uuid"),instanceId);
        return true;

    }
}
