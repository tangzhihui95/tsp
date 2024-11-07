import request from '@/utils/request'

// 查询车辆类型列表
export function listVehicleType(query) {
    return request({
      url: '/tsp/vehicle/model/list',
      method: 'get',
      params: query
    })
  }
  
// 新增车辆类型
export function addVehicleType(data) {
    return request({
      url: '/tsp/vehicle/model/add',
      method: 'post',
      data: data
    })
}

//新增二级型号
export function addVehicleModel(data) {
  return request({
    url: '/tsp/vehicle/model/addStdModel',
    method: 'post',
    data: data
  })
}

// 修改车辆类型
export function updateVehicleType(data) {
  return request({
    url: '/tsp/vehicle/model/edit',
    method: 'put',
    data: data
  })
}

//修改二级型号
export function updateVehicleModel(data) {
  return request({
    url: '/tsp/vehicle/model/editStdModel',
    method: 'put',
    data: data
  })
}

// 二级型号详情
export function vehicleModelDetail(tspVehicleStdModelId) {
  return request({
    url: '/tsp/vehicle/model/getByTspStdModelId/' + tspVehicleStdModelId,
    method: 'get'
  })  
}

// 删除单条车辆类型
export function delVehicleType(tspVehicleModelId) {
  return request({
    url: '/tsp/vehicle/model/delete/' + tspVehicleModelId,
    method: 'delete'
  })
}

// 批量删除车辆类型
export function batchDelVehicleType(tspVehicleModelIds) {
  return request({
    url: '/tsp/vehicle/model/deletes/' + tspVehicleModelIds,
    method: 'delete',

  })
}

//删除二级型号
export function delVehicleModel(tspVehicleStdModelId) {
  return request({
    url: '/tsp/vehicle/model/deleteStdModel/' + tspVehicleStdModelId,
    method: 'delete'
  })
}

// 二级型号下拉框
export function vehicleTypeModel(data) {
  return request({
    url: '/tsp/vehicle/model/selectChildrenList',
    method: 'post',
    data: data
  })
}
