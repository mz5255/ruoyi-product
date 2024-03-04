package com.ruoyi.system.utils;

import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.repository.SysUserRepository;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/2/25 13:42
 */
@Component
public class UserUtil {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysUserRepository sysUserRepository;

    /**
     * 全量同步到es
     * @param id
     */
    public  void fullSync(long id){
        SysUser sysUser = userService.selectUserById(id);
        sysUserRepository.save(sysUser);
    }

}
