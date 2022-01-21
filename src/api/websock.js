import request from '@/utils/request'
export function getWebSockNewOrder(){
  return request({
    url: '/api/order/getNewOrderList/store1',
    method: 'get'
  })
}
