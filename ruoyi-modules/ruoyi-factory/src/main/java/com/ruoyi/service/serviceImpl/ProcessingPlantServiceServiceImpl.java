package com.ruoyi.service.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.ruoyi.domain.Operator;
import com.ruoyi.domain.ProcessingPlant;
import com.ruoyi.domain.SysCity;
import com.ruoyi.domain.SysProductProcessingPlant;
import com.ruoyi.mapper.OperatorMapper;
import com.ruoyi.mapper.ProcessingPlantMapper;
import com.ruoyi.mapper.SysCityMapper;
import com.ruoyi.service.ProcessingPlantService;
import com.ruoyi.util.MapConverter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessingPlantServiceServiceImpl implements ProcessingPlantService {

    @Autowired
    private ProcessingPlantMapper processingPlantMapper;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private SysCityMapper cityMapper;

    @Override
    public List<ProcessingPlant> selectProcessingPlantList(ProcessingPlant processingPlant) {
        List<ProcessingPlant> list = processingPlantMapper.selectProcessingPlantList(processingPlant);
        for (ProcessingPlant plant : list) {
            if(plant.getStatus() == 1){
                plant.setStatusToString("正常");
            }else{
                plant.setStatusToString("停用");
            }
        }
        return list;
    }

    @Override
    public ProcessingPlant getProcessingPlantInfo(Integer id) {
        return processingPlantMapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public Integer deleteProcessingPlant(Integer id) {
        return processingPlantMapper.deleteByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public Integer insertProcessingPlant(ProcessingPlant processingPlant) {

        if(processingPlant.getBaseProvinceId()!=null){
            SysCity sysCity = cityMapper.selectByPrimaryKey(Long.valueOf(processingPlant.getBaseProvinceId()));
            processingPlant.setBaseProvinceName(sysCity.getName());
        }

        if(processingPlant.getBaseCityId()!=null){
            SysCity sysCity = cityMapper.selectByPrimaryKey(Long.valueOf(processingPlant.getBaseCityId()));
            processingPlant.setBaseCityName(sysCity.getName());
        }

        if(processingPlant.getBaseAreaId()!=null){
            SysCity sysCity = cityMapper.selectByPrimaryKey(Long.valueOf(processingPlant.getBaseAreaId()));
            processingPlant.setBaseAreaName(sysCity.getName());
        }


        return processingPlantMapper.insert(processingPlant);
    }

    @Override
    public Integer updateProcessingPlant(ProcessingPlant processingPlant) {

        if(processingPlant.getBaseProvinceId()!=null){
            SysCity sysCity = cityMapper.selectByPrimaryKey(Long.valueOf(processingPlant.getBaseProvinceId()));
            processingPlant.setBaseProvinceName(sysCity.getName());
        }

        if(processingPlant.getBaseCityId()!=null){
            SysCity sysCity = cityMapper.selectByPrimaryKey(Long.valueOf(processingPlant.getBaseCityId()));
            processingPlant.setBaseCityName(sysCity.getName());
        }

        if(processingPlant.getBaseAreaId()!=null){
            SysCity sysCity = cityMapper.selectByPrimaryKey(Long.valueOf(processingPlant.getBaseAreaId()));
            processingPlant.setBaseAreaName(sysCity.getName());
        }

        return processingPlantMapper.updateByPrimaryKey(processingPlant);
    }

    @Override
    public List<SysProductProcessingPlant> getProductByProcessingPlantId(Integer id) {

        return processingPlantMapper.getProductByProcessingPlantId(id);
    }

    @Override
    public List<Operator> getOperatorByProcessingPlantId(Integer id) {



        return operatorMapper.getOperatorByProcessingPlantId(id);
    }

    @Override
    public List<SysCity> getArea(Integer id) {
        return cityMapper.getCityList(id);
    }

    @Override
    public void export(List<ProcessingPlant> list, HttpServletResponse response) throws UnsupportedEncodingException {
        try {

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("工厂数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

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

            EasyExcel.write(response.getOutputStream(), ProcessingPlant.class)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerConverter(new MapConverter())
                    // 导出文件名
                    .autoCloseStream(Boolean.TRUE).sheet()
                    .doWrite(list);
        }catch (Exception e){

        }

    }

    @Override
    public String importProcessingPlant(List<ProcessingPlant> list) {
        return null;
    }

    @Override
    public void importData(ArrayList<ProcessingPlant> arrayList) {
        List<SysCity> cities = cityMapper.selectAll();

        for (ProcessingPlant processingPlant : arrayList) {
            if(processingPlant.getStatusToString().equals("正常")){
                processingPlant.setStatus(1);
            }else {
                processingPlant.setStatus(2);
            }
            for (SysCity city : cities) {
                if(processingPlant.getBaseProvinceName()!=null){
                    if(processingPlant.getBaseProvinceName().equals(city.getName())){
                        processingPlant.setBaseProvinceId(city.getId());
                    }
                }

                if(processingPlant.getBaseCityName()!=null){
                    if(processingPlant.getBaseCityName().equals(city.getName())){
                        processingPlant.setBaseCityId(city.getId());
                    }
                }

                if(processingPlant.getBaseAreaName()!=null){
                    if(processingPlant.getBaseAreaName().equals(city.getName())){
                        processingPlant.setBaseAreaId(city.getId());
                    }
                }

            }
        }

        processingPlantMapper.insertBacth(arrayList);
    }
}
