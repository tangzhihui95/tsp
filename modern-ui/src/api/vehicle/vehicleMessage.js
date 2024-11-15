import request from '@/utils/request'

// 查询车辆信息列表
export function listVehicleMessage(query) {
    return request({
      url: '/tsp/vehicle/list',
      method: 'get',
      params: query
    })
  }

//新增车辆信息
export function addVehicleMessage(data) {
  return request({
    url: '/tsp/vehicle/add',
    method: 'post',
    data: data
  })
}

//修改车辆信息
export function updateVehicleMessage(data) {
  return request({
    url: '/tsp/vehicle/edit',
    method: 'put',
    data: data
  })
}

//查看车辆信息详情
export function getVehicleMessage(tspVehicleId) {
  return request({
    url: '/tsp/vehicle/get/' + tspVehicleId,
    method: 'get'
  })
}

//删除单条车辆信息

export function deleteVehicleMessage(tspVehicleId) {
  return request({  
    url: '/tsp/vehicle/delete/' + tspVehicleId,
    method: 'delete'
  })
}

//批量删除车辆信息
export function batchDeleteVehicleMessage(tspVehicleIds) {
  return request({
    url: '/tsp/vehicle/deletes/' + tspVehicleIds,
    method: 'delete',
  })
}

//车辆信息报废
export function scrapVehicleMessage(data) {
  return request({
    url: '/tsp/vehicle/scrap',
    method: 'put',
    data: data
  })
}

//车辆与用户绑定
export function bindVehicleMessage(tspVehicleId,tspUserId) {
  return request({
    url: '/tsp/vehicle/bind/' + tspVehicleId + '/' + tspUserId,
    method: 'patch',
  })
}

//车辆解绑
export function unbindVehicleMessage(tspEquipmentId) {
  return request({
    url: '/tsp/vehicle/dealEquipment/' + tspEquipmentId,
    method: 'get',    
  })
}

//车辆认证查询
export function queryVehicleAuth(tspVehicleId) {
  return request({
    url: '/tsp/vehicle/getAuditInfo/' + tspVehicleId,
    method: 'get',
  })
}

//车辆绑定记录
export function listVehicleBindRecord(tspVehicleId) {
  return request({
    url: '/tsp/vehicle/getBind/' + tspVehicleId,
    method: 'get',
  })
}

//车辆关联账号列表
export function listVehicleMobile() {
  return request({
    url: '/tsp/vehicle/relationMobileOption',
    method: 'get',
  })    
} 