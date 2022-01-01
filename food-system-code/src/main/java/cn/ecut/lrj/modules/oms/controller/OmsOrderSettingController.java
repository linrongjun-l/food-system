package cn.ecut.lrj.modules.oms.controller;


import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.oms.model.OmsOrderSetting;
import cn.ecut.lrj.modules.oms.service.OmsOrderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单设置表 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@RestController
@Api(tags = "OmsOrderSettingController", description = "订单设置表")
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {

    @Autowired
    private OmsOrderSettingService omsOrderSettingService;



    @ApiOperation("订单设置初始化")
    @GetMapping("/{id}")
    public CommonResult list(@PathVariable("id") Long id) {
        OmsOrderSetting omsOrderSetting = omsOrderSettingService.getById(id);
        return CommonResult.success(omsOrderSetting);
    }



    @ApiOperation("订单设置修改")
    @PostMapping("/update/{id}")
    public CommonResult updateOrderSetting(@PathVariable("id") Long id,
                                           @RequestBody OmsOrderSetting omsOrderSetting) {
        Boolean result = omsOrderSettingService.updateOrderSetting(id, omsOrderSetting);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}

