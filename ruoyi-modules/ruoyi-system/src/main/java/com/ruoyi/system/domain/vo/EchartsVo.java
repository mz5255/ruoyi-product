package com.ruoyi.system.domain.vo;

import lombok.Data;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/3/3 13:40
 */
@Data
public class EchartsVo {

    private String name;

    private Integer value;


    /**
     * 2020-2022
     */
    private Integer vOne;

    /**
     * 2022-2024
     */
    private Integer vTwo;
    /**
     * 2024-2026
     */
    private Integer vThree;
    /**
     * 2026-2028
     */
    private Integer vFour;
}
