package cn.ecut.lrj.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ecut.lrj.modules.pms.mapper.PmsBrandMapper;
import cn.ecut.lrj.modules.pms.model.PmsBrand;
import cn.ecut.lrj.modules.pms.service.PmsBrandService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2021-07-18
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {
    /**
     * 品牌数据列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page list1(String keyword, Integer pageNum, Integer pageSize) {
        Page<PmsBrand> page = new Page<>(pageNum, pageSize);
        QueryWrapper<PmsBrand> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            queryWrapper.lambda().like(PmsBrand::getName, keyword);
        }
        queryWrapper.lambda().orderByAsc(PmsBrand::getSort);
        return this.page(page, queryWrapper);
    }

    /**
     * 修改品牌制造商、是否显示的公共方法
     *
     * @param ids
     * @param showStatus
     * @param getPublicStatus
     * @return
     */
    @Override
    public Boolean updateStatus(List<Long> ids, Integer showStatus, SFunction<PmsBrand, ?> getPublicStatus) {
        UpdateWrapper<PmsBrand> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(getPublicStatus, showStatus)
                .in(PmsBrand::getId, ids);

        return this.update(updateWrapper);
    }

    /**
     * 添加品牌
     *
     * @param pmsBrand
     * @return
     */
    @Override
    public Boolean create(PmsBrand pmsBrand) {

        return this.save(pmsBrand);
    }

    /**
     * 编辑品牌
     *
     * @param id
     * @param pmsBrand
     * @return
     */
    @Override
    public boolean update(Long id, PmsBrand pmsBrand) {
        return this.updateById(pmsBrand);
    }
}
