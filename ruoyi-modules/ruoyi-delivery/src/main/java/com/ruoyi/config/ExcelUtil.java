package com.ruoyi.config;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
     * 所需jar包
     * @param list
     * @param response
     */

// <dependency>
//            <groupId>org.apache.poi</groupId>
//            <artifactId>poi</artifactId>
//            <version>3.17</version>
//        </dependency>
//        <dependency>
//            <groupId>org.apache.poi</groupId>
//            <artifactId>poi-ooxml</artifactId>
//            <version>3.17</version>
//        </dependency>
//        <dependency>
//            <groupId>com.alibaba</groupId>
//            <artifactId>easyexcel</artifactId>
//            <version>2.2.0-beta2</version>
//        </dependency>


    public void exportUser(List list, HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("用户数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //需要合并的列
            int[] mergeColumeIndex = {1,2,3,4,5,6,7,8,9,10};
            //从第二行后开始合并
            int mergeRowIndex = 1;
            // 调用合并单元格工具类，此工具类是没有根据合并，数据相同就合并了
            ExcelFillCellMergeStrategy excelFillCellMergeStrategy = new ExcelFillCellMergeStrategy(mergeRowIndex,mergeColumeIndex);

            // 调用合并单元格工具类，此工具类是根据工程名称相同则合并后面数据
            //ExcelMergeUtil excelFillCellMergeStrategy = new ExcelMergeUtil(mergeRowIndex,mergeColumeIndex);
            // 这里需要设置不关闭流
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            //设置背景颜色
            headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            //设置头字体
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontHeightInPoints((short)13);
            headWriteFont.setBold(true);
            headWriteCellStyle.setWriteFont(headWriteFont);
            //设置头居中
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //内容策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //设置 水平居中
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //内容自动换行，实体类不能加@ContentRowHeight() 注解，否则会挤压数据，造成行高固定
            contentWriteCellStyle.setWrapped(true);
            contentWriteCellStyle.setShrinkToFit(true);//设置文本收缩至合适
            // 垂直居中
            contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            //设置左边框
            contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            //设置右边框
            contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
            //设置上边框
            contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
            //设置下边框
            contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

            EasyExcel.write(response.getOutputStream(), list.get(0).getClass())
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(excelFillCellMergeStrategy)
                    .registerConverter(new MapConverter())
                    // 导出文件名
                    .autoCloseStream(Boolean.TRUE).sheet()
                    .doWrite(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * excel 内存往外写方法
     * @param list      数据
     * @param fileName  文件名
     * @param sheetName sheet页的名字
     * @param response  返回给客户端（浏览器）
     */
    /**
     * todo 注意导出前端必须发get请求  但是不能用axios请求
     *  window.location="http://localhost:8902/planInfo/downExcel";
     * @param fileName
     * @param sheetName
     * @param list
     * @param response
     * @param <T>
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
                    .registerConverter(new MapConverter())
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
