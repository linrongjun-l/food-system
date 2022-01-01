package cn.ecut.lrj.modules.pms.service;

import cn.ecut.lrj.modules.pms.dto.ProductAttributeCategoryDto;
import cn.ecut.lrj.modules.pms.model.PmsProductAttributeCategory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
public interface PmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {

    /**
     *获取产品属性分类
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page list(Integer pageNum, Integer pageSize);

    /**
     * 初始化筛选属性级联数据
     * @return
     */
    List<ProductAttributeCategoryDto> listWithAttr();

}
