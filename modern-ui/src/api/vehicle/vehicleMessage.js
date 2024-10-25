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