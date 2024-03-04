package com.ruoyi.domain;

import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 操作人员表
* @TableName operator
*/
@Data
@Accessors(chain = true)
public class Operator {

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
    * 名字
    */
    @ApiModelProperty("名字")
    @Length(max= 255,message="编码长度不能超过255")
    private String name;
    /**
    * 年龄
    */
    @ApiModelProperty("年龄")
    private Integer age;
    /**
    * 性别(0、男 1、女)
    */
    @ApiModelProperty("性别(0、男 1、女)")
    private Integer sex;
    /**
    * 所属工厂id
    */
    @ApiModelProperty("所属工厂id")
    private Integer processingPlantId;
    /**
    * 入职日期
    */
    @ApiModelProperty("入职日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date joinedDate;
    /**
    * 离职日期
    */
    @ApiModelProperty("离职日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date terminationDate;
    /**
    * 是否在职(0、是  1、否)
    */
    @ApiModelProperty("是否在职(0、是  1、否)")
    private Integer status = 0;
    /**
    * 联系电话
    */
    @ApiModelProperty("联系电话")
    @Length(max= 255,message="编码长度不能超过255")
    private String phone;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
