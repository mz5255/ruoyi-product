package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sys_product_log
 */
@TableName(value ="sys_product_log")
@Data
public class SysProductLog implements Serializable {
    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 创建时间
     */
    private Integer spendTime;

    /**
     * 日志信息
     */
    private String logInformetion;

    /**
     * 运行状态(0:正常;1:异常)
     */
    private Integer status;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 操作ip
     */
    private String scopeIp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}