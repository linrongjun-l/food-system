package cn.ecut.lrj.modules.oms.service.impl;

import cn.ecut.lrj.modules.oms.model.OmsOrderSetting;
import cn.ecut.lrj.modules.oms.mapper.OmsOrderSettingMapper;
import cn.ecut.lrj.modules.oms.service.OmsOrderSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单设置表 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@Service
public class OmsOrderSettingServiceImpl extends ServiceImpl<OmsOrderSettingMapper, OmsOrderSetting> implements OmsOrderSettingService {

    @Override
    public Boolean updateOrderSetting(Long id, OmsOrderSetting omsOrderSetting) {
        omsOrderSetting.setId(id);
        return this.updateById(omsOrderSetting);
    }
}
