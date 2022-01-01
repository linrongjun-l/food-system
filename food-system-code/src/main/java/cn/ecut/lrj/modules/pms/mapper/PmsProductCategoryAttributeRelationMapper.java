package cn.ecut.lrj.modules.pms.mapper;

import cn.ecut.lrj.modules.pms.dto.RelationAttrInfoDto;
import cn.ecut.lrj.modules.pms.model.PmsProductCategoryAttributeRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类） Mapper 接口
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
public interface PmsProductCategoryAttributeRelationMapper extends BaseMapper<PmsProductCategoryAttributeRelation> {

    List<RelationAttrInfoDto> getAttrInfoCategoryById(Long productCategoryId);
}
