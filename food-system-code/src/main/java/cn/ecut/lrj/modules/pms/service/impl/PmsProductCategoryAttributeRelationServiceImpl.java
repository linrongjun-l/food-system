package cn.ecut.lrj.modules.pms.service.impl;

import cn.ecut.lrj.modules.pms.model.PmsProductCategoryAttributeRelation;
import cn.ecut.lrj.modules.pms.mapper.PmsProductCategoryAttributeRelationMapper;
import cn.ecut.lrj.modules.pms.service.PmsProductCategoryAttributeRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类） 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@Service
public class PmsProductCategoryAttributeRelationServiceImpl extends ServiceImpl<PmsProductCategoryAttributeRelationMapper, PmsProductCategoryAttributeRelation> implements PmsProductCategoryAttributeRelationService {

}
