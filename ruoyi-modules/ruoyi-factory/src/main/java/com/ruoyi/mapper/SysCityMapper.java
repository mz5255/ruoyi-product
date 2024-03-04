package com.ruoyi.mapper;

import com.ruoyi.domain.SysCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity generator.domain.SysCity
 */
@Mapper
public interface SysCityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysCity record);

    int insertSelective(SysCity record);

    SysCity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysCity record);

    int updateByPrimaryKey(SysCity record);

    List<SysCity> getCityList(Integer id);

    List<SysCity> selectAll();
}




