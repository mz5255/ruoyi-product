package com.ruoyi.service;

import com.ruoyi.domain.Operator;
import com.ruoyi.domain.ProcessingPlant;
import com.ruoyi.domain.SysCity;
import com.ruoyi.domain.SysProductProcessingPlant;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public interface ProcessingPlantService {
    List<ProcessingPlant> selectProcessingPlantList(ProcessingPlant processingPlant);

    ProcessingPlant getProcessingPlantInfo(Integer id);

    Integer deleteProcessingPlant(Integer id);

    Integer insertProcessingPlant(ProcessingPlant processingPlant);

    Integer updateProcessingPlant(ProcessingPlant processingPlant);

    List<SysProductProcessingPlant> getProductByProcessingPlantId(Integer id);

    List<Operator> getOperatorByProcessingPlantId(Integer id);

    List<SysCity> getArea(Integer id);

    void export(List<ProcessingPlant> list, HttpServletResponse response) throws UnsupportedEncodingException;

    String importProcessingPlant(List<ProcessingPlant> list);

    void importData(ArrayList<ProcessingPlant> arrayList);
}
