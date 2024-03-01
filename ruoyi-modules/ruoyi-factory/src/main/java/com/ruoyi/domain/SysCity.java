package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
* 
* @TableName sys_city
*/
@Data
public class SysCity {

    /**
    * 
    */
    @ApiModelProperty("")
    @JsonProperty("value")
    private Integer id;
    /**
    * 行政代码
    */
    @ApiModelProperty("行政代码")
    @Length(max= 50,message="编码长度不能超过50")
    private String code;
    /**
    * 名称
    */
    @ApiModelProperty("名称")
    @Length(max= 255,message="编码长度不能超过255")
    @JsonProperty("label")
    private String name;
    /**
    * 父id
    */
    @ApiModelProperty("父id")
    private Integer parentId;
    /**
    * 首字母
    */
    @ApiModelProperty("首字母")
    @Length(max= 10,message="编码长度不能超过10")
    private String firstLetter;
    /**
    * 城市等级
    */
    @ApiModelProperty("城市等级")
    private Integer level;

    @JsonProperty("children")
    private List<SysCity> areas;

}
