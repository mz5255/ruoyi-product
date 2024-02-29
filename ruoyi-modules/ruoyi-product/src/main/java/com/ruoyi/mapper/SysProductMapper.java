package com.ruoyi.mapper;

import com.ruoyi.domain.SysProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 会更好
* @description 针对表【sys_product(农产品管理表)】的数据库操作Mapper
* @createDate 2024-02-29 09:16:23
* @Entity com.ruoyi.domain.SysProduct
*/
public interface SysProductMapper extends BaseMapper<SysProduct> {

    List<SysProduct> getManagement(SysProduct sysProduct);
}




