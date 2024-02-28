package com.ruoyi.service;

import com.ruoyi.domain.Operator;

import java.util.List;

public interface OperatorService {
    List<Operator> selectOperatorList(Operator operator);

    Operator getOperatorInfo(Integer id);

    Integer deleteOperator(Integer id);

    Integer insertOperator(Operator operator);

    Integer updateOperator(Operator operator);
}
