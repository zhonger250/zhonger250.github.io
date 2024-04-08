package com.example.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhonger250
 * @Date: 2024-04-08 12:12:20
 * @Description: (SysIncomingInspection)表实体类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@SuppressWarnings("serial")
public class SysIncomingInspectionUpdateDTO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(name = "id", value = "主键", dataType = "int")
    private String id;


    /**
     * 来料检验单编号
     */
    @ApiModelProperty(name = "incoming_inspection_code", value = "来料检验单编号", dataType = "java.lang.String")
    private String incomingInspectionCode;


    /**
     * 来料检验单名称
     */
    @ApiModelProperty(name = "incoming_inspection_name", value = "来料检验单名称", dataType = "java.lang.String")
    private String incomingInspectionName;


    /**
     * 供应商ID
     */
    @ApiModelProperty(name = "vendor_id", value = "供应商ID", dataType = "java.lang.Integer")
    private Integer vendorId;


    /**
     * 供应商编码
     */
    @ApiModelProperty(name = "vendor_code", value = "供应商编码", dataType = "java.lang.String")
    private String vendorCode;


    /**
     * 供应商简称
     */
    @ApiModelProperty(name = "vendor_abbreviation", value = "供应商简称", dataType = "java.lang.String")
    private String vendorAbbreviation;


    /**
     * 供应商批次号
     */
    @ApiModelProperty(name = "vendor_lot_number", value = "供应商批次号", dataType = "java.lang.String")
    private String vendorLotNumber;


    /**
     * 产品物料编码
     */
    @ApiModelProperty(name = "product_item_code", value = "产品物料编码", dataType = "java.lang.String")
    private String productItemCode;


    /**
     * 产品物料名称
     */
    @ApiModelProperty(name = "product_item_name", value = "产品物料名称", dataType = "java.lang.String")
    private String productItemName;


    /**
     * 接收数量
     */
    @ApiModelProperty(name = "quantity_received", value = "接收数量", dataType = "java.lang.Integer")
    private Integer quantityReceived;


    /**
     * 检测数量
     */
    @ApiModelProperty(name = "number_of_detection", value = "检测数量", dataType = "java.lang.Integer")
    private Integer numberOfDetection;


    /**
     * 不合格数量
     */
    @ApiModelProperty(name = "number_of_unqualified", value = "不合格数量", dataType = "java.lang.Integer")
    private Integer numberOfUnqualified;


    /**
     * 检测结果(1.检测通过 2.检测不通过)
     */
    @ApiModelProperty(name = "result_of_detection", value = "检测结果(1.检测通过 2.检测不通过)", dataType = "java.lang.Integer")
    private Integer resultOfDetection;


    /**
     * 来料日期
     */
    @TableField(value = "date_of_incoming_inspection")
    private Date dateOfIncomingInspection;


    /**
     * 检测人员
     */
    @ApiModelProperty(name = "detection_by", value = "检测人员", dataType = "java.lang.Integer")
    private Integer detectionBy;


    /**
     * 单据状态(1.草稿 2.已确认 3.已完成)
     */
    @ApiModelProperty(name = "document_status", value = "单据状态(1.草稿 2.已确认 3.已完成)", dataType = "java.lang.Integer")
    private Integer documentStatus;


    /**
     * 检测日期
     */
    @TableField(value = "date_of_detection")
    private Date dateOfDetection;


    /**
     * 创建人ID
     */
    @ApiModelProperty(name = "created_by", value = "创建人ID", dataType = "java.lang.Integer")
    private Integer createdBy;


    /**
     * 修改人
     */
    @ApiModelProperty(name = "modified_by", value = "修改人", dataType = "java.lang.Integer")
    private Integer modifiedBy;

    /**
     * 版本
     */
    @ApiModelProperty(name = "version", value = "乐观锁", dataType = "int")
    private Integer version;


    /**
     * 备注
     */
    @ApiModelProperty(name = "remark", value = "备注", dataType = "java.lang.String")
    private String remark;
}
