package com.ruoyi.system.api.domain;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.api.domain.CropInfo;
import lombok.Data;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 农作物种植记录跟踪信息对象 plan_info
 *
 * @author 马震
 * @date 2024-02-29
 */
@Data
@ExcelIgnoreUnannotated
public class PlanInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * 农作物信息
     */
    private CropInfo cropInfo;

    /** 农作物记录ID */
    private Long planId;

    /** 农作物信息ID */
    @ExcelProperty(value = "农作物信息ID")
    private Long cropInfoId;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "记录时间")
    private Date recordTime;

    /** 温度 */
    @ExcelProperty(value = "温度")
    private String temperature;

    /** 生长状况 */
    @ExcelProperty(value = "生长状况")
    private String arowths;

    /** 湿度 */
    @ExcelProperty(value = "湿度")
    private String humidness;

    /** 光照 */
    @ExcelProperty(value = "光照")
    private String illumination;

    /** 农作物图片 */
    @ExcelProperty(value = "农作物图片")
    private String pic;

    /** 操作人 */
    @ExcelProperty(value = "操作人")
    private String operator;

    /** 逻辑删除 */
    @ExcelIgnore
    private Long delFlag;
    @ExcelProperty(value = "删除状态")
    private String delFlagString;

}
