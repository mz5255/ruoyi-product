import request from '@/utils/request'

// 查询参数列表
export function getProductManagement(query) {
  return request({
    url: '/product/productManagement/ProductList',
    method: 'get',
    params: query
  })
}
// 修改状态
export function updateStatusById(id) {
  return request({
    url: '/product/productManagement/updateStatusById?productId='+id,
    method: 'get',
  })
}

// 产品新增
export function saveProduct(query) {
  return request({
    url: '/product/productManagement/ProductSave',
    method: 'post',
    params: query
  })
}

//产品删除
export function delProduct(productManagementId) {
  return request({
    url: '/product/productManagement/ProductDel?productManagementId='+productManagementId,
    method: 'get',
  })
}

//产品更改
export function updateProduct(query) {
  return request({
    url: '/product/productManagement/updateProduct',
    method: 'post',
    params: query
  })
}

export function exportExael() {
  return request({
    url: '/product/productManagement/exportExcel',
    method: 'post',
  })
}




