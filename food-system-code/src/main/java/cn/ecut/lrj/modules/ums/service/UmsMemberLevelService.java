package cn.ecut.lrj.modules.ums.service;

import cn.ecut.lrj.modules.ums.model.UmsMemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * <p>
 * 会员等级表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2021-03-09
 */
public interface UmsMemberLevelService extends IService<UmsMemberLevel> {

    List<UmsMemberLevel> list(Integer defaultStatus);
}
