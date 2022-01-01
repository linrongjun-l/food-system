package cn.ecut.lrj.modules.oms.controller;


import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.oms.dto.OrderParamsDto;
import cn.ecut.lrj.modules.oms.model.OmsOrder;
import cn.ecut.lrj.modules.oms.service.OmsOrderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@RestController
@Api(tags = "OmsOrderController", description = "订单表")
@RequestMapping("/order")
public class OmsOrderController {

    @Autowired
    private OmsOrderService omsOrderService;


    @ApiOperation("订单表初始化")
    @GetMapping("/list")
    public CommonResult<CommonPage<OmsOrder>> list(OrderParamsDto orderParamsDto) {
        Page<OmsOrder> page = omsOrderService.list(orderParamsDto);
        return CommonResult.success(CommonPage.restPage(page));
    }



    @ApiOperation("根据订单id获取订单信息")
    @GetMapping("/{id}")
    public CommonResult getOrderDetail(@PathVariable("id") Long id) {
        OmsOrder omsOrder = omsOrderService.getById(id);
        return CommonResult.success(omsOrder);
    }



    @ApiOperation("修改收货人信息")
    @PostMapping("/update/receiverInfo")
    public CommonResult updateReceiverInfo(@RequestBody OrderParamsDto orderParamsDto) {
        Boolean result = omsOrderService.updateReceiverInfo(orderParamsDto);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }



    @ApiOperation("修改备注订单")
    @PostMapping("/update/note")
    public CommonResult updateNote(OrderParamsDto orderParamsDto) {
        Boolean result = omsOrderService.updateReceiverInfo(orderParamsDto);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }



    @ApiOperation("订单发货")
    @PostMapping("/update/delivery")
    public CommonResult updateDelivery(@RequestBody List<OrderParamsDto> orderParamsDto) {

        Boolean result = omsOrderService.updateReceiverInfo(orderParamsDto.get(0));
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }



    @ApiOperation("订单关闭")
    @PostMapping("/update/close")
    public CommonResult updateClose(OrderParamsDto orderParamsDto) {

        Boolean result = omsOrderService.updateReceiverInfo(orderParamsDto);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}

