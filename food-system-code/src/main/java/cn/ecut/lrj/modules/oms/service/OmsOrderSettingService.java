package cn.ecut.lrj.modules.oms.service;

import cn.ecut.lrj.modules.oms.model.OmsOrderSetting;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单设置表 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
public interface OmsOrderSettingService extends IService<OmsOrderSetting> {

    /**
     * 订单设置修改
     * @param id
     * @param omsOrderSetting
     * @return
     */
    Boolean updateOrderSetting(Long id, OmsOrderSetting omsOrderSetting);
}
