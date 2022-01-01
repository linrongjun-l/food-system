package cn.ecut.lrj.modules.oms.service.impl;

import cn.ecut.lrj.modules.oms.dto.OrderParamsDto;
import cn.ecut.lrj.modules.oms.model.OmsOrder;
import cn.ecut.lrj.modules.oms.mapper.OmsOrderMapper;
import cn.ecut.lrj.modules.oms.service.OmsOrderCacheService;
import cn.ecut.lrj.modules.oms.service.OmsOrderService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {

    @Autowired
    private OmsOrderCacheService omsOrderCacheService;

    /**
     * 订单表初始化
     *
     * @param orderParamsDto
     * @return
     */
    @Override
    public Page<OmsOrder> list(OrderParamsDto orderParamsDto) {

        /*Page<OmsOrder> orderList = omsOrderCacheService.getOrderList(orderParamsDto.getPageNum());
        if (orderList!=null)return orderList;*/

        Page<OmsOrder> page = new Page<>(orderParamsDto.getPageNum(), orderParamsDto.getPageSize());
        QueryWrapper<OmsOrder> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsOrder> lambda = queryWrapper.lambda();

        //订单编号
        if (!StrUtil.isBlank(orderParamsDto.getOrderSn())) {
            lambda.eq(OmsOrder::getOrderSn, orderParamsDto.getOrderSn());
        }

        //receiverKeyword(收货人姓名，收货人电话)
        if (!StrUtil.isBlank(orderParamsDto.getReceiverKeyword())) {
            //收货人电话
            if (orderParamsDto.getReceiverKeyword().matches("[1-9][0-9]{11}")) {
                lambda.eq(OmsOrder::getReceiverPhone, orderParamsDto.getReceiverKeyword());
            } else {//收货人姓名
                lambda.eq(OmsOrder::getReceiverName, orderParamsDto.getReceiverKeyword());

            }
        }
        //确认收货状态
        if (orderParamsDto.getStatus() != null && (orderParamsDto.getStatus() == 0 || orderParamsDto.getStatus() == 1)) {
            lambda.eq(OmsOrder::getStatus, orderParamsDto.getStatus());
        }

        //订单类型
        if (orderParamsDto.getOrderType() != null && (orderParamsDto.getOrderType() == 0 || orderParamsDto.getOrderType() == 1)) {
            lambda.eq(OmsOrder::getOrderType, orderParamsDto.getOrderType());
        }

        //订单来源
        if (orderParamsDto.getSourceType() != null && (orderParamsDto.getSourceType() == 0 || orderParamsDto.getSourceType() == 1)) {
            lambda.eq(OmsOrder::getSourceType, orderParamsDto.getSourceType());
        }
        //createTime提交时间
        if (orderParamsDto.getCreateTime() != null) {
            lambda.eq(OmsOrder::getCreateTime, orderParamsDto.getCreateTime());
        }
        Page<OmsOrder> page1 = this.page(page, queryWrapper);
        if (page != null) {
            /*omsOrderCacheService.setOrderList(orderParamsDto.getPageNum(),page1);*/
            return page1;
        }

        return null;
    }

    /**
     * 修改收货人信息、订单备注、订单发货、关闭订单
     *
     * @param orderParamsDto
     * @return
     */
    @Override
    public Boolean updateReceiverInfo(OrderParamsDto orderParamsDto) {
        OmsOrder omsOrder = orderParamsDto;
        if (orderParamsDto.getOrderId() != null) {
            omsOrder.setId(orderParamsDto.getOrderId());
        }
        //关闭订单
        if (orderParamsDto.getIds() != null && orderParamsDto.getIds().size() > 0) {
            /*List<OmsOrder> list = new ArrayList<>();
            for (Long id : orderParamsDto.getIds()) {
                OmsOrder omsOrder1 = new OmsOrder();
                BeanUtil.copyProperties(omsOrder,omsOrder1);
                omsOrder1.setId(id);
                list.add(omsOrder1);
            }
            return this.updateBatchById(list);*/
            UpdateWrapper<OmsOrder> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda()
                    .set(OmsOrder::getNote, omsOrder.getNote())
                    .set(OmsOrder::getStatus, omsOrder.getStatus())
                    .in(OmsOrder::getId, orderParamsDto.getIds());
            return this.update(updateWrapper);

        }
        return this.updateById(omsOrder);
    }
}
