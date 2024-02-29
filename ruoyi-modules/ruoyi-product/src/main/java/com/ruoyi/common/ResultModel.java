package com.ruoyi.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @Desc
* @author 李国树
* @date 2023/2/13 9:05
* @version 1.0
*/
@Data
public class ResultModel implements Serializable {
    @ApiModelProperty("响应码")
    private Integer code;
    @ApiModelProperty("响应描述")
    private String msg;
    @ApiModelProperty("业务数据")
    private Object data;

    public static ResultModel successed(Object data){
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(200);
        resultModel.setMsg("操作成功");
        resultModel.setData(data);
        return resultModel;
    }

    public static ResultModel failed(Integer code,String msg){
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(code);
        resultModel.setMsg(msg);
        resultModel.setData(null);
        return resultModel;
    }
}

 
