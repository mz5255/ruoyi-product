import request from '@/utils/request'

// 查询种植基地信息列表
export function listInfo(query) {
  return request({
    url: '/delivery/baseInfo/list',
    method: 'get',
    params: query
  })
}

// 查询种植基地信息详细
export function getInfo(baseId) {
  return request({
    url: '/delivery/baseInfo/' + baseId,
    method: 'get'
  })
}

// 新增种植基地信息
export function addInfo(data) {
  return request({
    url: '/delivery/baseInfo/add',
    method: 'post',
    data: data
  })
}

// 修改种植基地信息
export function updateInfo(data) {
  return request({
    url: '/delivery/baseInfo/update',
    method: 'put',
    data: data
  })
}

// 删除种植基地信息
export function delInfo(baseId) {
  return request({
    url: '/delivery/baseInfo/' + baseId,
    method: 'delete'
  })
}
