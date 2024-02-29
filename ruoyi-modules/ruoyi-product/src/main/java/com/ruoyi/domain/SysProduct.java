package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 农产品管理表
 * @TableName sys_product
 */
@TableName(value ="sys_product")
@Data
public class SysProduct implements Serializable {
    /**
     * 产品id
     */
    @TableId(type = IdType.AUTO)
    private Integer productId;

    /**
     * 版本号
     */
    private String version;

    /**
     * 生产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 农产品名称
     */
    private String cultivarName;

    /**
     * 产品的状态 （0、正常 1、下架）
     */
    private Integer status;

    /**
     * 产品类型（1、果蔬 2、农作物）
     */
    private Integer type;

    /**
     * 产品介绍
     */
    private String introduce;

    /**
     * 产品照片
     */
    private String photo;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除(0:删除;1:保留)
     */
    private Integer deleted;

    /**
     * 产品编号
     */
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}