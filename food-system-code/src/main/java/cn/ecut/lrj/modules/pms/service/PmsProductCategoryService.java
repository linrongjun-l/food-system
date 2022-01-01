package cn.ecut.lrj.modules.pms.service;

import cn.ecut.lrj.modules.pms.dto.ProductCategoryDto;
import cn.ecut.lrj.modules.pms.dto.productCategoryChildrenDto;
import cn.ecut.lrj.modules.pms.model.PmsProduct;
import cn.ecut.lrj.modules.pms.model.PmsProductCategory;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
public interface PmsProductCategoryService extends IService<PmsProductCategory> {

    /**
     * 获取商品分类
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page list(Long parentId, Integer pageNum, Integer pageSize);

    /**
     *  修改是否在导航显示
     * @param ids
     * @param status
     * @param getPublicStatus
     * @return
     */
    Boolean updateStatus(List<Long> ids, Integer status, SFunction<PmsProductCategory, ?> getPublicStatus);

    /**
     * 商品分类删除
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 添加商品分类
     * @param productCategoryDto
     * @return
     */
    boolean create(ProductCategoryDto productCategoryDto);

    /**
     * 修改商品分类
     * @param productCategoryDto
     * @return
     */
    boolean update(ProductCategoryDto productCategoryDto);

    /**
     * 商品分类下拉级联初始化
     * @return
     */
    List<productCategoryChildrenDto> listWithChildren();
}
