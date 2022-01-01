package cn.ecut.lrj.modules.oms.service.impl;

import cn.ecut.lrj.modules.oms.dto.OrderReturnApplyParamsDto;
import cn.ecut.lrj.modules.oms.model.OmsOrderReturnApply;
import cn.ecut.lrj.modules.oms.mapper.OmsOrderReturnApplyMapper;
import cn.ecut.lrj.modules.oms.service.OmsOrderReturnApplyService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@Service
public class OmsOrderReturnApplyServiceImpl extends ServiceImpl<OmsOrderReturnApplyMapper, OmsOrderReturnApply> implements OmsOrderReturnApplyService {

    /**
     * 退货申请初始化
     *
     * @param orderReturnApplyParamsDto
     * @return
     */
    @Override
    public Page<OmsOrderReturnApply> list(OrderReturnApplyParamsDto orderReturnApplyParamsDto) {
        Page<OmsOrderReturnApply> page = new Page<>(orderReturnApplyParamsDto.getPageNum(), orderReturnApplyParamsDto.getPageSize());
        QueryWrapper<OmsOrderReturnApply> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsOrderReturnApply> lambda = queryWrapper.lambda();

        //服务单号
        if (orderReturnApplyParamsDto.getId() != null) {
            lambda.eq(OmsOrderReturnApply::getId, orderReturnApplyParamsDto.getId());
        }

        //receiverKeyword(退货人姓名，退货人电话)
        if (!StrUtil.isBlank(orderReturnApplyParamsDto.getReceiverKeyword())) {
            //收货人电话
            if (orderReturnApplyParamsDto.getReceiverKeyword().matches("[1-9][0-9]{11}")) {
                lambda.eq(OmsOrderReturnApply::getReturnPhone, orderReturnApplyParamsDto.getReceiverKeyword());
            } else {//收货人姓名
                lambda.eq(OmsOrderReturnApply::getReturnName, orderReturnApplyParamsDto.getReceiverKeyword());

            }
        }
        //确认收货状态
        if (orderReturnApplyParamsDto.getStatus() != null && (orderReturnApplyParamsDto.getStatus() == 0 || orderReturnApplyParamsDto.getStatus() == 1)) {
            lambda.eq(OmsOrderReturnApply::getStatus, orderReturnApplyParamsDto.getStatus());
        }

        //createTime提交时间
        if (orderReturnApplyParamsDto.getCreateTime() != null) {
            lambda.eq(OmsOrderReturnApply::getCreateTime, orderReturnApplyParamsDto.getCreateTime());
        }

        //handleMan处理人员
        if (!StrUtil.isBlank(orderReturnApplyParamsDto.getHandleMan())) {
            lambda.eq(OmsOrderReturnApply::getHandleMan, orderReturnApplyParamsDto.getHandleMan());
        }

        //HandleTime处理时间
        if (orderReturnApplyParamsDto.getHandleTime() != null) {
            lambda.eq(OmsOrderReturnApply::getHandleTime, orderReturnApplyParamsDto.getHandleTime());
        }
        Page<OmsOrderReturnApply> page1 = this.page(page, queryWrapper);
        if (page != null) {
            /*omsOrderCacheService.setOrderList(orderParamsDto.getPageNum(),page1);*/
            return page1;
        }

        return null;
    }

    /**
     * 确认退货、拒绝退货
     *
     * @param id
     * @param omsOrderReturnApply
     * @return
     */
    @Override
    public Boolean updateReturnApply(Long id, OmsOrderReturnApply omsOrderReturnApply) {
        omsOrderReturnApply.setId(id);
        return this.updateById(omsOrderReturnApply);
    }
}
