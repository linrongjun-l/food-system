package cn.ecut.lrj.modules.oms.service.impl;

import cn.ecut.lrj.modules.oms.model.OmsOrderReturnReason;
import cn.ecut.lrj.modules.oms.mapper.OmsOrderReturnReasonMapper;
import cn.ecut.lrj.modules.oms.service.OmsOrderReturnReasonService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 退货原因表 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@Service
public class OmsOrderReturnReasonServiceImpl extends ServiceImpl<OmsOrderReturnReasonMapper, OmsOrderReturnReason> implements OmsOrderReturnReasonService {


    /**
     * 退货原因设置初始化
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<OmsOrderReturnReason> list(Integer pageNum, Integer pageSize) {
        Page<OmsOrderReturnReason> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OmsOrderReturnReason> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(OmsOrderReturnReason::getSort);
        return this.page(page, queryWrapper);
    }


    /**
     * 添加退货原因
     *
     * @param omsOrderReturnReason
     * @return
     */
    @Override
    public Boolean createReturnReason(OmsOrderReturnReason omsOrderReturnReason) {

        return this.save(omsOrderReturnReason);
    }

    /**
     * 修改退货原因
     *
     * @param id
     * @param omsOrderReturnReason
     * @return
     */
    @Override
    public Boolean updateReturnReason(Long id, OmsOrderReturnReason omsOrderReturnReason) {
        omsOrderReturnReason.setId(id);
        return this.updateById(omsOrderReturnReason);
    }

    /**
     * 是否可用
     *
     * @param ids
     * @param status
     * @return
     */
    @Override
    public Boolean updateReturnReasonStatus(List<Long> ids, Integer status) {
        UpdateWrapper<OmsOrderReturnReason> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(OmsOrderReturnReason::getStatus, status)
                .in(OmsOrderReturnReason::getId, ids);
        return this.update(updateWrapper);
    }
}
