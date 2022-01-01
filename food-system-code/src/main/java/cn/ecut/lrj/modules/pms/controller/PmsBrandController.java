package cn.ecut.lrj.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.pms.model.PmsBrand;
import cn.ecut.lrj.modules.pms.service.PmsBrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author XuShu
 * @since 2021-07-18
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService pmsBrandService;

    /**
     * 品牌数据列表
     * 在商品列表中公用
     * url:'/brand/list',
     * method:'get',
     */
    @ApiOperation("品牌数据列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsBrand>> getList(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page page = pmsBrandService.list1(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 品牌是否显示
     * url:'/brand/update/showStatus',
     * method:'post',
     */
    @ApiOperation("品牌是否显示")
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids,
                                         @RequestParam("showStatus") Integer showStatus) {
        Boolean result = pmsBrandService.updateStatus(ids, showStatus, PmsBrand::getShowStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 品牌制造商
     * url:'/brand/update/factoryStatus',
     * method:'post',
     */
    @ApiOperation("商品新品")
    @PostMapping("/update/factoryStatus")
    public CommonResult updateaFctoryStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("factoryStatus") Integer factoryStatus) {
        Boolean result = pmsBrandService.updateStatus(ids, factoryStatus, PmsBrand::getFactoryStatus);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 添加品牌
     * url:'/brand/create',
     * method:'post',
     * data:data
     */
    @ApiOperation("添加品牌")
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsBrand pmsBrand) {

        Boolean result = pmsBrandService.create(pmsBrand);

        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除品牌
     * url:'/brand/delete/'+id,
     * method:'get',
     */
    @ApiOperation("删除品牌")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        boolean result = pmsBrandService.removeById(id);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 编辑品牌数据初始化
     * url:'/brand/'+id,
     * method:'get',
     */
    @ApiOperation("编辑品牌数据初始化")
    @GetMapping("/{id}")
    public CommonResult getBrand(@PathVariable("id") Long id) {
        PmsBrand pmsBrand = pmsBrandService.getById(id);
        return CommonResult.success(pmsBrand);
    }

    /**
     * 编辑品牌
     * url:'/brand/update/'+id,
     * method:'post',
     * data:data
     */
    @ApiOperation("编辑品牌")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable("id") Long id,
                               @RequestBody PmsBrand pmsBrand) {
        boolean result = pmsBrandService.update(id, pmsBrand);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}

