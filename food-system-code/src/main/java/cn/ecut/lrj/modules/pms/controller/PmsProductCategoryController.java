package cn.ecut.lrj.modules.pms.controller;


import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.pms.dto.ProductCategoryDto;
import cn.ecut.lrj.modules.pms.dto.productCategoryChildrenDto;
import cn.ecut.lrj.modules.pms.model.PmsProductCategory;
import cn.ecut.lrj.modules.pms.service.PmsProductCategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@RestController
@RequestMapping("/productCategory")
@Api(tags = "PmsProductCategoryController", description = "商品分类")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    @ApiOperation("获取商品分类")
    @GetMapping(value = "/list/{parentId}")
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable("parentId") Long parentId,
                                                                @RequestParam("pageNum") Integer pageNum,
                                                                @RequestParam("pageSize") Integer pageSize) {
        Page page = (Page) pmsProductCategoryService.list(parentId, pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation("修改是否在导航栏显示")
    @PostMapping(value = "/update/navStatus")
    public CommonResult updateUavStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("navStatus") Integer navStatus) {
        boolean result = pmsProductCategoryService.updateStatus(ids, navStatus,PmsProductCategory::getNavStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改是否显示")
    @PostMapping(value = "/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids,
                                         @RequestParam("showStatus") Integer showStatus) {
        boolean result = pmsProductCategoryService.updateStatus(ids, showStatus,PmsProductCategory::getShowStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("商品分类删除")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        boolean result = pmsProductCategoryService.delete(id);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("添加商品分类")
    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody ProductCategoryDto productCategoryDto) {
        boolean result = pmsProductCategoryService.create(productCategoryDto);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("初始化修改数据")
    @GetMapping(value = "/{id}")
    public CommonResult<PmsProductCategory> getProductCategory(@PathVariable("id") Long id) {

        PmsProductCategory result = pmsProductCategoryService.getById(id);
        return CommonResult.success(result);
    }

    @ApiOperation("修改商品分类")
    @PostMapping(value = "/update/{id}")
    public CommonResult updateProductCategory(@RequestBody ProductCategoryDto productCategoryDto, @PathVariable("id") Long id) {
        boolean result = pmsProductCategoryService.update(productCategoryDto);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("商品分类下拉级联初始化")
    @GetMapping("/list/withChildren")
    public CommonResult<List<productCategoryChildrenDto>> listWithChildren() {
        List<productCategoryChildrenDto> list = pmsProductCategoryService.listWithChildren();
        return CommonResult.success(list);
    }

}

