package com.ruoyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.domain.ProcessingProcessTrackingRecords;
import com.ruoyi.service.ProcessingProcessTrackingRecordsService;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processingProcessTrackingRecords")
public class ProcessingProcessTrackingRecordsController extends BaseController {

    @Autowired
    private ProcessingProcessTrackingRecordsService processingProcessTrackingRecordsService;

    /**
     * 获取加工过程记录跟踪列表
     */
    //@RequiresPermissions("factory:processingProcessTrackingRecords:list")
    @GetMapping("/list")
    public R<?> list(ProcessingProcessTrackingRecords processingProcessTrackingRecords)
    {
        //startPage();
        PageHelper.startPage(processingProcessTrackingRecords.getPageNum(),processingProcessTrackingRecords.getPageSize());
        List<ProcessingProcessTrackingRecords> list = processingProcessTrackingRecordsService.selectProcessingProcessTrackingRecordsList(processingProcessTrackingRecords);
        PageInfo<ProcessingProcessTrackingRecords> info = new PageInfo<>(list);
        return R.ok(info);
    }

    /**
     * 根据id获取加工过程记录跟踪
     */
    //@RequiresPermissions("system:user:list")
    @GetMapping("/getProcessingProcessTrackingRecordsInfo")
    public R<ProcessingProcessTrackingRecords> getProcessingProcessTrackingRecordsInfo(@RequestParam(value = "id") Integer id)
    {
        ProcessingProcessTrackingRecords processingProcessTrackingRecords = processingProcessTrackingRecordsService.getProcessingProcessTrackingRecordsInfo(id);
        return R.ok(processingProcessTrackingRecords);
    }

    /**
     * 根据id删除加工过程记录跟踪
     */
    //@RequiresPermissions("system:user:list")
    @DeleteMapping("/deleteProcessingProcessTrackingRecords")
    public R<?> deleteProcessingProcessTrackingRecords(@RequestParam(value = "id") Integer id)
    {
        int num = processingProcessTrackingRecordsService.deleteProcessingProcessTrackingRecords(id);
        return R.ok(num,"删除成功");
    }


    /**
     * 添加加工过程记录跟踪
     */
    //@RequiresPermissions("system:user:list")
    @PostMapping("/insertProcessingProcessTrackingRecords")
    public R<?> insertProcessingProcessTrackingRecords(@RequestBody ProcessingProcessTrackingRecords processingProcessTrackingRecords)
    {
        Integer num = processingProcessTrackingRecordsService.insertProcessingProcessTrackingRecords(processingProcessTrackingRecords);
        return R.ok(num,"添加成功");
    }

    /**
     * 修改加工过程记录跟踪
     */
    //@RequiresPermissions("system:user:list")
    @PutMapping("/updateProcessingProcessTrackingRecords")
    public R<?> updateProcessingProcessTrackingRecords(@RequestBody ProcessingProcessTrackingRecords processingProcessTrackingRecords)
    {
        Integer num = processingProcessTrackingRecordsService.updateProcessingProcessTrackingRecords(processingProcessTrackingRecords);
        return R.ok(num,"修改成功");
    }

}
