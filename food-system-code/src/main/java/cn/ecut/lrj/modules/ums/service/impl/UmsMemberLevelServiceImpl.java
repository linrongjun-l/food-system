package cn.ecut.lrj.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ecut.lrj.modules.ums.mapper.UmsMemberLevelMapper;
import cn.ecut.lrj.modules.ums.model.UmsMemberLevel;
import cn.ecut.lrj.modules.ums.service.UmsMemberLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员等级表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2021-03-09
 */
@Service
public class UmsMemberLevelServiceImpl extends ServiceImpl<UmsMemberLevelMapper, UmsMemberLevel> implements UmsMemberLevelService {

    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {
        QueryWrapper<UmsMemberLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsMemberLevel::getDefaultStatus,defaultStatus);
        return this.list(queryWrapper);
    }
}
