package cn.ecut.lrj.modules.pms.controller;


import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.pms.dto.ProductConditionDto;
import cn.ecut.lrj.modules.pms.dto.ProductParamsDto;
import cn.ecut.lrj.modules.pms.model.PmsProduct;
import cn.ecut.lrj.modules.pms.service.PmsProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@RestController
@RequestMapping("/product")
@Api(tags = "PmsProductController", description = "商品列表")
public class PmsProductController {

    @Resource
    private PmsProductService productService;

    @ApiOperation("获取商品类表")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProduct>> list(ProductConditionDto productConditionDto){
        Page<PmsProduct> list = productService.list(productConditionDto);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation("商品列表逻辑删除")
    @PostMapping(value = "/update/deleteStatus")
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean result = productService.removeByIds(ids);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("商品上架")
    @PostMapping("/update/publishStatus")
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
        Boolean result = productService.updateStatus(ids, publishStatus, PmsProduct::getPublishStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("商品新品")
    @PostMapping("/update/newStatus")
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {
        Boolean result = productService.updateStatus(ids, newStatus, PmsProduct::getNewStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("商品推荐")
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("recommendStatus") Integer recommendStatus) {
        Boolean result = productService.updateStatus(ids, recommendStatus, PmsProduct::getRecommandStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("商品添加")
    @PostMapping("/create")
    public CommonResult create(@RequestBody ProductParamsDto productParamsDto) {

        Boolean result = productService.create(productParamsDto);

        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

}

