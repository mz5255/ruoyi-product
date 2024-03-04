package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName apply_rocord
 */
@TableName(value ="apply_rocord")
@Data
public class ApplyRocord implements Serializable {
    /**
     * 审批单号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 职位
     */
    private String position;

    /**
     * 审批人
     */
    private String recordName;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    /**
     * 审批内容
     */
    private String recordText;

    /**
     * 审批结果
     */
    private Integer status;

    /**
     * 备用字段
     */
    private String remark;

    /**
     * 申请单号
     */
    private String applyForNum;

    /**
     *
     */
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
