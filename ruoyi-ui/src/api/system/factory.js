import request from '@/utils/request'
import data from "@/views/system/dict/data";

// 查询部门列表
export function listDept(data) {
  return request({
    url: '/factory/processingPlant/list',
    method: 'post',
    dataType:'jsonp',
    data : data
  })
}

export function listDeptTwo(data) {
  return request({
    url: '/factory/processingProcessTrackingRecords/list',
    method: 'post',
    dataType:'jsonp',
    data : data
  })
}
// 查询工厂详细
export function getDept(id) {
  return request({
    url: '/factory/processingPlant/getProcessingPlantInfo?id=' + id,
    method: 'get',
    dataType:'jsonp',
  })
}

export function getDeptRecords(id) {
  return request({
    url: '/factory/processingProcessTrackingRecords/getProcessingProcessTrackingRecordsInfo?id=' + id,
    method: 'get',
    dataType:'jsonp',
  })
}
export function getCityIds(id){
  return request({
    url: '/factory/processingPlant/getCityNums?id=' + id,
    method: 'get',
    dataType: 'jsonp',
  })
}

// 新增工厂
export  function  addDept(data){
  return request({
    url: '/factory/processingPlant/insertProcessingPlant',
    method: 'post',
    dataType:'jsonp',
    data: data
  })
}

export  function  AddRecords(data){
  return request({
    url: '/factory/processingProcessTrackingRecords/insertProcessingProcessTrackingRecords',
    method: 'post',
    dataType:'jsonp',
    data: data
  })
}

// 修改部门
export function updateDept(data) {
  return request({
    url: '/factory/processingPlant/updateProcessingPlant',
    method: 'put',
    data: data
  })
}

// 修改记录
export function updateDeptRecords(data) {
  return request({
    url: '/factory/processingProcessTrackingRecords/updateProcessingProcessTrackingRecords',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delDept(id) {
  return request({
    url: '/factory/processingPlant/deleteProcessingPlant?id=' + id,
    method: 'delete',
  })
}

export function delDeptRecords(id) {
  return request({
    url: '/factory/processingProcessTrackingRecords/deleteProcessingProcessTrackingRecords?id=' + id,
    method: 'delete',
  })
}

export function city(value){
  return request({
    url: '/factory/processingPlant/getArea?id=' + value,
    method: 'get',
    dataType:'jsonp',
  })
}
export function cityList(){
  return request({
    url: '/factory/processingPlant/getArea?id=0',
    method: 'get',
    dataType:'jsonp',
  })
}

export function exportFactory(data){
  return request({
    url: '/factory/processingPlant/export',
    method: 'post',
    dataType:'jsonp',
    responseType:'blob',
    data: data
  })
}

export function ActorList(id){
  return request({
    url: '/factory/processingPlant/getProductByProcessingPlantId?id='+ id,
    method: 'get',
    dataType:'jsonp',
  })
}

export function dataLists(id){
  return request({
    url: '/factory/processingPlant/getOperatorByProcessingPlantId?id='+ id,
    method: 'get',
    dataType:'jsonp',
  })
}

export function fileS3Upload(file){
  return request({
    url: '/factory/processingPlant/importData',
    headers:{'Content-Type': 'multipart/form-data'},
    method: 'post',
    dataType:'formdata',
    data:file
  })
}

export function report() {
  return request({
    url: '/factory/processingPlant/report',
    method: 'get'
  })
}

