package com.ruoyi.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/2/28 21:18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class R {
    private Integer code;

    private String msg;

    private Object data;

    public static R success(Object data){
        return new R(200,"操作成功",data);
    }

    public static R failure(String msg){
        return new R(400,msg,null);
    }

}
