package com.ruoyi.fegin.factory;

import com.ruoyi.fegin.ProductFeginService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/3/1 16:17
 */
@Service
public class ProductFactoryImpl implements ProductFeginService {
    @Override
    public Map<String, Object> getProductList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data","熔断器熔断");
        return map;
    }
}
