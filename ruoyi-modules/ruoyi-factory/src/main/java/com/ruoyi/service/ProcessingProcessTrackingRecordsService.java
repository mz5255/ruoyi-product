package com.ruoyi.service;

import com.ruoyi.domain.ProcessingProcessTrackingRecords;

import java.util.List;

public interface ProcessingProcessTrackingRecordsService {

    List<ProcessingProcessTrackingRecords> selectProcessingProcessTrackingRecordsList(ProcessingProcessTrackingRecords processingProcessTrackingRecords);

    ProcessingProcessTrackingRecords getProcessingProcessTrackingRecordsInfo(Integer id);

    Integer deleteProcessingProcessTrackingRecords(Integer id);

    Integer insertProcessingProcessTrackingRecords(ProcessingProcessTrackingRecords processingProcessTrackingRecords);

    Integer updateProcessingProcessTrackingRecords(ProcessingProcessTrackingRecords processingProcessTrackingRecords);
}
