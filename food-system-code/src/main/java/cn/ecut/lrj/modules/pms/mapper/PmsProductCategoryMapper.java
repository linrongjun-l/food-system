package cn.ecut.lrj.modules.pms.mapper;

import cn.ecut.lrj.modules.pms.dto.productCategoryChildrenDto;
import cn.ecut.lrj.modules.pms.model.PmsProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    List<productCategoryChildrenDto> listWithChildren();
}
