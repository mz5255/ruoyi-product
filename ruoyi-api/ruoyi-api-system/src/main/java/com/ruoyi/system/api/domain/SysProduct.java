package com.ruoyi.system.api.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 农产品管理表
 * @TableName sys_product
 */
@Data
public class SysProduct implements Serializable {
    /**
     * 产品id
     */
    @ExcelIgnore
    private Integer productId;

    /**
     * 版本号
     */
    @ExcelProperty("版本号")
    private BigDecimal version;

    /**
     * 生产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("生产时间")

    private Date createTime;

    /**
     * 农产品名称
     */
    @ExcelProperty("农产品名称")

    private String cultivarName;

    /**
     * 产品的状态 （0、正常 1、下架）
     */
    @ExcelProperty("产品的状态")

    private Integer status;

    /**
     * 产品类型（1、果蔬 2、农作物）
     */
    @ExcelProperty("产品类型")

    private Integer type;

    /**
     * 产品介绍
     */
    @ExcelProperty("产品介绍")

    private String introduce;

    /**
     * 产品照片
     */
    @ExcelProperty("产品照片")

    private String photo;

    /**
     * 排序
     */
    @ExcelProperty("sort")

    private Integer sort;

    /**
     * 逻辑删除(0:删除;1:保留)
     */
    private Integer deleted;

    /**
     * 产品编号
     */
    @ExcelProperty("产品编号")

    private String productNum;

    /**
     * 备用字段
     */
    private String standbyFields;
    /**
     * 备用字段
     */
    private String standbyFieldsTwo;
    /**
     * 备用字段
     */
    private String standbyFieldsThree;

    private static final long serialVersionUID = 1L;
}
