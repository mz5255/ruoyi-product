package com.ruoyi.controller;

import java.util.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.ruoyi.config.minio.MinioConfig;
import com.ruoyi.config.R;
import com.ruoyi.config.excel.ExcelDictDTOListener;
import com.ruoyi.config.minio.MinioUploadEnum;
import com.ruoyi.config.minio.MinioUtil;
import com.ruoyi.config.redis.RedisConfig;
import com.ruoyi.fegin.ProductFeginService;
import com.ruoyi.fegin.SysUserFeginService;
import com.ruoyi.service.CropInfoService;
import com.ruoyi.system.api.domain.CropInfo;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.system.api.domain.PlanInfo;
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

@Slf4j
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
    @Autowired
    ExcelDictDTOListener excelDictDTOListener;
    @Autowired
    ProductFeginService productFeginService;
    @Autowired
    RedisConfig redisConfig;
    @Autowired
    CropInfoService cropInfoService;
    @Autowired
    MinioUtil minioUtil;


    /**
     *
     * 获取农作物信息
     */
    @GetMapping("productList")
    public R getProductList(){
        List<CropInfo> cropInfos = cropInfoService.selectCropInfoList(new CropInfo());
        List cropInfoList = redisConfig.redistCache("cropInfoList", cropInfos);

//        Map<String, Object> productList = productFeginService.getProductList();
//        List data = (List) productList.get("data");
//        List productList1 = redisConfig.redistCache("productList", data);
        return R.success(cropInfoList);
    };






    /**
     * 导入excel
     */
    @CrossOrigin
    @RequestMapping("saveExcel")
    public void saveExcel(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file);
        try {
            // 读取文件流
            EasyExcel.read
                            (file.getInputStream(),// 前端上传的文件
                                    PlanInfo.class,// 跟excel对应的实体类
                                    new ExcelDictDTOListener())// 监听器
                    .excelType(ExcelTypeEnum.XLSX)// excel的类型
                    .sheet().doRead();
            log.info("importData finished");
        } catch (IOException e) {
            log.info("失败");
            e.printStackTrace();
        }
    }



    /**
     * 下载excel
     */
    @GetMapping("downExcel")
    public void getDownExcel(){
        try {
            ArrayList<PlanInfo> list = new ArrayList<>();
            List<PlanInfo> planInfos = planInfoService.selectPlanInfoList(new PlanInfo());
            for (PlanInfo r : planInfos) {
                if (r.getDelFlag() != 0) {
                    r.setDelFlagString("已删除");
                } else {
                    r.setDelFlagString("未删除");
                }
//                //如果图片不为空 则下载到本地
//                if (planInfo.getPic() != null && planInfo.getPic() != "") {
//                    //图片网络地址
//                    URI url = new URI(planInfo.getPic());
//
//                    URLConnection conn = url.toURL().openConnection();
//
//                    InputStream inputStream = conn.getInputStream();
//                    //替换图片名称
//                    String fileName =
//                            com.ruoyi.common.core.utils.
//                                    uuid.UUID.randomUUID().toString().toString() +
//                                    planInfo.getPic().substring(planInfo.getPic().lastIndexOf("."));
//                    //图片在本地的存储位置
//                    String SYSPATH ="C:/Users/Lenovo/Desktop/新建文件夹/";//本地地址
//                    String path = SYSPATH + fileName;
//                    FileOutputStream outputStream = new FileOutputStream(path);
//
//                    //写入到本地
//                    int bytesRead;
//                    byte[] buffer = new byte[4096];
//                    while ((bytesRead = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, bytesRead);
//                    }
//
//                    outputStream.close();//关流
//                    inputStream.close();//关流
//
//                    planInfo.setPic(path);
//                    list.add(planInfo);
//                }
            }
            System.out.println(planInfos);
            int mergeRowIndex =1;
            int [] mergeColumIndex = {1};
            int page = 5;
            excelDictDTOListener.downExcel("用户列表","用户列表",planInfos,response,mergeRowIndex,mergeColumIndex,page);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 图片上传
     */
    @CrossOrigin
    @RequestMapping(value = "/uploadUrl")
    public HashMap<String,Object> getUploadUrl(MultipartFile file){
        HashMap<String, Object> map = minioUtil.uploadMinioFile(file);
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
        System.out.println(planInfo);
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
