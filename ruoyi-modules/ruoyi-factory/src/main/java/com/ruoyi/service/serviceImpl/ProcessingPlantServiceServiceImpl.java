package com.ruoyi.service.serviceImpl;

import com.ruoyi.domain.ProcessingPlant;
import com.ruoyi.mapper.ProcessingPlantMapper;
import com.ruoyi.service.ProcessingPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessingPlantServiceServiceImpl implements ProcessingPlantService {

    @Autowired
    private ProcessingPlantMapper processingPlantMapper;

    @Override
    public List<ProcessingPlant> selectProcessingPlantList(ProcessingPlant processingPlant) {
        List<ProcessingPlant> list = processingPlantMapper.selectProcessingPlantList(processingPlant);
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
        return processingPlantMapper.insert(processingPlant);
    }

    @Override
    public Integer updateProcessingPlant(ProcessingPlant processingPlant) {
        return processingPlantMapper.updateByPrimaryKey(processingPlant);
    }
}
