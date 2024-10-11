import request from '@/utils/request'

// 查询设备类型列表
export function listdeviceType(query) {
  return request({
    url: '/tsp/equipmentType/list',
    method: 'get',
    params: query
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
//新增设备型号
export function adddeviceModel(data) {
  return request({  
    url: '/tsp/equipmentModel/add',
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
// 修改设备型号
export function updatedeviceModel(data) {     
  return request({
    url: '/tsp/equipmentModel/edit',
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
//批量删除设备类型
export function batchdeldeviceType(ids) {
  return request({
    url: '/tsp/equipmentType/deletes/' +ids,
    method: 'delete',

  })
}
// 删除设备型号
export function deldeviceModel(tspEquipmentModelId) {
  return request({
    url: '/tsp/equipmentModel/delete/' + tspEquipmentModelId,    
    method: 'delete'              
  })  
}
//下拉框

export function selectdeviceType(data) {
  return request({
    url: '/tsp/equipmentType/selectList',
    method: 'post',
    data: data
  })
}