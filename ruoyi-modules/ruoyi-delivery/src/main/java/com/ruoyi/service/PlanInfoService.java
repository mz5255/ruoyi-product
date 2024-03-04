package com.ruoyi.service;

import java.util.List;
import com.ruoyi.system.api.domain.PlanInfo;

/**
 * 农作物种植记录跟踪信息Service接口
 *
 * @author ruoyi
 * @date 2024-02-29
 */
public interface PlanInfoService
{
    /**
     * 查询农作物种植记录跟踪信息
     *
     * @param planId 农作物种植记录跟踪信息主键
     * @return 农作物种植记录跟踪信息
     */
    public PlanInfo selectPlanInfoByPlanId(Long planId);

    /**
     * 查询农作物种植记录跟踪信息列表
     *
     * @param planInfo 农作物种植记录跟踪信息
     * @return 农作物种植记录跟踪信息集合
     */
    public List<PlanInfo> selectPlanInfoList(PlanInfo planInfo);

    /**
     * 新增农作物种植记录跟踪信息
     *
     * @param planInfo 农作物种植记录跟踪信息
     * @return 结果
     */
    public int insertPlanInfo(PlanInfo planInfo);

    /**
     * 修改农作物种植记录跟踪信息
     *
     * @param planInfo 农作物种植记录跟踪信息
     * @return 结果
     */
    public int updatePlanInfo(PlanInfo planInfo);

    /**
     * 批量删除农作物种植记录跟踪信息
     *
     * @param planIds 需要删除的农作物种植记录跟踪信息主键集合
     * @return 结果
     */
    public int deletePlanInfoByPlanIds(Long[] planIds);

    /**
     * 删除农作物种植记录跟踪信息信息
     *
     * @param planId 农作物种植记录跟踪信息主键
     * @return 结果
     */
    public int deletePlanInfoByPlanId(Long planId);
}
