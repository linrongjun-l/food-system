package cn.ecut.lrj.modules.oms.service;

import cn.ecut.lrj.modules.oms.dto.OrderParamsDto;
import cn.ecut.lrj.modules.oms.model.OmsOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
public interface OmsOrderService extends IService<OmsOrder> {

    /**
     * 订单表初始化
     * @param orderParamsDto
     * @return
     */
    Page<OmsOrder> list(OrderParamsDto orderParamsDto);

    /**
     * 修改收货人信息
     * @param orderParamsDto
     * @return
     */
    Boolean updateReceiverInfo(OrderParamsDto orderParamsDto);
}
