package cn.ecut.lrj.modules.pms.service.impl;

import cn.ecut.lrj.modules.pms.dto.ProductCategoryDto;
import cn.ecut.lrj.modules.pms.dto.productCategoryChildrenDto;
import cn.ecut.lrj.modules.pms.model.PmsProduct;
import cn.ecut.lrj.modules.pms.model.PmsProductCategory;
import cn.ecut.lrj.modules.pms.mapper.PmsProductCategoryMapper;
import cn.ecut.lrj.modules.pms.model.PmsProductCategoryAttributeRelation;
import cn.ecut.lrj.modules.pms.service.PmsProductCategoryAttributeRelationService;
import cn.ecut.lrj.modules.pms.service.PmsProductCategoryService;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationService relationService;

    @Override
    public Page list(Long parentId, Integer pageNum, Integer pageSize) {

        Page page = new Page(pageNum,pageSize);

        QueryWrapper<PmsProductCategory> wrapper = new QueryWrapper();
        wrapper.lambda()
                .eq(PmsProductCategory::getParentId, parentId)
                .orderByAsc(PmsProductCategory::getSort);

        return this.page(page,wrapper);
    }

    @Override
    public Boolean updateStatus(List<Long> ids, Integer status, SFunction<PmsProductCategory, ?> getPublicStatus) {
        UpdateWrapper<PmsProductCategory> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda()
                .set(getPublicStatus,status)
                .in(PmsProductCategory::getId,ids);

        return this.update(updateWrapper);
    }

    @Override
    public boolean delete(Long id) {

        return this.removeById(id);
    }

    @Override
    @Transactional
    public boolean create(ProductCategoryDto productCategoryDto) {
        boolean isSaveI, isSaveII;
        //保存商品分类
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        //通过BeanUtils，将productCategoryDto中数据拷贝到pmsProductCategory中
        BeanUtils.copyProperties(productCategoryDto, pmsProductCategory);
        pmsProductCategory.setProductCount(0);
        if (pmsProductCategory.getParentId() == 0) {
            pmsProductCategory.setLevel(0);
        } else {
            pmsProductCategory.setLevel(1);
        }

        isSaveI = this.save(pmsProductCategory);

        //添加商品筛选属性
        isSaveII = saveAttrRelation(productCategoryDto, pmsProductCategory);

        return true;
    }

    /**
     * 添加筛选属性
     *
     * @param productCategoryDto
     * @param pmsProductCategory
     * @return
     */
    private boolean saveAttrRelation(ProductCategoryDto productCategoryDto, PmsProductCategory pmsProductCategory) {
        //商品筛选属性
        List<Long> productAttributeIdList = productCategoryDto.getProductAttributeIdList();
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        for (Long attrId : productAttributeIdList) {
            //得到分类保存后的主键id, 保存商品分类筛选属性的关系
            PmsProductCategoryAttributeRelation pmsProductCategoryAttributeRelation = new PmsProductCategoryAttributeRelation();
            pmsProductCategoryAttributeRelation.setProductAttributeId(attrId);
            pmsProductCategoryAttributeRelation.setProductCategoryId(pmsProductCategory.getId());
            relationList.add(pmsProductCategoryAttributeRelation);
        }

        return relationService.saveBatch(relationList);

    }

    @Override
    @Transactional
    public boolean update(ProductCategoryDto productCategoryDto) {
        boolean isSaveI, isSaveII;
        //保存商品分类
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        //通过BeanUtils，将productCategoryDto中数据拷贝到pmsProductCategory中
        BeanUtils.copyProperties(productCategoryDto, pmsProductCategory);
        if (pmsProductCategory.getParentId() == 0) {
            pmsProductCategory.setLevel(0);
        } else {
            pmsProductCategory.setLevel(1);
        }

        isSaveI = this.updateById(pmsProductCategory);

        //删除已经保存的关联属性----根据商品分类id删除
        QueryWrapper<PmsProductCategoryAttributeRelation> relationWrapper = new QueryWrapper<>();
        relationWrapper.lambda().eq(PmsProductCategoryAttributeRelation::getProductCategoryId, pmsProductCategory.getId());
        relationService.remove(relationWrapper);

        //添加商品筛选属性
        saveAttrRelation(productCategoryDto, pmsProductCategory);

        return true;
    }

    @Override
    public List<productCategoryChildrenDto> listWithChildren() {

        return pmsProductCategoryMapper.listWithChildren();
    }
}
