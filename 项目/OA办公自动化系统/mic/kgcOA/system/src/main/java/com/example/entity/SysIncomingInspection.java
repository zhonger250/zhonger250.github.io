package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhonger250
 * @Date: 2024-04-08 12:12:19
 * @Description: (SysIncomingInspection) 来料检验
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "sys_incoming_inspection")
@SuppressWarnings("serial")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysIncomingInspection extends Model<SysIncomingInspection> implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 来料检验单编号
     */
    @TableField(value = "incoming_inspection_code")
    private String incomingInspectionCode;


    /**
     * 来料检验单名称
     */
    @TableField(value = "incoming_inspection_name")
    private String incomingInspectionName;


    /**
     * 供应商ID
     */
    @TableField(value = "vendor_id")
    private Integer vendorId;


    /**
     * 供应商编码
     */
    @TableField(value = "vendor_code")
    private String vendorCode;


    /**
     * 供应商简称
     */
    @TableField(value = "vendor_abbreviation")
    private String vendorAbbreviation;


    /**
     * 供应商批次号
     */
    @TableField(value = "vendor_lot_number")
    private String vendorLotNumber;


    /**
     * 产品物料编码
     */
    @TableField(value = "product_item_code")
    private String productItemCode;


    /**
     * 产品物料名称
     */
    @TableField(value = "product_item_name")
    private String productItemName;


    /**
     * 接收数量
     */
    @TableField(value = "quantity_received")
    private Integer quantityReceived;


    /**
     * 检测数量
     */
    @TableField(value = "number_of_detection")
    private Integer numberOfDetection;


    /**
     * 不合格数量
     */
    @TableField(value = "number_of_unqualified")
    private Integer numberOfUnqualified;


    /**
     * 检测结果(1.检测通过 2.检测不通过)
     */
    @TableField(value = "result_of_detection")
    private Integer resultOfDetection;


    /**
     * 来料日期
     */
    @TableField(value = "date_of_incoming_inspection")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfIncomingInspection;


    /**
     * 检测人员
     */
    @TableField(value = "detection_by")
    private Integer detectionBy;


    /**
     * 单据状态(1.草稿 2.已确认 3.已完成)
     */
    @TableField(value = "document_status")
    private Integer documentStatus;


    /**
     * 创建日期
     */
    @TableField(value = "creation_date", fill = FieldFill.INSERT)
    @JsonIgnore
    private Date creationDate;


    /**
     * 检测日期
     */
    @TableField(value = "date_of_detection")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfDetection;


    /**
     * 创建人ID
     */
    @TableField(value = "created_by")
    private Integer createdBy;


    /**
     * 修改日期
     */
    @TableField(value = "modify_date", fill = FieldFill.UPDATE)
    @JsonIgnore
    private Date modifyDate;


    /**
     * 修改人
     */
    @TableField(value = "modified_by")
    private Integer modifiedBy;


    /**
     * 乐观锁
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;


    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
