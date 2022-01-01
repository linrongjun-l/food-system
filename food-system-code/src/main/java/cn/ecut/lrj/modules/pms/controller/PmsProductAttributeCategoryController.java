package cn.ecut.lrj.modules.pms.controller;


import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.pms.dto.ProductAttributeCategoryDto;
import cn.ecut.lrj.modules.pms.model.PmsProductAttributeCategory;
import cn.ecut.lrj.modules.pms.service.PmsProductAttributeCategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@RestController
@Api(tags = "PmsProductAttributeCategoryController", description = "产品属性分类表")
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;


    @ApiOperation("获取产品属性分类")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        Page page = (Page) pmsProductAttributeCategoryService.list(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation("添加产品属性分类")
    @PostMapping(value = "/create")
    public CommonResult create(PmsProductAttributeCategory pmsProductAttributeCategory) {
        boolean result = pmsProductAttributeCategoryService.save(pmsProductAttributeCategory);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("修改产品属性分类")
    @PostMapping(value = "/update/{id}")
    public CommonResult updateProductCategory(PmsProductAttributeCategory pmsProductAttributeCategory, @PathVariable("id") Long id) {

        boolean result = pmsProductAttributeCategoryService.updateById(pmsProductAttributeCategory);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }



    @ApiOperation("删除产品属性分类")
    @GetMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        boolean result = pmsProductAttributeCategoryService.removeById(id);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("初始化筛选属性级联数据")
    @GetMapping("/list/withAttr")
    public CommonResult<List<ProductAttributeCategoryDto>> listWithAttr() {
        List<ProductAttributeCategoryDto> list = pmsProductAttributeCategoryService.listWithAttr();
        return CommonResult.success(list);
    }
}

