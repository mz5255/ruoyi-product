package com.ruoyi.service;

import com.ruoyi.domain.SysProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 会更好
* @description 针对表【sys_product(农产品管理表)】的数据库操作Service
* @createDate 2024-02-29 09:16:23
*/
public interface SysProductService extends IService<SysProduct> {

    List<SysProduct> getManagement(SysProduct sysProduct);
}
