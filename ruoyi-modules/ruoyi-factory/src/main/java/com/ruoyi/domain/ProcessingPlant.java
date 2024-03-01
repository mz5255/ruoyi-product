package com.ruoyi.domain;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = {"序号"}, index = 0)
    private Integer id;
    /**
    * 版本号
    */
    @ApiModelProperty("版本号")
    @ExcelIgnore
    private Integer version;
    /**
    * 工厂名称
    */
    @ApiModelProperty("工厂名称")
    @Length(max= 255,message="编码长度不能超过255")
    @ExcelProperty(value = {"工程名称"}, index = 1)
    private String name;
    /**
    * 地址
    */
    @ApiModelProperty("地址")
    @Length(max= 255,message="编码长度不能超过255")
    @ExcelIgnore
    private String address;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = {"创建时间"}, index = 2)
    private Date createTime;
    /**
    * 状态(0、正常 1、停用)
    */
    @ApiModelProperty("状态(0、正常 1、停用)")
    @ExcelIgnore
    private Integer status;

    @ExcelProperty(value = {"状态"}, index = 3)
    private String statusToString;


    /**
    * 法人
    */
    @ApiModelProperty("法人")
    @Length(max= 255,message="编码长度不能超过255")
    @ExcelProperty(value = {"法人"}, index = 4)
    private String legalPerson;
    /**
    * 工程人数
    */
    @ApiModelProperty("工厂人数")
    @ExcelProperty(value = {"工厂人数"}, index = 5)
    private Integer personNumber;


    //省id
    @ExcelIgnore
    private Integer baseProvinceId;


    //省名称
    @ExcelProperty(value = {"省"}, index = 6)
    private String baseProvinceName;


    //市id
    @ExcelIgnore
    private Integer baseCityId;


    //市名称
    @ExcelProperty(value = {"市"}, index = 7)
    private String baseCityName;

    //区id
    @ExcelIgnore
    private Integer baseAreaId;


    //区名称
    @ExcelProperty(value = {"省"}, index = 8)
    private String baseAreaName;



    @ExcelIgnore
    private Integer pageNum = 1;

    @ExcelIgnore
    private Integer pageSize = 10;
}
