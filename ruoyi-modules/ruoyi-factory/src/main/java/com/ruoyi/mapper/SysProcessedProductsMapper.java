package com.ruoyi.mapper;

import com.ruoyi.domain.SysProcessedProduct ;

/**
 * @Entity generator.domain.SysProcessedProducts
 */
public interface SysProcessedProductsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysProcessedProduct record);

    int insertSelective(SysProcessedProduct record);

    SysProcessedProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysProcessedProduct record);

    int updateByPrimaryKey(SysProcessedProduct record);

}




