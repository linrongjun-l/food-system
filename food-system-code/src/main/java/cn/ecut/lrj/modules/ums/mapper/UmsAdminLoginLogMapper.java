package cn.ecut.lrj.modules.ums.mapper;

import cn.ecut.lrj.modules.ums.dto.UmsAdminLogDto;
import cn.ecut.lrj.modules.ums.model.UmsAdminLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


/**
 * <p>
 * 后台用户登录日志表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2020-08-21
 */
public interface UmsAdminLoginLogMapper extends BaseMapper<UmsAdminLoginLog> {

    Page<UmsAdminLogDto> selectListByUserName(Page<UmsAdminLogDto> page, String userName);
}
