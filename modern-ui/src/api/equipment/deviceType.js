import request from '@/utils/request'

// 查询设备类型列表
export function listdeviceType(query) {
  return request({
    url: '/tsp/equipmentType/list',
    method: 'get',
    params: query
  })
}

// 查询设备类型详细
export function getdeviceType(tspEquipmentTypeId) {
  return request({
    url: '/tsp/equipmentType/list/' + tspEquipmentTypeId ,
    method: 'get'
  })
}

// 新增设备类型
export function adddeviceType(data) {
  return request({
    url: '/tsp/equipmentType/add',
    method: 'post',
    data: data
  })
}

// 修改设备类型
export function updatedeviceType(data) {
  return request({
    url: '/tsp/equipmentType/edit',
    method: 'put',
    data: data
  })
}

// 删除设备类型
export function deldeviceType(tspEquipmentTypeId) {
  return request({
    url: '/tsp/equipmentType/delete/' + tspEquipmentTypeId,
    method: 'delete'
  })
}