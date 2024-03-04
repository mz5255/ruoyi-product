package com.ruoyi.system.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @TableName sys_city
 */
@Data
public class SysCity implements Serializable {


    private List<SysCity> children;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 行政代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 首字母
     */
    private String firstLetter;

    /**
     * 城市等级
     */
    private Integer level;

    private static final long serialVersionUID = 1L;




}
