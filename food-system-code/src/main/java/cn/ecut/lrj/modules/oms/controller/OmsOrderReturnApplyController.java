package cn.ecut.lrj.modules.oms.controller;


import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.oms.dto.OrderReturnApplyParamsDto;
import cn.ecut.lrj.modules.oms.model.OmsOrderReturnApply;
import cn.ecut.lrj.modules.oms.model.OmsOrderReturnReason;
import cn.ecut.lrj.modules.oms.service.OmsOrderReturnApplyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单退货申请 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@RestController
@Api(tags = "OmsOrderReturnApplyController", description = "订单退货申请")
@RequestMapping("/returnApply")
public class OmsOrderReturnApplyController {

    @Autowired
    private OmsOrderReturnApplyService omsOrderReturnApplyService;


    @ApiOperation("退货申请初始化")
    @GetMapping("/list")
    public CommonResult<CommonPage<OmsOrderReturnApply>> list(OrderReturnApplyParamsDto orderReturnApplyParamsDto) {
        Page<OmsOrderReturnApply> page = omsOrderReturnApplyService.list(orderReturnApplyParamsDto);
        QueryWrapper<OmsOrderReturnReason> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(OmsOrderReturnReason::getSort);
        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation("退货原因详情")
    @GetMapping("/{id}")
    public CommonResult getApplyDetail(@PathVariable("id") Long id) {
        OmsOrderReturnApply omsOrderSetting = omsOrderReturnApplyService.getById(id);
        return CommonResult.success(omsOrderSetting);
    }



    @ApiOperation("确认退货、拒绝退货")
    @PostMapping("/update/status/{id}")
    public CommonResult updateReturnApply(@PathVariable("id") Long id,
                                          @RequestBody OmsOrderReturnApply omsOrderReturnApply) {
        Boolean result = omsOrderReturnApplyService.updateReturnApply(id, omsOrderReturnApply);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }



    @ApiOperation("删除退货中申请处理")
    @PostMapping("/delete")
    public CommonResult deleteReturnApply(List<Long> ids) {
        Boolean result = omsOrderReturnApplyService.removeByIds(ids);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

}

