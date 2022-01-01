package cn.ecut.lrj.modules.oms.service;

import cn.ecut.lrj.modules.oms.model.OmsOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public interface OmsOrderCacheService {

    /**
     * 从缓存中获取订单表
     *
     * @param orderId
     * @return
     */
    Page<OmsOrder> getOrderList(Integer orderId);

    /**
     * 设置缓存中获取订单表
     *
     * @param orderId
     */
    void setOrderList(Integer orderId, Page<OmsOrder> omsOrders);
}
