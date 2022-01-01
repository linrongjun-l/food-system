package cn.ecut.lrj.modules.oms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.ecut.lrj.common.service.RedisService;
import cn.ecut.lrj.modules.oms.model.OmsOrder;
import cn.ecut.lrj.modules.oms.service.OmsOrderCacheService;
import cn.ecut.lrj.modules.oms.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 后台订单管理缓存
 */
@Service
public class OmsOrderCacheServiceImpl implements OmsOrderCacheService {
    @Autowired
    private OmsOrderService omsOrderService;

    @Autowired
    private RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.orderList}")
    private String REDIS_KEY_ORDER_LIST;

    @Override
    public Page<OmsOrder> getOrderList(Integer orderId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ORDER_LIST + ":" + orderId;
        return (Page<OmsOrder>) redisService.get(key);
    }

    @Override
    public void setOrderList(Integer orderId, Page<OmsOrder> omsOrders) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ORDER_LIST + ":" + orderId;
        redisService.set(key, omsOrders, REDIS_EXPIRE);
    }
}
