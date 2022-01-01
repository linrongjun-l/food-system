package cn.ecut.lrj.modules.oms.controller;


import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.oms.model.OmsCompanyAddress;
import cn.ecut.lrj.modules.oms.service.OmsCompanyAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 公司收发货地址表 前端控制器
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@RestController
@Api(tags = "OmsCompanyAddressController", description = "公司收货地址")
@RequestMapping("/companyAddress")
public class OmsCompanyAddressController {

    @Autowired
    private OmsCompanyAddressService omsCompanyAddressService;

    /**
     * url:'/companyAddress/list',
     * method:'get'
     */
    @ApiOperation(value = "公司收货地址")
    @GetMapping("/list")
    public CommonResult list() {
        List<OmsCompanyAddress> list = omsCompanyAddressService.list();
        return CommonResult.success(list);
    }
}

