package com.ruoyi.mapper;

import com.ruoyi.domain.ProcessingProcessTrackingRecords;
import com.ruoyi.domain.SysProductProcessingPlant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.ruoyi.domain.ProcessingProcessTrackingRecords
 */
@Mapper
public interface ProcessingProcessTrackingRecordsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProcessingProcessTrackingRecords record);

    int insertSelective(ProcessingProcessTrackingRecords record);

    ProcessingProcessTrackingRecords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProcessingProcessTrackingRecords record);

    int updateByPrimaryKey(ProcessingProcessTrackingRecords record);

    List<ProcessingProcessTrackingRecords> selectProcessingProcessTrackingRecordsList(@Param("param") ProcessingProcessTrackingRecords processingProcessTrackingRecords);

    List<SysProductProcessingPlant> getSysProductProcessingPlantByProductIds(@Param("productId") List<Integer> productIds);
}




