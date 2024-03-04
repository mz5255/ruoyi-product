package com.ruoyi.mapper;

import java.util.List;
import com.ruoyi.system.api.domain.BaseInfo;

/**
 * 种植基地信息Mapper接口
 *
 * @author ruoyi
 * @date 2024-02-29
 */
public interface BaseInfoMapper
{
    /**
     * 查询种植基地信息
     *
     * @param baseId 种植基地信息主键
     * @return 种植基地信息
     */
    public BaseInfo selectBaseInfoByBaseId(Long baseId);

    /**
     * 查询种植基地信息列表
     *
     * @param baseInfo 种植基地信息
     * @return 种植基地信息集合
     */
    public List<BaseInfo> selectBaseInfoList(BaseInfo baseInfo);

    /**
     * 新增种植基地信息
     *
     * @param baseInfo 种植基地信息
     * @return 结果
     */
    public int insertBaseInfo(BaseInfo baseInfo);

    /**
     * 修改种植基地信息
     *
     * @param baseInfo 种植基地信息
     * @return 结果
     */
    public int updateBaseInfo(BaseInfo baseInfo);

    /**
     * 删除种植基地信息
     *
     * @param baseId 种植基地信息主键
     * @return 结果
     */
    public int deleteBaseInfoByBaseId(Long baseId);

    /**
     * 批量删除种植基地信息
     *
     * @param baseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseInfoByBaseIds(Long[] baseIds);
}
