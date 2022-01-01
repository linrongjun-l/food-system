package cn.ecut.lrj.modules.pms.service.impl;

import cn.ecut.lrj.modules.pms.dto.ProductAttributeCategoryDto;
import cn.ecut.lrj.modules.pms.model.PmsProductAttributeCategory;
import cn.ecut.lrj.modules.pms.mapper.PmsProductAttributeCategoryMapper;
import cn.ecut.lrj.modules.pms.service.PmsProductAttributeCategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@Service
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory> implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;

    /**
     * 商品类型初始化
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page list(Integer pageNum, Integer pageSize) {

        return this.page(new Page<>(pageNum, pageSize));
    }

    /**
     * @return
     */
    @Override
    public List<ProductAttributeCategoryDto> listWithAttr() {
        List<ProductAttributeCategoryDto> list = productAttributeCategoryMapper.getlistWithAttr();
        return list;
    }
}
