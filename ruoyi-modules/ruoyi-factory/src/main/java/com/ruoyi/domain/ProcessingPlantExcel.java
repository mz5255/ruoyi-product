package com.ruoyi.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
* 加工工厂
* @TableName processing_plant
*/
@Data
public class ProcessingPlantExcel {


    private String name;


    private Date createTime;


    private String statusToString;



    private String legalPerson;


    private Integer personNumber;

    private String baseProvinceName;



    private String baseCityName;



    private String baseAreaName;

}
