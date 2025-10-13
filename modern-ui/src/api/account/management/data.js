import request from '@/utils/request'

// 查询字典数据列表
export function listData(query) {
  return request({
    url: '/tsp/user/list',
    method: 'get',
    params: query
  })
}
// 新增账户信息
export function addaccount(data) {
  return request({
    url: '/tsp/user/add',
    method: 'post',
    data: data
  })
}
// 修改账户信息
export function updateaccount(data) {
    return request({
      url: '/tsp/user/edit',
      method: 'put',
      data: data
    })
  }
 // 删除设备信息
export function delaccount(tspUserId) {
    return request({
      url: '/tsp/user/deletes/' + tspUserId ,
      method: 'delete'
    })
  }
//批量删除设备类型
export function batchdelaccount(tspUserIds) {
  return request({
    url: '/tsp/user/deletes/' + tspUserIds ,
    method: 'delete',

  })
}