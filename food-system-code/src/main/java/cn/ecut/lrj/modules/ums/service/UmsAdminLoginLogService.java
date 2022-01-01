package cn.ecut.lrj.modules.ums.service;

import cn.ecut.lrj.modules.ums.dto.UmsAdminLogDto;
import cn.ecut.lrj.modules.ums.model.UmsAdminLoginLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台用户登录日志表 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
public interface UmsAdminLoginLogService extends IService<UmsAdminLoginLog> {

    Page<UmsAdminLogDto> list(Integer pageNum, Integer pageSize, String userName);
}
