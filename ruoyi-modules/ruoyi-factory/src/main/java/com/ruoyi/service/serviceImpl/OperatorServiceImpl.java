package com.ruoyi.service.serviceImpl;

import com.ruoyi.domain.Operator;
import com.ruoyi.mapper.OperatorMapper;
import com.ruoyi.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {


    @Autowired
    private OperatorMapper mapper;

    @Override
    public List<Operator> selectOperatorList(Operator operator) {

        List<Operator> list = mapper.selectOperatorList(operator);

        return list;
    }

    @Override
    public Operator getOperatorInfo(Integer id) {
        return mapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public Integer deleteOperator(Integer id) {
        return mapper.deleteByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public Integer insertOperator(Operator operator) {

        return mapper.insert(operator);
    }

    @Override
    public Integer updateOperator(Operator operator) {
        return mapper.updateByPrimaryKey(operator);
    }
}
