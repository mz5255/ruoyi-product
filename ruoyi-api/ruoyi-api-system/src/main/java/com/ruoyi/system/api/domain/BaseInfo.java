package com.ruoyi.system.api.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 种植基地信息对象 base_info
 *
 * @author ruoyi
 * @date 2024-02-29
 */
@Data
public class BaseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long baseId;

    /** 编号  */
    @Excel(name = "编号 ")
    private Long baseNum;

    /** 省id */
    @Excel(name = "省id")
    private Long provinceId;

    /** 市id */
    @Excel(name = "市id")
    private Long cityId;

    /** 区id */
    @Excel(name = "区id")
    private Long areaId;

    /** 基地名称 */
    @Excel(name = "基地名称")
    private String baseName;

    /** 合作单位id  */
    @Excel(name = "合作单位id ")
    private Long cooperatorId;

    /** 负责人 */
    @Excel(name = "负责人")
    private String charge;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phone;

    /** 面积  */
    @Excel(name = "面积 ")
    private String area;

    /** 适合种植的农作物 */
    @Excel(name = "适合种植的农作物")
    private String cropName;

    /** 基地状态 */
    @Excel(name = "基地状态")
    private String status;

    /** 排序（级别） */
    @Excel(name = "排序", readConverterExp = "级=别")
    private Long level;

    /** 逻辑删除 */
    private Long delFlag;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;


}
