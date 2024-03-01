package com.ruoyi.fegin.factory;

import com.ruoyi.fegin.SysUserFeginService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/2/29 15:02
 */
@Service
public class SysUserFactoryImpl implements SysUserFeginService {

    @Override
    public HashMap<String, Object> getUsetList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data","熔断器熔断");
        return map;
    }
}
