package com.ruoyi.system.service.impl;

import com.ruoyi.system.api.domain.SysCity;
import com.ruoyi.system.mapper.SysCityMapper;
import com.ruoyi.system.service.ISysCityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/2/23 8:02
 */
@Service
public class SysCityServiceImpl implements ISysCityService {

    @Resource
    SysCityMapper sysCityMapper;

    @Override
    public List<SysCity> myList(int i) {
       List<SysCity> list = sysCityMapper.getTreeList(i);
        return list;
    }
}
