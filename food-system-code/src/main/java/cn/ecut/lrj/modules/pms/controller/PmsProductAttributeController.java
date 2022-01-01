package cn.ecut.lrj.modules.pms.controller;


import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.pms.dto.RelationAttrInfoDto;
import cn.ecut.lrj.modules.pms.model.PmsProductAttribute;
import cn.ecut.lrj.modules.pms.service.PmsProductAttributeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@RestController
@Api(tags = "PmsProductAttributeController", description = "商品属性参数表")
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;


    @ApiOperation("初始化商品类型-属性列表")
    @RequestMapping("/list/{cid}")
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable("cid") Long cid,
                                                                 @RequestParam("pageNum") Integer pageNum,
                                                                 @RequestParam("pageSize") Integer pageSize,
                                                                 @RequestParam("type") Integer type) {
        Page page = (Page) pmsProductAttributeService.list(cid, pageNum, pageSize, type);

        return CommonResult.success(CommonPage.restPage(page));
    }


    @ApiOperation("添加商品类型-属性列表")
    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody PmsProductAttribute pmsProductAttribute) {
        boolean result = pmsProductAttributeService.create(pmsProductAttribute);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * url:'/productAttribute/'+id,
     * method:'get'
     */
    @ApiOperation("初始更化新商品类型-属性列表")
    @GetMapping(value = "/{id}")
    public CommonResult<PmsProductAttribute> getProductCategory(@PathVariable("id") Long id) {

        PmsProductAttribute result = pmsProductAttributeService.getById(id);
        return CommonResult.success(result);
    }

    /**
     * url:'/productAttribute/update/'+id,
     * method:'post',
     * data:data
     */
    @ApiOperation("更新化商品类型-属性列表")
    @PostMapping(value = "/update/{id}")
    public CommonResult updateProductCategory(@RequestBody PmsProductAttribute pmsProductAttribute, @PathVariable("id") Long id) {
        boolean result = pmsProductAttributeService.updateById(pmsProductAttribute);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * url:'/productAttribute/delete',
     * method:'post',
     * data:data
     */
    @ApiOperation("删除化商品类型-属性列表")
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean result = pmsProductAttributeService.delete(ids);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * url:'/productAttribute/attrInfo/'+productCategoryId,
     * method:'get'
     */
    @ApiOperation("根据商品分类id,获取关联的筛选属性")
    @GetMapping("/attrInfo/{productCategoryId}")
    public CommonResult<List<RelationAttrInfoDto>> getAttrInfoCategoryById(@PathVariable("productCategoryId") Long productCategoryId) {
        List<RelationAttrInfoDto> list = pmsProductAttributeService.getAttrInfoCategoryById(productCategoryId);
        return CommonResult.success(list);
    }
}

