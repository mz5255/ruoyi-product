package com.ruoyi.fegin;

import com.ruoyi.fegin.factory.ProductFactoryImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
@Component
@FeignClient(value = "ruoyi-product",fallbackFactory = ProductFactoryImpl.class)
public interface ProductFeginService {
    @GetMapping("/productManagement/ProductListOne")
    public Map<String,Object> getProductList();
}
