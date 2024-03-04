import request from '@/utils/request'



// 查询审批记录列表
export function getApplyRocordList(query) {
  return request({
    url: '/activiti/activiti/getApplyRocordList',
    method: 'get',
    params: query
  })
}


// 查询【请填写功能名称】列表
export function getVoList(query) {
  return request({
    url: '/activiti/activiti/activitiList?username='+query,
    method: 'get',
  })
}


// ；流程审批
export function complete(data) {
  return request({
    url: '/activiti/activiti/complete',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateVo(data) {
  return request({
    url: '/system/vo',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delVo(num) {
  return request({
    url: '/system/vo/' + num,
    method: 'delete'
  })
}
