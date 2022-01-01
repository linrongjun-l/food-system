package cn.ecut.lrj.modules.oms.controller;


import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.oms.model.OmsOrderReturnReason;
import cn.ecut.lrj.modules.oms.service.OmsOrderReturnReasonService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 退货原因表 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@RestController
@Api(tags = "OmsOrderReturnReasonController", description = "退货原因")
@RequestMapping("/returnReason")
public class OmsOrderReturnReasonController {

    @Autowired
    private OmsOrderReturnReasonService omsOrderReturnReasonService;


    @ApiOperation("退货原因设置初始化")
    @GetMapping("/list")
    public CommonResult<CommonPage<OmsOrderReturnReason>> list(@RequestParam("pageNum") Integer pageNum,
                                                               @RequestParam("pageSize") Integer pageSize) {
        Page<OmsOrderReturnReason> page = omsOrderReturnReasonService.list(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }



    @ApiOperation("添加退货原因")
    @PostMapping("/create")
    public CommonResult createReturnReason(@RequestBody OmsOrderReturnReason omsOrderReturnReason) {
        Boolean result = omsOrderReturnReasonService.createReturnReason(omsOrderReturnReason);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }



    @ApiOperation("删除退货原因")
    @PostMapping("/delete")
    public CommonResult deleteReturnReason(@RequestParam("ids") List<Long> ids) {
        Boolean result = omsOrderReturnReasonService.removeByIds(ids);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }



    @ApiOperation("编辑退货原因初始化")
    @GetMapping("/{id}")
    public CommonResult getApplyDetail(@PathVariable("id") Long id) {
        OmsOrderReturnReason omsOrderSetting = omsOrderReturnReasonService.getById(id);
        return CommonResult.success(omsOrderSetting);
    }



    @ApiOperation("修改退货原因")
    @PostMapping("/update/{id}")
    public CommonResult updateReturnReason(@PathVariable("id") Long id,
                                           @RequestBody OmsOrderReturnReason omsOrderReturnReason) {
        Boolean result = omsOrderReturnReasonService.updateReturnReason(id, omsOrderReturnReason);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }



    @ApiOperation("是否可用")
    @PostMapping("/update/status")
    public CommonResult updateReturnReasonStatus(@RequestParam("ids") List<Long> ids,
                                                 @RequestParam("status") Integer status) {
        Boolean result = omsOrderReturnReasonService.updateReturnReasonStatus(ids, status);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}

