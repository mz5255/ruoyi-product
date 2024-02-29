package com.ruoyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 加工工厂
* @TableName processing_plant
*/
@Data
public class ProcessingPlant {

    /**
    * id
    */
    @ApiModelProperty("id")
    private Integer id;
    /**
    * 版本号
    */
    @ApiModelProperty("版本号")
    private Integer version;
    /**
    * 工厂名称
    */
    @ApiModelProperty("工厂名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String name;
    /**
    * 地址
    */
    @ApiModelProperty("地址")
    @Length(max= 255,message="编码长度不能超过255")
    private String address;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
    * 状态(0、正常 1、停用)
    */
    @ApiModelProperty("状态(0、正常 1、停用)")
    private Integer status;
    /**
    * 法人
    */
    @ApiModelProperty("法人")
    @Length(max= 255,message="编码长度不能超过255")
    private String legalPerson;
    /**
    * 工程人数
    */
    @ApiModelProperty("工程人数")
    private Integer personNumber;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
