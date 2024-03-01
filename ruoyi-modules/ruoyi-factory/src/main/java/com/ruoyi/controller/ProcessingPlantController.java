package com.ruoyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.domain.*;
import com.ruoyi.service.ProcessingPlantService;
import com.ruoyi.util.ExcelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    @PostMapping("/list")
    public R<?> list(@RequestBody ProcessingPlant processingPlant)
    {
        PageHelper.startPage(processingPlant.getPageNum(),processingPlant.getPageSize());
        List<ProcessingPlant> list = processingPlantService.selectProcessingPlantList(processingPlant);
        PageInfo<ProcessingPlant> info = new PageInfo<>(list);
        return R.ok(info);
    }


    /**
     * 导出加工列表
     */
    //@RequiresPermissions("system:user:list")
    @PostMapping("/export")
    public void export(@RequestBody ProcessingPlant processingPlant, HttpServletResponse response) throws UnsupportedEncodingException {
        List<ProcessingPlant> list = processingPlantService.selectProcessingPlantList(processingPlant);
        processingPlantService.export(list,response);
    }



    @PostMapping("/importData")
    @CrossOrigin
    public R<?> importData(@RequestParam MultipartFile file) throws Exception {
        /*ExcelUtil<ProcessingPlant> util = new ExcelUtil<ProcessingPlant>(ProcessingPlant.class);
        List<ProcessingPlant> list = util.importExcel(file.getInputStream());
        String message = processingPlantService.importProcessingPlant(list);*/
        ArrayList<ProcessingPlant> arrayList = new ArrayList<>();
        List<ProcessingPlantExcel> list = ExcelUtil.readExcel(file, ProcessingPlantExcel.class);
        for (ProcessingPlantExcel processingPlantExcel : list) {
            ProcessingPlant plant = new ProcessingPlant();
            BeanUtils.copyProperties(processingPlantExcel,plant);
            arrayList.add(plant);
        }
        processingPlantService.importData(arrayList);
        return R.ok();
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
    @PutMapping("/updateProcessingPlant")
    public R<?> updateProcessingPlant(@RequestBody ProcessingPlant processingPlant)
    {
        Integer num = processingPlantService.updateProcessingPlant(processingPlant);
        return R.ok(num,"修改成功");
    }

    /**
     * 获取加工工厂加工的农作物
     */
    //@RequiresPermissions("system:user:list")
    @GetMapping("/getProductByProcessingPlantId")
    public R<?> getProductByProcessingPlantId(@RequestParam("id")Integer id)
    {
        List<SysProductProcessingPlant> list = processingPlantService.getProductByProcessingPlantId(id);
        /*list.add(new SysProductProcessingPlant().setProductId(1).setProductName("西瓜"));
        list.add(new SysProductProcessingPlant().setProductId(2).setProductName("山药"));
        list.add(new SysProductProcessingPlant().setProductId(3).setProductName("水蜜桃"));
        list.add(new SysProductProcessingPlant().setProductId(4).setProductName("黄瓜"));
        list.add(new SysProductProcessingPlant().setProductId(5).setProductName("豆角"));
        list.add(new SysProductProcessingPlant().setProductId(6).setProductName("西红柿"));
        list.add(new SysProductProcessingPlant().setProductId(7).setProductName("茄子"));
        list.add(new SysProductProcessingPlant().setProductId(8).setProductName("大白菜"));*/
        return R.ok(list);
    }

    /**
     * 获取加工工厂的操作人员
     *
     */
    //@RequiresPermissions("system:user:list")
    @GetMapping("/getOperatorByProcessingPlantId")
    public R<?> getOperatorByProcessingPlantId(@RequestParam("id")Integer id)
    {
        List<Operator> list = processingPlantService.getOperatorByProcessingPlantId(id);
        /*list.add(new Operator().setId(1).setName("张三"));
        list.add(new Operator().setId(2).setName("李四"));
        list.add(new Operator().setId(3).setName("王五"));
        list.add(new Operator().setId(4).setName("赵六"));
        list.add(new Operator().setId(5).setName("林云果"));
        list.add(new Operator().setId(6).setName("赵家杨"));*/
        return R.ok(list);
    }

    @GetMapping("/getArea")
    public R<?> getArea(@RequestParam("id")Integer id)
    {
        try {
            List<SysCity> list = processingPlantService.getArea(id);
            return R.ok(list);
        }catch (Exception e){
            System.out.println(e);
        }
        return R.ok();
    }

    /**
     * 查询省市区id的集合
     * @param id
     * @return
     */
    @GetMapping("/getCityNums")
    public ArrayList<Integer> getCityNums(Integer id){
        ProcessingPlant processingPlant = processingPlantService.getProcessingPlantInfo(id);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(processingPlant.getBaseProvinceId());
        ids.add(processingPlant.getBaseCityId());
        ids.add(processingPlant.getBaseAreaId());
        return ids;
    }

}
