package com.ruoyi.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 马震
 * @version 1.0
 * 
 */

public class ExcelUtil {



    /**
     * excel 内存往外写方法
     * @param list      数据
     * @param fileName  文件名
     * @param sheetName sheet页的名字
     * @param response  返回给客户端（浏览器）
     */
    public static  <T> void downExcel(String fileName,String sheetName, List<T> list,HttpServletResponse response) {

        try {
            //        告诉浏览器返回的是 Excel
            //设置响应格式
            response.setContentType("application/vnd.ms-excel;chartset=utf-8"); //文件扩展名为excel格式
            //告诉浏览器编码规则
            response.setCharacterEncoding("utf-8");
            //文件名进行编码
            fileName = URLEncoder.encode(fileName+".xlsx", StandardCharsets.UTF_8.toString());
            //响应头加文件名信息
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName); //触发文件名为filename的“另存为”对话框
            EasyExcel.write(response.getOutputStream())
                    .head(list.get(0).getClass())  //指定导出的 Excel 文件的表头信息
                    .excelType(ExcelTypeEnum.XLSX)  //ExcelTypeEnum.XLSX 表示导出为 Excel 2007 及更高版本的 .xlsx 格式文件
                    .autoCloseStream(true)  //在写入 Excel 文件完成后是否自动关闭输出流
                    .sheet(sheetName)  //设置导出的 Excel 文件的 sheet 名称的方法·
                    .doWrite(list);  //写入excel文件的内容一般为数据库直接查出
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * excel 往内存里面读方法
     * @param obj  通过反射获取该对象
     * @param file 文件
     * @return
     * @param <T>
     */
    public static  <T>  List read(Class<T> obj, MultipartFile file) {

        try{
            System.out.println(file.getInputStream());
            List list = EasyExcel.read(file.getInputStream())
                    .head(obj)
                    .sheet()
                    .doReadSync();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}
