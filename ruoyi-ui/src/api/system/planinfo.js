import request from '@/utils/request'

//获取农作物信息
export function getProductList() {
  return request({
    url: '/delivery/planInfo/productList',
    method: 'get',
  })
}
// 导出excel
export function downExcel() {
  return request({
    url: '/delivery/planInfo/downExcel',
    method: 'get',
  })
}
// 查询用户列表
export function getListUser() {
  return request({
    url: '/delivery/planInfo/userList',
    method: 'get',
  })
}
// 查询农作物种植记录跟踪信息列表
export function listInfo(query) {
  return request({
    url: '/delivery/planInfo/list',
    method: 'get',
    params: query
  })
}

// 查询农作物种植记录跟踪信息详细
export function getInfo(planId) {
  return request({
    url: '/delivery/planInfo/' + planId,
    method: 'get'
  })
}

// 新增农作物种植记录跟踪信息
export function addInfo(data) {
  return request({
    url: '/delivery/planInfo/add',
    method: 'post',
    data: data
  })
}

// 修改农作物种植记录跟踪信息
export function updateInfo(data) {
  return request({
    url: '/delivery/planInfo/update',
    method: 'put',
    data: data
  })
}

// 删除农作物种植记录跟踪信息
export function delInfo(planId) {
  return request({
    url: '/delivery/planInfo/' + planId,
    method: 'delete'
  })
}
