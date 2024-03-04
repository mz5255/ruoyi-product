package com.ruoyi.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/2/27 8:33
 */
@Data
public class ApplyForVo extends BaseEntity {
    /**
     * 申请单号
     */
    private String num;

    /**
     * 申请人
     */
    private String name;

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
     * 审批内容
     */
    private String recordText;

    /**
     * 备用字段
     */
    private String remark;

    /**
     * 审批结果 2未审核
     */
    private Integer status;

    /**
     *用户ID
     */
    private Integer userId;

    private String taskId;

    private String taskName;

    private String assignee;

    private String instanceId;

    /**
     * 审批状态
     */
    private String flag;
}
