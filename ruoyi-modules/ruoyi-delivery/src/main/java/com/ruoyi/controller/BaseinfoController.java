package com.ruoyi.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.BaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.service.BaseInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 种植基地信息Controller
 *
 * @author ruoyi
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/baseInfo")
public class BaseinfoController extends BaseController
{
    @Autowired
    private BaseInfoService baseInfoService;



    /**
     * 查询种植基地信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BaseInfo baseInfo)
    {
        startPage();
        List<BaseInfo> list = baseInfoService.selectBaseInfoList(baseInfo);
        return getDataTable(list);
    }

    /**
     * 导出种植基地信息列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaseInfo baseInfo)
    {
        List<BaseInfo> list = baseInfoService.selectBaseInfoList(baseInfo);
        ExcelUtil<BaseInfo> util = new ExcelUtil<BaseInfo>(BaseInfo.class);
        util.exportExcel(response, list, "种植基地信息数据");
    }

    /**
     * 获取种植基地信息详细信息
     */
    @GetMapping(value = "/{baseId}")
    public AjaxResult getInfo(@PathVariable("baseId") Long baseId)
    {
        return success(baseInfoService.selectBaseInfoByBaseId(baseId));
    }

    /**
     * 新增种植基地信息
     */
    @PostMapping("add")
    public AjaxResult add(@RequestBody BaseInfo baseInfo)
    {
        return toAjax(baseInfoService.insertBaseInfo(baseInfo));
    }

    /**
     * 修改种植基地信息
     */
    @PutMapping("update")
    public AjaxResult edit(@RequestBody BaseInfo baseInfo)
    {
        return toAjax(baseInfoService.updateBaseInfo(baseInfo));
    }

    /**
     * 删除种植基地信息
     */
    @DeleteMapping("/{baseIds}")
    public AjaxResult remove(@PathVariable Long[] baseIds)
    {
        return toAjax(baseInfoService.deleteBaseInfoByBaseIds(baseIds));
    }




}
