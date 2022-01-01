package cn.ecut.lrj.modules.pms.service;

import cn.ecut.lrj.modules.pms.dto.RelationAttrInfoDto;
import cn.ecut.lrj.modules.pms.model.PmsProductAttribute;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
public interface PmsProductAttributeService extends IService<PmsProductAttribute> {

    /**
     * 初始化商品属性
     * @param cid
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    Page list(Long cid, Integer pageNum, Integer pageSize, Integer type);

    /**
     * 商品属性添加
     * @param pmsProductAttribute
     * @return
     */
    boolean create(PmsProductAttribute pmsProductAttribute);

    /**
     * 商品属性删除
     * @param ids
     * @return
     */
    boolean delete(List<Long> ids);

    /**
     *
     * @param productCategoryId
     * @return
     */
    List<RelationAttrInfoDto> getAttrInfoCategoryById(Long productCategoryId);
}
