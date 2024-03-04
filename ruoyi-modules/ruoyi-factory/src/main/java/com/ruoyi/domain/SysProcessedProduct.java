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
public class SysProcessedProduct {

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除状态
     */
    private Integer delFlag;

    /**
     * 操作人
     */
    private Integer operator;
}
