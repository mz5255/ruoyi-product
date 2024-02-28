package com.ruoyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.domain.ProcessingPlant;
import com.ruoyi.domain.ProcessingProcessTrackingRecords;
import com.ruoyi.service.ProcessingPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processingPlant")
public class ProcessingPlantController extends BaseController {

    @Autowired
    private ProcessingPlantService processingPlantService;

    /**
     * 获取加工工厂列表
     */
    //@RequiresPermissions("system:user:list")
    @GetMapping("/list")
    public TableDataInfo list(ProcessingPlant processingPlant)
    {
        PageHelper.startPage(processingPlant.getPageNum(),processingPlant.getPageSize());
        List<ProcessingPlant> list = processingPlantService.selectProcessingPlantList(processingPlant);
        PageInfo<ProcessingPlant> info = new PageInfo<>(list);
        return getDataTable(list);
    }

    /**
     * 根据id获取加工工厂
     */
    //@RequiresPermissions("system:user:list")
    @GetMapping("/getProcessingPlantInfo")
    public R<ProcessingPlant> getProcessingPlantInfo(@RequestParam(value = "id") Integer id)
    {
        ProcessingPlant processingPlant = processingPlantService.getProcessingPlantInfo(id);
        return R.ok(processingPlant);
    }

    /**
     * 根据id删除加工工厂
     */
    //@RequiresPermissions("system:user:list")
    @DeleteMapping("/deleteProcessingPlant")
    public R<?> deleteProcessingPlant(@RequestParam(value = "id") Integer id)
    {
        Integer num = processingPlantService.deleteProcessingPlant(id);
        return R.ok(num,"删除成功");
    }


    /**
     * 添加加工工厂
     */
    //@RequiresPermissions("system:user:list")
    @PostMapping("/insertProcessingPlant")
    public R<?> insertProcessingPlant(@RequestBody ProcessingPlant processingPlant)
    {
        Integer num = processingPlantService.insertProcessingPlant(processingPlant);
        return R.ok(num,"添加成功");
    }

    /**
     * 修改加工工厂
     */
    //@RequiresPermissions("system:user:list")
    @PostMapping("/updateProcessingPlant")
    public R<?> updateProcessingPlant(@RequestBody ProcessingPlant processingPlant)
    {
        Integer num = processingPlantService.updateProcessingPlant(processingPlant);
        return R.ok(num,"修改成功");
    }
}
