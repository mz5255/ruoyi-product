package com.ruoyi.system.utils;

import com.ruoyi.system.api.domain.SysCity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/3/4 8:32
 */
@RestController
@RequestMapping("three")
public class ThreeUtil {






    @GetMapping("threeList")
        public  List<SysCity> menuListToTree(List<SysCity> list, Integer parentId){
            // 一级目录
            List<SysCity> parentList = list.stream().filter(menu -> menu.getParentId().equals(parentId)).collect(Collectors.toList());
//            // 遍历一级
//            parentList=parentList.stream().map(menu -> {
//                List<Object> menus = menuListToTree(list, menu.getId());
//                    if(menus!=null){
//                        menu.setChildren(menus);
//                }
//                return menu;
//            }).collect(Collectors.toList());
            return parentList;
        }
}
