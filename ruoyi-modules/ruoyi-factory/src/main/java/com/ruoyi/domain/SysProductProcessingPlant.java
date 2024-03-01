package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
* 操作人员表
* @TableName operator
*/
@Data
@Accessors(chain = true)
public class SysProductProcessingPlant {


    @ApiModelProperty("产品id")
    private Integer productId;

    @ApiModelProperty("工厂id")
    private Integer processingPlantId;

    @ApiModelProperty("产品名字")
    private String productName;

    @ApiModelProperty("删除状态")
    private Integer delFlag;

    @ApiModelProperty("操作人")
    private Integer operator;


    @ApiModelProperty("创建")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

}
