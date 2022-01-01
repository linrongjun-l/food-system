package cn.ecut.lrj.modules.oms.service;

import cn.ecut.lrj.modules.oms.model.OmsOrderReturnReason;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 退货原因表 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
public interface OmsOrderReturnReasonService extends IService<OmsOrderReturnReason> {

    /**
     * 退货原因设置初始化
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<OmsOrderReturnReason> list(Integer pageNum, Integer pageSize);

    /**
     * 添加退货原因
     * @param omsOrderReturnReason
     * @return
     */
    Boolean createReturnReason(OmsOrderReturnReason omsOrderReturnReason);

    /**
     * 修改退货原因
     * @param id
     * @param omsOrderReturnReason
     * @return
     */
    Boolean updateReturnReason(Long id, OmsOrderReturnReason omsOrderReturnReason);

    /**
     * 是否可用
     * @param ids
     * @param status
     * @return
     */
    Boolean updateReturnReasonStatus(List<Long> ids, Integer status);
}
