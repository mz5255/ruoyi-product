package com.ruoyi.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.mapper.PlanInfoMapper;
import com.ruoyi.service.PlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.api.domain.PlanInfo;

/**
 * 农作物种植记录跟踪信息Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-29
 */
@Service
public class PlanInfoServiceImpl implements PlanInfoService
{
    @Autowired
    private PlanInfoMapper planInfoMapper;

    /**
     * 查询农作物种植记录跟踪信息
     *
     * @param planId 农作物种植记录跟踪信息主键
     * @return 农作物种植记录跟踪信息
     */
    @Override
    public PlanInfo selectPlanInfoByPlanId(Long planId)
    {
        return planInfoMapper.selectPlanInfoByPlanId(planId);
    }

    /**
     * 查询农作物种植记录跟踪信息列表
     *
     * @param planInfo 农作物种植记录跟踪信息
     * @return 农作物种植记录跟踪信息
     */
    @Override
    public List<PlanInfo> selectPlanInfoList(PlanInfo planInfo)
    {
        return planInfoMapper.selectPlanInfoList(planInfo);
    }

    /**
     * 新增农作物种植记录跟踪信息
     *
     * @param planInfo 农作物种植记录跟踪信息
     * @return 结果
     */
    @Override
    public int insertPlanInfo(PlanInfo planInfo)
    {
        planInfo.setCreateTime(DateUtils.getNowDate());
        return planInfoMapper.insertPlanInfo(planInfo);
    }

    /**
     * 修改农作物种植记录跟踪信息
     *
     * @param planInfo 农作物种植记录跟踪信息
     * @return 结果
     */
    @Override
    public int updatePlanInfo(PlanInfo planInfo)
    {
        return planInfoMapper.updatePlanInfo(planInfo);
    }

    /**
     * 批量删除农作物种植记录跟踪信息
     *
     * @param planIds 需要删除的农作物种植记录跟踪信息主键
     * @return 结果
     */
    @Override
    public int deletePlanInfoByPlanIds(Long[] planIds)
    {
        return planInfoMapper.deletePlanInfoByPlanIds(planIds);
    }

    /**
     * 删除农作物种植记录跟踪信息信息
     *
     * @param planId 农作物种植记录跟踪信息主键
     * @return 结果
     */
    @Override
    public int deletePlanInfoByPlanId(Long planId)
    {
        return planInfoMapper.deletePlanInfoByPlanId(planId);
    }
}
