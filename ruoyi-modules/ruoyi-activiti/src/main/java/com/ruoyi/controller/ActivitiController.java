package com.ruoyi.controller;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.config.PageBean;
import com.ruoyi.config.R;
import com.ruoyi.domain.ApplyFor;
import com.ruoyi.domain.ApplyRocord;
import com.ruoyi.domain.vo.ApplyForVo;
import com.ruoyi.fegin.SysUserService;
import com.ruoyi.service.ApplyForService;
import com.ruoyi.service.ApplyRocordService;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 马震
 * @version 1.0
 * @date 2024/2/26 21:26
 */

@RestController
@RequestMapping("activiti")
public class ActivitiController {


    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;

    @Autowired
    ApplyForService applyForService;
    @Autowired
    ApplyRocordService applyRocordService;
    @Autowired
    SysUserService sysUserService;






    /**
     * 测试接口连接
     * @return
     */
    @PostMapping("ll")
    public String get(){
        return "11";
    }

    /**
     *
     */
    @GetMapping("getApplyRocordList")
    public R getApplyRocordList(ApplyRocord applyRocord, PageBean pageBean){

        if(StringUtils.isEmpty(applyRocord.getRecordName())){
            return R.failure("请先登录");

        }
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("record_name",applyRocord.getRecordName());
        List list = applyRocordService.list(queryWrapper);
        PageInfo<ApplyRocord> pageInfo = new PageInfo<>(list);
        return R.success(pageInfo);
    }




    /**
     * 生成请假单
     */
    @PostMapping("add")
    public R addActiviti(@RequestBody ApplyFor applyFor){
        HashMap<String, Object> map = new HashMap<>();


        //防重复提交
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("user_name",applyFor.getUserName());
        queryWrapper.eq("status",0);
        ApplyFor dbApplyFor = applyForService.getOne(queryWrapper);
        if(dbApplyFor != null ){
            return R.failure(applyFor.getUserName()+"没有被审核,请不要连续申请");
        }

//        SysUser user = sysUserService.getUserByUsername(applyFor.getUserName());
//        if(user == null){
//            return R.failure(applyFor.getUserName()+"没有被删除,状态异常请联系管理员");
//        }


        applyFor.setCreated(new Date());
        applyForService.save(applyFor);
        map.put("name","admin");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("applyFor",applyFor.getNum(), map);
        return R.success(processInstance.getProcessInstanceId());
    }

    /**
     *展示流程定义
     */
    @GetMapping("activitiList")
    public R getActivitiList(String username){
        if(!username.equals("admin")){
            return R.failure("该用户没有待办");
        }

        List<Task> taskList = null ;
        ArrayList<ApplyForVo> list = new ArrayList<>();
           taskList =  taskService.createTaskQuery()
                    .processDefinitionKey("applyFor")
                    .taskAssignee(username)
                    .list();

        taskList.forEach(r->{
            ApplyForVo applyForVo = new ApplyForVo();

            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(r.getProcessInstanceId())
                    .singleResult();

            ApplyFor applyFor = applyForService.getById(historicProcessInstance.getBusinessKey());

            if(applyFor != null){
                applyForVo.setTaskName(r.getName());
                applyForVo.setTaskId(r.getId());
                applyForVo.setAssignee(r.getAssignee());
                applyForVo.setInstanceId(r.getProcessInstanceId());

                applyForVo.setName(applyFor.getUserName());
                applyForVo.setCreated(applyFor.getCreated());
                applyForVo.setReason(applyFor.getReason());
                applyForVo.setNum(applyFor.getNum());
                applyForVo.setUserId(applyFor.getUserId());
                applyForVo.setStatus(2);
            }
            list.add(applyForVo);
        });
        return R.success(list);
    }

    /**
     *流程审批
     */
    @PostMapping("complete")
    @Transactional
    public R applyForComplete(@RequestBody ApplyForVo applyForVo){

        //创建一个map集合用于给流程变量赋值
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",applyForVo.getName());
        map.put("flag",applyForVo.getFlag());
        taskService.complete(applyForVo.getTaskId(),map);

        //查出历史表任务id
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(applyForVo.getInstanceId())
                .singleResult();
        //查出当前流程任务
        ApplyFor applyFor = applyForService.getById(historicProcessInstance.getBusinessKey());

        //修改登录用户状态
        if(applyForVo.getFlag().equals("1")){
            //修改为同意
            applyFor.setStatus(1);
            //修改用户删除状态
            String num = applyForVo.getNum();
            QueryWrapper queryWrapper =new QueryWrapper();
            queryWrapper.eq("num",num);
            ApplyFor dbApplyFor = applyForService.getOne(queryWrapper);
            String userName = dbApplyFor.getUserName();
            if(userName == null ){
                return R.failure("没有改人信息");
            }
            SysUser sysUser = sysUserService.getUserByUsername(userName);
            sysUser.setDelFlag("0");
            sysUserService.updateUser(sysUser);
            sysUserService.updateEs(sysUser);


        }else{
            //修改为拒绝
            applyFor.setStatus(2);
        }
        applyForService.updateById(applyFor);

        //添加申批记录
        ApplyRocord applyRocord = new ApplyRocord();
        applyRocord.setRecordText(applyForVo.getRecordText());
        applyRocord.setApplyForNum(applyFor.getNum());
        applyRocord.setPosition("总经理");
        applyRocord.setStatus(applyFor.getStatus());
        applyRocord.setCreated(new Date());
        applyRocord.setRecordName("admin");
        applyRocord.setUserId(applyFor.getUserId());
        applyRocordService.save(applyRocord);

        return R.success(null);
    }



    //4.流程跟踪的接口
    @CrossOrigin
    @RequestMapping("image")
    public void lookImg(String processInstanceId, HttpServletResponse response) throws IOException, TranscoderException {
        InputStream in = this.getProcessDiagram(processInstanceId);

        //svg 转  png
        byte[] streamBytes = IOUtils.toByteArray(in);
        ByteArrayOutputStream byteArrayOutputStream = this.svgToPng(streamBytes);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/png");
        String imageName = "image" + Instant.now().getEpochSecond() + ".png";
        response.addHeader("Content-Disposition", "inline; filename=" + imageName);
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(byteArrayInputStream, outputStream);
        outputStream.close();
    }
    //svg转png
    private ByteArrayOutputStream svgToPng(byte[] streamBytes) throws TranscoderException, IOException {

        PNGTranscoder t = new PNGTranscoder();

        TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(streamBytes));

        ByteArrayOutputStream ostream = new ByteArrayOutputStream();

        TranscoderOutput output = new TranscoderOutput(ostream);

        t.transcode(input, output);

        ostream.flush();
        return ostream;

    }


    /**
     * 获取流程实例图
     */
    public InputStream getProcessDiagram(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        List<String> highLightedActivities =
                historyService.createHistoricActivityInstanceQuery().
                        processInstanceId(processInstanceId).list().stream().
                        map(item -> item.getActivityId()).collect(Collectors.toList());

        // null check
        if (processInstance != null) {
            // get process model
            BpmnModel model = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
            if (model != null && model.getLocationMap().size() > 0) {
                ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
                // 生成流程图 已启动的task 高亮
                return generator.generateDiagram(model,
                        runtimeService.getActiveActivityIds(processInstanceId),
                        highLightedActivities, "simsun", "simsun", "simsun");
                // 生成流程图 都不高亮
//                return generator.generateDiagram(model,Collections.<String>emptyList());
            }
        }
        return null;
    }

}
