package com.ruoyi.system.api.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 农作物信息对象 crop_info
 *
 * @author ruoyi
 * @date 2024-02-29
 */
@Data
public class CropInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 编号   */
    @Excel(name = "编号  ")
    private Long cropNum;

    /** 种植基地id */
    @Excel(name = "种植基地id")
    private Long baseId;

    /** 农作物名称 */
    @Excel(name = "农作物名称")
    private String cropName;

    /** 登记时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registrationTime;

    /** 周期 */
    @Excel(name = "周期")
    private String cycle;

    /** 负责人 */
    @Excel(name = "负责人")
    private String baseCharge;

    /** 负责人联系方式 */
    @Excel(name = "负责人联系方式")
    private String basePhone;

    /** 种植方式id */
    @Excel(name = "种植方式id")
    private Long planmethodId;

    /** 灌溉方式id */
    @Excel(name = "灌溉方式id")
    private Long irrigationId;

    /** 肥料类型id */
    @Excel(name = "肥料类型id")
    private Long fertId;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /** 逻辑删除 */
    private Long delFlag;


}
