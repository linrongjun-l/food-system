package cn.ecut.lrj.modules.oms.service.impl;

import cn.ecut.lrj.modules.oms.model.OmsOrderItem;
import cn.ecut.lrj.modules.oms.mapper.OmsOrderItemMapper;
import cn.ecut.lrj.modules.oms.service.OmsOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单中所包含的商品 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements OmsOrderItemService {

}
