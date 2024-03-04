package com.ruoyi.fegin;

import com.ruoyi.system.api.domain.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("ruoyi-system")
public interface SysUserService {
    @PostMapping("user/getUserByUsername")
    public SysUser getUserByUsername(@RequestParam("username")String username);

    @PostMapping("user/updateUser")
    public void updateUser(@RequestBody SysUser sysUser);

    @PostMapping("user/updateEs")
    public void updateEs(@RequestBody SysUser sysUser);
}
