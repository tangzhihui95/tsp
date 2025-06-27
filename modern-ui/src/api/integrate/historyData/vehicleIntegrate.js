import request from '@/utils/request'

// 查询数据列表
export function listData(query) {
  return request({
    url: '/tsp/vehicle/data/list',
    method: 'get',
    params: query
  })

}
