package com.ruoyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.PageBean;
import com.ruoyi.common.ResultModel;
import com.ruoyi.domain.SysProduct;
import com.ruoyi.service.SysProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("productManagement")
@RefreshScope
public class ProductController {
    @Autowired
    SysProductService productService;


    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 产品列表
     * @param pageBean
     * @return
     */
    @GetMapping("ProductList")
    public ResultModel productList(PageBean pageBean, SysProduct SysProduct){
//        List<SysProduct> productList = (List<SysProduct>) redisTemplate.opsForValue().get("productList");
//        if (productList!=null){
//            PageHelper.startPage(pageBean);
//            PageInfo<SysProduct> productManagementPageInfo = new PageInfo<>(productList);
//            return ResultModel.successed(productManagementPageInfo);
//        }
        PageHelper.startPage(pageBean);
        List<SysProduct> list = productService.getManagement(SysProduct);
        PageInfo<SysProduct> productManagementPageInfo = new PageInfo<>(list);
//        redisTemplate.opsForValue().set("productList",list);
        return ResultModel.successed(productManagementPageInfo);

    }

    /**
     * 产品新增
     * @param productManagement
     * @return
     */
    @PostMapping("ProductSave")
    public ResultModel ProductSave(SysProduct productManagement){
        productManagement.setCreateTime(new Date());
        productService.save(productManagement);
        return ResultModel.successed(null);
    }

    /**
     * 产品更改
     * @param productManagement
     * @return
     */
    @PostMapping("ProductUpdate")
    public ResultModel ProductUpdate(SysProduct productManagement){
        productService.updateById(productManagement);
        return ResultModel.successed(null);
    }

    /**
     * 逻辑删除
     * @param productManagementId
     * @return
     */
    @PostMapping("ProductDel")
    public ResultModel ProductDel(Integer productManagementId){
        SysProduct productServiceById = productService.getById(productManagementId);
        productServiceById.setDeleted(1);
        productService.updateById(productServiceById);
        return ResultModel.successed(null);
    }

    @GetMapping("updateStatusById")
    public ResultModel updateStatusById(Integer productId){
        SysProduct productServiceById = productService.getById(productId);
        Integer status=null;
        if (productServiceById.getStatus()==1){
            status=2;
        }else {
            status=1;
        }
        productServiceById.setStatus(status);
        productService.updateById(productServiceById);
        return ResultModel.successed(null);
    }

}
