package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName apply_for
 */
@TableName(value ="apply_for")
@Data
public class ApplyFor implements Serializable {
    /**
     * 申请单号
     */
    @TableId
    private String num;

    /**
     * 申请人
     */
    private String userName;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 备用字段
     */
    private String remark;

    /**
     * 审批结果0同意1不同意
     */
    private Integer status;

    /**
     *
     */
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
