package com.ruoyi.mapper;

import com.ruoyi.domain.Operator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.ruoyi.domain.Operator
 */
@Mapper
public interface OperatorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Operator record);

    int insertSelective(Operator record);

    Operator selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Operator record);

    int updateByPrimaryKey(Operator record);

    List<Operator> selectOperatorList(@Param("param") Operator operator);
}




