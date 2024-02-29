package com.ruoyi.config;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.HashMap;
import java.util.Map;

public class MapConverter implements Converter<Map<?, ?> > {

    @Override
    public Class<?> supportJavaTypeKey() {
        // 返回支持的Java类型
        return Map.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        // 返回支持的Excel单元格数据类型
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Map<?, ?> convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) throws Exception {
        // 根据实际需求实现将Excel中的单元格数据转换为Map的方法
        // 这里仅作示例，假设Map的键和值都是字符串类型
        String[] keyValuePairs = cellData.getStringValue().split(",");
        Map<String, String> map = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            if (entry.length == 2) {
                map.put(entry[0], entry[1]);
            }
        }
        return map;
    }

    @Override
    public CellData<String> convertToExcelData(Map<?, ?> value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) throws Exception {
        // 根据实际需求实现将Map转换为Excel中的单元格数据的方法
        // 这里仅作示例，假设Map的键和值都是字符串类型
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : value.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        String cellValue = sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
        return new CellData<>(cellValue);
    }
}