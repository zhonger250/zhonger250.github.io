package com.example.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: zhonger250
 * @Date: 2024-04-15 15:34:29
 * @Description: 会议表(TbMeeting)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "tb_meeting")
@SuppressWarnings("serial")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TbMeeting extends Model<TbMeeting> implements Serializable {

    /**
     * 主键
     */

    private Integer id;


    /**
     * UUID
     */

    private String uuid;


    /**
     * 会议题目
     */
    private String title;



    private Long creatorId;



    private Date date;

    /**
     * 开会地点
     */

    private String place;


    /**
     * 开始时间
     */

    private String start;


    /**
     * 结束时间
     */

    private String end;


    /**
     * 会议类型（1在线会议，2线下会议）
     */

    private Short type;


    /**
     * 参与者
     */

    private String members;


    /**
     * 会议内容
     */

    private String desc;


    /**
     * 工作流实例ID
     */

    private String instanceId;


    /**
     * 出席人员名单
     */

    private String present;


    /**
     * 未出席人员名单
     */

    private String unpresent;


    /**
     * 状态（1.申请中，2.审批未通过，3.审批通过，4.会议进行中，5.会议结束）
     */

    private Short status;


    /**
     * 创建时间
     */

    private Date createTime;


}
