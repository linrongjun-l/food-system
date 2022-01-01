package cn.ecut.lrj.modules.oms.service;

import cn.ecut.lrj.modules.oms.dto.OrderReturnApplyParamsDto;
import cn.ecut.lrj.modules.oms.model.OmsOrderReturnApply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
public interface OmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {

    /**
     * 退货申请初始化
     * @param orderReturnApplyParamsDto
     * @return
     */
    Page<OmsOrderReturnApply> list(OrderReturnApplyParamsDto orderReturnApplyParamsDto);

    /**
     *确认退货、拒绝退货
     * @param id
     * @param omsOrderReturnApply
     * @return
     */
    Boolean updateReturnApply(Long id, OmsOrderReturnApply omsOrderReturnApply);
}
