import request from '@/utils/request'
import Vue from 'vue'

// 查询设备信息列表
export function listdeviceModel(query) {
  return request({
    url: '/tsp/equipment/list',
    method: 'get',
    params: query
  })
}
// 新增设备信息
export function adddeviceModel(data) {
  return request({
    url: '/tsp/equipment/add',
    method: 'post',
    data: data
  })
}
// 修改设备信息
export function updatedeviceModel(data) {
    return request({
      url: '/tsp/equipment/edit',
      method: 'put',
      data: data
    })
  }
  // 删除设备信息
export function deldeviceModel(tspEquipmentIds) {
    return request({
      url: '/tsp/equipment/deletes/' + tspEquipmentIds,
      method: 'delete'
    })
  }
  //报废设备
  export function scrapdeviceModel(data) {
    return request({
      url: '/tsp/equipment/scrap',
      method: 'put',
      data: data
    })
  }

  // event Bus 用于无关系组件间的通信。
Vue.prototype.$bus = new Vue()
