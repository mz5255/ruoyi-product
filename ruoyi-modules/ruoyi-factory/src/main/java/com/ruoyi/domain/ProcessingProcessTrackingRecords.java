package com.ruoyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 加工过程记录跟踪表
* @TableName processing_process_tracking_records
*/
@Data
public class ProcessingProcessTrackingRecords {

    /**
    * 
    */
    @ApiModelProperty("id")
    private Integer id;
    /**
    * 
    */
    @ApiModelProperty("版本号")
    private Integer version;
    /**
    * 加工产品id
    */
    @ApiModelProperty("加工产品id")
    private Integer agriculturalProductId;
    /**
    * 加工工厂id
    */
    @ApiModelProperty("加工工厂id")
    private Integer processingPlantId;
    /**
    * 加工时间
    */
    @ApiModelProperty("加工时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date processTime;
    /**
    * 加工内容
    */
    @ApiModelProperty("加工内容")
    @Length(max= 255,message="编码长度不能超过255")
    private String processContext;
    /**
    * 操作人员id
    */
    @ApiModelProperty("操作人员id")
    private Integer operatorId;
    /**
    * 记录人员id
    */
    @ApiModelProperty("记录人员id")
    private Integer recordingPersonnelId;
    /**
    * 加工产品名称
    */
    @ApiModelProperty("加工产品名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String agriculturalProductName;
    /**
    * 加工工厂名称
    */
    @ApiModelProperty("加工工厂名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String processingPlantName;
    /**
    * 操作人员名称
    */
    @ApiModelProperty("操作人员名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String operatorName;
    /**
    * 记录人员名称
    */
    @ApiModelProperty("记录人员名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String recordingPersonnelName;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
