package com.ruoyi.fegin;

import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.fegin.factory.SysUserFactoryImpl;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@Component
@FeignClient(name = "ruoyi-system",fallbackFactory = SysUserFactoryImpl.class )
public interface SysUserFeginService {
    @GetMapping("/user/userList")
    public HashMap<String,Object> getUsetList();
}
