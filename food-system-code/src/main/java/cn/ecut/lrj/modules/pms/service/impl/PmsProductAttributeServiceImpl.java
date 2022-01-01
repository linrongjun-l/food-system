package cn.ecut.lrj.modules.pms.service.impl;

import cn.ecut.lrj.modules.pms.dto.RelationAttrInfoDto;
import cn.ecut.lrj.modules.pms.mapper.PmsProductCategoryAttributeRelationMapper;
import cn.ecut.lrj.modules.pms.model.PmsProductAttribute;
import cn.ecut.lrj.modules.pms.mapper.PmsProductAttributeMapper;
import cn.ecut.lrj.modules.pms.model.PmsProductAttributeCategory;
import cn.ecut.lrj.modules.pms.service.PmsProductAttributeCategoryService;
import cn.ecut.lrj.modules.pms.service.PmsProductAttributeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {

    @Autowired
    private PmsProductAttributeMapper pmsProductAttributeMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationMapper relationMapper;

    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;


    @Override
    public Page list(Long cid, Integer pageNum, Integer pageSize, Integer type) {

        Page<PmsProductAttribute> page = new Page<>(pageNum, pageSize);

        QueryWrapper<PmsProductAttribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(PmsProductAttribute::getProductAttributeCategoryId, cid)
                .eq(PmsProductAttribute::getType, type)
                .orderByAsc(PmsProductAttribute::getSort);

        return this.page(page, queryWrapper);
    }


    @Override
    @Transactional
    public boolean create(PmsProductAttribute pmsProductAttribute) {
        boolean save = this.save(pmsProductAttribute);
        if (save) {
            //更新对应属性、参数的数量
            UpdateWrapper<PmsProductAttributeCategory> updateWrapper = new UpdateWrapper<>();
            if (pmsProductAttribute.getType() == 0) {
                //修改属性
                updateWrapper.setSql("attribute_count=attribute_count+1");
            } else if (pmsProductAttribute.getType() == 1) {
                //修改参数
                updateWrapper.setSql("param_count=param_count+1");
            }
            updateWrapper.lambda().eq(PmsProductAttributeCategory::getId, pmsProductAttribute.getProductAttributeCategoryId());
            pmsProductAttributeCategoryService.update(updateWrapper);
        }
        return save;
    }

    /**
     * 删除化商品类型-属性列表
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public boolean delete(List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        //得到当前属性的类别
        PmsProductAttribute pmsProductAttribute = null;
        for (Long id : ids) {
            pmsProductAttribute = this.getById(id);
            if (pmsProductAttribute != null) {
                break;
            }
        }

        int leng = pmsProductAttributeMapper.deleteBatchIds(ids);

        if (leng > 0 && pmsProductAttribute != null) {
            UpdateWrapper<PmsProductAttributeCategory> updateWrapper = new UpdateWrapper<>();
            if (pmsProductAttribute.getType() == 0) {
                //修改属性
                updateWrapper.setSql("attribute_count=attribute_count-" + leng);
            } else if (pmsProductAttribute.getType() == 1) {
                //修改参数
                updateWrapper.setSql("param_count=param_count-" + leng);
            }
            updateWrapper.lambda().eq(PmsProductAttributeCategory::getId, pmsProductAttribute.getProductAttributeCategoryId());
            pmsProductAttributeCategoryService.update(updateWrapper);
        }


        return leng > 0;
    }

    /**
     * @param productCategoryId
     * @return
     */
    @Override
    public List<RelationAttrInfoDto> getAttrInfoCategoryById(Long productCategoryId) {

        return relationMapper.getAttrInfoCategoryById(productCategoryId);
    }
}
