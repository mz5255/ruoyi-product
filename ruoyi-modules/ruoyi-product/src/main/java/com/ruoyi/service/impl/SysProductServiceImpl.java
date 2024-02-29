package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.domain.SysProduct;
import com.ruoyi.service.SysProductService;
import com.ruoyi.mapper.SysProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 会更好
* @description 针对表【sys_product(农产品管理表)】的数据库操作Service实现
* @createDate 2024-02-29 09:16:23
*/
@Service
public class SysProductServiceImpl extends ServiceImpl<SysProductMapper, SysProduct>
    implements SysProductService{

    @Override
    public List<SysProduct> getManagement(SysProduct sysProduct) {
        return baseMapper.getManagement(sysProduct);
    }
}




