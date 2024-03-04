import request from '@/utils/request'

//获取农作物信息
export function getCountList() {
  return request({
    url: '/system/user/getListByCreated',
    method: 'get',
  })
}
