package com.ruoyi.service;

import com.ruoyi.domain.ProcessingPlant;

import java.util.List;

public interface ProcessingPlantService {
    List<ProcessingPlant> selectProcessingPlantList(ProcessingPlant processingPlant);

    ProcessingPlant getProcessingPlantInfo(Integer id);

    Integer deleteProcessingPlant(Integer id);

    Integer insertProcessingPlant(ProcessingPlant processingPlant);

    Integer updateProcessingPlant(ProcessingPlant processingPlant);
}
