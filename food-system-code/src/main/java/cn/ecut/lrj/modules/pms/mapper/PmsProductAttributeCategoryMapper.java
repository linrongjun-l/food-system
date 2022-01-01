package cn.ecut.lrj.modules.pms.mapper;

import cn.ecut.lrj.modules.pms.dto.ProductAttributeCategoryDto;
import cn.ecut.lrj.modules.pms.model.PmsProductAttributeCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 Mapper 接口
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
public interface PmsProductAttributeCategoryMapper extends BaseMapper<PmsProductAttributeCategory> {

    List<ProductAttributeCategoryDto> getlistWithAttr();
}
