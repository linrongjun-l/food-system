package cn.ecut.lrj.modules.ums.controller.admin;

import cn.ecut.lrj.common.api.CommonPage;
import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.modules.ums.dto.UmsAdminLogDto;
import cn.ecut.lrj.modules.ums.model.UmsAdminLoginLog;
import cn.ecut.lrj.modules.ums.service.UmsAdminLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "UmsAdminLoginLogController" , description = "admin登入日志")
@RequestMapping("/admin/login/log")
public class UmsAdminLoginLogController {

    @Resource
    private UmsAdminLoginLogService adminLoginLogService;

    @GetMapping("/list")
    public CommonResult<CommonPage<UmsAdminLogDto>> list(Integer pageNum, Integer pageSize, String userName){

        Page<UmsAdminLogDto> list = adminLoginLogService.list(pageNum, pageSize, userName);

        return CommonResult.success(CommonPage.restPage(list));
    }
}
