package com.ruoyi.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.domain.CropInfo;
import com.ruoyi.service.CropInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 农作物信息Controller
 *
 * @author ruoyi
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/cropInfo")
public class CropInfoController extends BaseController
{
    @Autowired
    private CropInfoService cropInfoService;

    /**
     * 查询农作物信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CropInfo cropInfo)
    {
        startPage();
        List<CropInfo> list = cropInfoService.selectCropInfoList(cropInfo);
        return getDataTable(list);
    }

    /**
     * 导出农作物信息列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, CropInfo cropInfo)
    {
        List<CropInfo> list = cropInfoService.selectCropInfoList(cropInfo);
        ExcelUtil<CropInfo> util = new ExcelUtil<CropInfo>(CropInfo.class);
        util.exportExcel(response, list, "农作物信息数据");
    }

    /**
     * 获取农作物信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cropInfoService.selectCropInfoById(id));
    }

    /**
     * 新增农作物信息
     */
    @PostMapping("add")
    public AjaxResult add(@RequestBody CropInfo cropInfo)
    {
        return toAjax(cropInfoService.insertCropInfo(cropInfo));
    }

    /**
     * 修改农作物信息
     */
    @PutMapping("update")
    public AjaxResult edit(@RequestBody CropInfo cropInfo)
    {
        return toAjax(cropInfoService.updateCropInfo(cropInfo));
    }

    /**
     * 删除农作物信息
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cropInfoService.deleteCropInfoByIds(ids));
    }
}
