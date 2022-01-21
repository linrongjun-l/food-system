import request from '@/utils/request'
export function getOrderCount(beginTime,endTime) {
  return request({
    url:'/order/statistics/'+beginTime+"/"+endTime,
    method:'get',
    async: false
  })
}

export function totalOrderSale(countDay){
  return request({
    url:'/order/statistics/'+countDay,
    method:'get',
  })
}

export function getOrderStatus(){
  return request({
    url: '/order/statistics/status',
    method: 'get'
  })
}

export function getMonthOrder(){
  return request({
    url: '/order/statistics/monthOrder',
    method: 'get'
  })
}

export function getWeekOrder(){
  return request({
    url: '/order/statistics/weekOrder',
    method: 'get'
  })
}
