package cn.ecut.lrj.modules.ums.service.impl;

import cn.ecut.lrj.modules.ums.dto.UmsAdminLogDto;
import cn.ecut.lrj.modules.ums.mapper.UmsAdminLoginLogMapper;
import cn.ecut.lrj.modules.ums.model.UmsAdminLoginLog;
import cn.ecut.lrj.modules.ums.service.UmsAdminLoginLogService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台用户登录日志表 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-22
 */
@Service
public class UmsAdminLoginLogServiceImpl extends ServiceImpl<UmsAdminLoginLogMapper, UmsAdminLoginLog> implements UmsAdminLoginLogService {

    @Resource
    private UmsAdminLoginLogMapper adminLoginLogMapper;

    @Override
    public Page<UmsAdminLogDto> list(Integer pageNum, Integer pageSize, String userName) {

        Page<UmsAdminLogDto> page = new Page(pageNum,pageSize);

        return adminLoginLogMapper.selectListByUserName(page, userName);
    }
}
