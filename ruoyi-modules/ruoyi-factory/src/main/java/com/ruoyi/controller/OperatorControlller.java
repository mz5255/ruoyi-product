package com.ruoyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.domain.Operator;
import com.ruoyi.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorControlller {


    @Autowired
    private OperatorService operatorService;

    /**
     * 获取操作人员列表
     * @param operator
     */
    //@RequiresPermissions("system:user:list")
    @GetMapping("/list")
    public R<?> list(Operator operator)
    {
        PageHelper.startPage(operator.getPageNum(), operator.getPageSize());
        List<Operator> list = operatorService.selectOperatorList(operator);
        PageInfo<Operator> info = new PageInfo<>(list);
        return R.ok(list);
    }


    /**
     * 根据id获取操作人员
     */
    //@RequiresPermissions("system:user:list")
    @GetMapping("/getOperatorInfo")
    public R<Operator> getOperatorInfo(@RequestParam(value = "id") Integer id)
    {
        Operator operator = operatorService.getOperatorInfo(id);
        return R.ok(operator);
    }

    /**
     * 根据id删除操作人员
     */
    //@RequiresPermissions("system:user:list")
    @DeleteMapping("/deleteOperator")
    public R<?> deleteOperator(@RequestParam(value = "id") Integer id)
    {
        Integer num = operatorService.deleteOperator(id);
        return R.ok(num,"删除成功");
    }

    /**
     * 添加操作人员
     */
    //@RequiresPermissions("system:user:list")
    @PostMapping("/insertOperator")
    public R<?> insertOperator(@RequestBody Operator operator)
    {
        Integer num = operatorService.insertOperator(operator);
        return R.ok(num,"添加成功");
    }

    /**
     * 修改操作人员
     */
    //@RequiresPermissions("system:user:list")
    @PutMapping("/updateOperator")
    public R<?> updateOperator(@RequestBody Operator operator)
    {
        Integer num = operatorService.updateOperator(operator);
        return R.ok(num,"修改成功");
    }
}
