package com.ruoyi.service.serviceImpl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.auth.AuthUtil;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.domain.Operator;
import com.ruoyi.domain.ProcessingProcessTrackingRecords;
import com.ruoyi.domain.SysProductProcessingPlant;
import com.ruoyi.mapper.OperatorMapper;
import com.ruoyi.mapper.ProcessingProcessTrackingRecordsMapper;
import com.ruoyi.service.ProcessingProcessTrackingRecordsService;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProcessingProcessTrackingRecordsServiceImpl implements ProcessingProcessTrackingRecordsService {

    @Autowired
    ProcessingProcessTrackingRecordsMapper mapper;

    @Autowired
    private OperatorMapper operatorMapper;


    @Override
    public List<ProcessingProcessTrackingRecords> selectProcessingProcessTrackingRecordsList(ProcessingProcessTrackingRecords processingProcessTrackingRecords) {

        List<ProcessingProcessTrackingRecords> list = mapper.selectProcessingProcessTrackingRecordsList(processingProcessTrackingRecords);

        return list;
    }

    @Override
    public ProcessingProcessTrackingRecords getProcessingProcessTrackingRecordsInfo(Integer id) {

        return mapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public Integer deleteProcessingProcessTrackingRecords(Integer id) {



        return mapper.deleteByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public Integer insertProcessingProcessTrackingRecords(ProcessingProcessTrackingRecords processingProcessTrackingRecords) {


        StringBuilder productName = new StringBuilder("加工");

        processingProcessTrackingRecords.setProcessTime(new Date());
        String token = SecurityUtils.getToken();
        LoginUser loginUser = AuthUtil.getLoginUser(token);
        processingProcessTrackingRecords.setRecordingPersonnelId(loginUser.getUserid().intValue());
        processingProcessTrackingRecords.setRecordingPersonnelName(loginUser.getUsername());

        Operator operator = operatorMapper.selectByPrimaryKey(Long.valueOf(processingProcessTrackingRecords.getOperatorId()));
        processingProcessTrackingRecords.setOperatorName(operator.getName());

        List<SysProductProcessingPlant> list = mapper.getSysProductProcessingPlantByProductIds(processingProcessTrackingRecords.getProductIds());
        for (SysProductProcessingPlant sysProductProcessingPlant : list) {
            productName.append(sysProductProcessingPlant.getProductName());
        }
        processingProcessTrackingRecords.setProcessContext(productName.toString());
        return mapper.insert(processingProcessTrackingRecords);
    }

    @Override
    public Integer updateProcessingProcessTrackingRecords(ProcessingProcessTrackingRecords processingProcessTrackingRecords) {
        return mapper.updateByPrimaryKey(processingProcessTrackingRecords);
    }
}
