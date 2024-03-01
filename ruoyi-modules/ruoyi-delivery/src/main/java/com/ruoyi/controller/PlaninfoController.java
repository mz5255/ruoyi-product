package com.ruoyi.controller;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.io.file.FileNameUtil;
import com.ruoyi.config.MinioConfig;
import com.ruoyi.config.R;
import com.ruoyi.fegin.SysUserFeginService;
import com.ruoyi.system.api.domain.SysUser;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.domain.PlanInfo;
import com.ruoyi.service.PlanInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 农作物种植记录跟踪信息Controller
 *
 * @author ruoyi
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/planInfo")
public class PlaninfoController extends BaseController
{



    @Autowired
    private PlanInfoService planInfoService;
    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysUserFeginService sysUserFeginService;

    @Autowired
    HttpServletResponse response;

    /**
     * 下载excel
     */
    @GetMapping("downExcel")
    public void getDownExcel(){
        List<PlanInfo> planInfos = planInfoService.selectPlanInfoList(new PlanInfo());
        System.out.println(planInfos);
        com.ruoyi.config.ExcelUtil.downExcel("用户列表","用户列表",planInfos,response);
    }


    /**
     * 图片上传
     */
    @CrossOrigin
    @RequestMapping(value = "/uploadUrl")
    public HashMap<String,Object> getUploadUrl(MultipartFile file){
        String filename = file.getOriginalFilename();
        HashMap<String, Object> map = new HashMap<>();
        long size = file.getSize();//获取文件长度
        if(size>1*1024*1024){
            map.put("code",10001);
            map.put("msg","文件长度不能大于1MB");
            return map;
        }
        MinioClient build = MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .build();
        try {
            build.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(filename)
                    .stream(file.getInputStream(),file.getSize(),-1)
                    .build());

        }catch (Exception e){
            e.printStackTrace();
        }

        map.put("data",minioConfig.getEndpoint()+"/"+minioConfig.getBucket()+"/"+filename);//发送路径给前端

        return map;
    }

    /**
     * 查询用户列表
     */
    @GetMapping("userList")
    public R getUserList(){
        List list  = null;
        if(redisTemplate.hasKey("planInfoSysUserList")){
            list = redisTemplate.opsForList().range("planInfoSysUserList", 0, -1);
        }else{
        HashMap<String, Object> usetList = sysUserFeginService.getUsetList();
            list = (List) usetList.get("list");
            redisTemplate.opsForList().rightPushAll("planInfoSysUserList",list);
            redisTemplate.expire("planInfoSysUserList",30, TimeUnit.MINUTES);
        }
        return R.success(list);
    }

    /**
     * 查询农作物种植记录跟踪信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(PlanInfo planInfo)
    {
        startPage();
        List<PlanInfo> list = planInfoService.selectPlanInfoList(planInfo);
        return getDataTable(list);
    }

    /**
     * 导出农作物种植记录跟踪信息列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlanInfo planInfo)
    {
        List<PlanInfo> list = planInfoService.selectPlanInfoList(planInfo);
        ExcelUtil<PlanInfo> util = new ExcelUtil<PlanInfo>(PlanInfo.class);
        util.exportExcel(response, list, "农作物种植记录跟踪信息数据");
    }

    /**
     * 获取农作物种植记录跟踪信息详细信息
     */
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return success(planInfoService.selectPlanInfoByPlanId(planId));
    }

    /**
     * 新增农作物种植记录跟踪信息
     */
    @PostMapping("add")
    public AjaxResult add(@RequestBody PlanInfo planInfo)
    {

        return toAjax(planInfoService.insertPlanInfo(planInfo));
    }

    /**
     * 修改农作物种植记录跟踪信息
     */
    @PutMapping("update")
    public AjaxResult edit(@RequestBody PlanInfo planInfo)
    {
        return toAjax(planInfoService.updatePlanInfo(planInfo));
    }

    /**
     * 删除农作物种植记录跟踪信息
     */
    @DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(planInfoService.deletePlanInfoByPlanIds(planIds));
    }
}
