package com.ruoyi.mapper;

import com.ruoyi.domain.ProcessingPlant;
import com.ruoyi.domain.SysProductProcessingPlant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @Entity com.ruoyi.domain.ProcessingPlant
 */
@Mapper
public interface ProcessingPlantMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProcessingPlant record);

    int insertSelective(ProcessingPlant record);

    ProcessingPlant selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProcessingPlant record);

    int updateByPrimaryKey(ProcessingPlant record);

    List<ProcessingPlant> selectProcessingPlantList(@Param("param") ProcessingPlant processingPlant);

    List<SysProductProcessingPlant> getProductByProcessingPlantId(@Param("id") Integer id);

    void insertBacth(@Param("list") ArrayList<ProcessingPlant> arrayList);
}




