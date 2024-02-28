package com.ruoyi.service.serviceImpl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.domain.ProcessingProcessTrackingRecords;
import com.ruoyi.mapper.ProcessingProcessTrackingRecordsMapper;
import com.ruoyi.service.ProcessingProcessTrackingRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProcessingProcessTrackingRecordsServiceImpl implements ProcessingProcessTrackingRecordsService {

    @Autowired
    ProcessingProcessTrackingRecordsMapper mapper;

    @Override
    public List<ProcessingProcessTrackingRecords> selectProcessingProcessTrackingRecordsList(ProcessingProcessTrackingRecords processingProcessTrackingRecords) {

        List<ProcessingProcessTrackingRecords> list = mapper.selectProcessingProcessTrackingRecordsList(processingProcessTrackingRecords);

        return list;
    }

    @Override
    public ProcessingProcessTrackingRecords getProcessingProcessTrackingRecordsInfo(Integer id) {

        return mapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public Integer deleteProcessingProcessTrackingRecords(Integer id) {



        return mapper.deleteByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public Integer insertProcessingProcessTrackingRecords(ProcessingProcessTrackingRecords processingProcessTrackingRecords) {
        processingProcessTrackingRecords.setProcessTime(new Date());
        return mapper.insert(processingProcessTrackingRecords);
    }

    @Override
    public Integer updateProcessingProcessTrackingRecords(ProcessingProcessTrackingRecords processingProcessTrackingRecords) {
        return mapper.updateByPrimaryKey(processingProcessTrackingRecords);
    }
}
