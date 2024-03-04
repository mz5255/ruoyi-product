package com.ruoyi.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.BaseInfoMapper;
import com.ruoyi.system.api.domain.BaseInfo;

/**
 * 种植基地信息Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-29
 */
@Service
public class BaseInfoServiceImpl implements BaseInfoService
{
    @Autowired
    private BaseInfoMapper baseInfoMapper;

    /**
     * 查询种植基地信息
     *
     * @param baseId 种植基地信息主键
     * @return 种植基地信息
     */
    @Override
    public BaseInfo selectBaseInfoByBaseId(Long baseId)
    {
        return baseInfoMapper.selectBaseInfoByBaseId(baseId);
    }

    /**
     * 查询种植基地信息列表
     *
     * @param baseInfo 种植基地信息
     * @return 种植基地信息
     */
    @Override
    public List<BaseInfo> selectBaseInfoList(BaseInfo baseInfo)
    {
        return baseInfoMapper.selectBaseInfoList(baseInfo);
    }

    /**
     * 新增种植基地信息
     *
     * @param baseInfo 种植基地信息
     * @return 结果
     */
    @Override
    public int insertBaseInfo(BaseInfo baseInfo)
    {
        baseInfo.setCreateTime(DateUtils.getNowDate());
        return baseInfoMapper.insertBaseInfo(baseInfo);
    }

    /**
     * 修改种植基地信息
     *
     * @param baseInfo 种植基地信息
     * @return 结果
     */
    @Override
    public int updateBaseInfo(BaseInfo baseInfo)
    {
        return baseInfoMapper.updateBaseInfo(baseInfo);
    }

    /**
     * 批量删除种植基地信息
     *
     * @param baseIds 需要删除的种植基地信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseInfoByBaseIds(Long[] baseIds)
    {
        return baseInfoMapper.deleteBaseInfoByBaseIds(baseIds);
    }

    /**
     * 删除种植基地信息信息
     *
     * @param baseId 种植基地信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseInfoByBaseId(Long baseId)
    {
        return baseInfoMapper.deleteBaseInfoByBaseId(baseId);
    }
}
