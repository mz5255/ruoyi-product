package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 加工工厂
 * @TableName processing_plant
 */
@TableName(value ="processing_plant")
@Data
public class ProcessingPlant implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 工厂名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态(0、正常 1、停用)
     */
    private Integer status;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 工厂人数
     */
    private Integer personNumber;

    /**
     * 省id
     */
    private Integer baseProvinceId;

    /**
     * 市id
     */
    private Integer baseCityId;

    /**
     * 区id
     */
    private Integer baseAreaId;

    /**
     * 省名称
     */
    private String baseProvinceName;

    /**
     * 市名称
     */
    private String baseCityName;

    /**
     * 区名称
     */
    private String baseAreaName;

    /**
     * 地址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}