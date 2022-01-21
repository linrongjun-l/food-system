import request from '@/utils/request'
export function policy(params) {
  return request({
    url:'/aliyun/oss/policy',
    method:'get',
    params: params
  })
}
