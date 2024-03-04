package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.SysCity;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【sys_city】的数据库操作Mapper
* @createDate 2024-02-23 08:01:34
* @Entity com.ruoyi.system.domain.SysCity
*/
public interface SysCityMapper {

    List<SysCity> getTreeList(int i);
}




