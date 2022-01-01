package cn.ecut.lrj.modules.pms.service;

import cn.ecut.lrj.modules.pms.model.PmsBrand;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author XuShu
 * @since 2021-07-18
 */
public interface PmsBrandService extends IService<PmsBrand> {

    Page list1(String keyword, Integer pageNum, Integer pageSize);

    Boolean updateStatus(List<Long> ids, Integer showStatus, SFunction<PmsBrand, ?> getPublicStatus);

    Boolean create(PmsBrand pmsBrand);

    boolean update(Long id, PmsBrand pmsBrand);
}
