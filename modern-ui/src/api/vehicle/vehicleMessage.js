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
//车辆与设备绑定（目前没有）
export function bindVehicleEquipment(tspVehicleId,tspEquipmentId) {
  return request({
    url: '/tsp/vehicle/bindEquipment/' + tspVehicleId + '/' + tspEquipmentId,
    method: 'patch',
  })
}
//车辆设备解绑
export function unbindVehicleMessage(tspEquipmentId) {
  return request({
    url: '/tsp/vehicle/dealEquipment/' + tspEquipmentId,
    method: 'get',    
  })
}

//当前设备绑定记录
export function equipmentNow(tspEquipmentId) {
  return request({
    url: '/tsp/vehicle/equipmentNow/' + tspEquipmentId,
    method: 'get',
  })
}
//历史绑定设备记录
export function equipmentHistory(tspVehicleId) {
  return request({
    url: '/tsp/vehicle/equipmentHistory/' + tspVehicleId,
    method: 'get',
  })
}
//绑定信息页面车主绑定记录
export function listVehicleOwner( ) {
  return request({
    url: '/tsp/vehicle/useVehicleRecordList',
    method: 'post',
  })
}

//车辆操作栏实名查询
export function queryVehicleAuth(tspVehicleId) {
  return request({
    url: '/tsp/vehicle/getAuditInfo/' + tspVehicleId,
    method: 'get',
  })
}

//车辆列表操作栏绑定记录
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

//经销商名称下拉框
export function listDealerName() {
  return request({
    url: '/tsp/vehicle/saleNameList',
    method: 'get',
  })
} 

//根据经销商名称获取经销商地址
export function dealerAddress(dealerName) {
  return request({
    url: '/tsp/vehicle/saleNameGetAddress/' + dealerName,
    method: 'get',
  })
} 

