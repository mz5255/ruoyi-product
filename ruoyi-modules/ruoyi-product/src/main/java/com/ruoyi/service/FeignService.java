package com.ruoyi.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.domain.ProcessingPlant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(value = "factory/processingPlant")
public interface FeignService {
    @PostMapping("/list")
    public PageInfo<ProcessingPlant> list(@RequestBody ProcessingPlant processingPlant);
}
