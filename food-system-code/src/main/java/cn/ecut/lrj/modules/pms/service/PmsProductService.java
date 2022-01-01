package cn.ecut.lrj.modules.pms.service;

import cn.ecut.lrj.modules.pms.dto.ProductConditionDto;
import cn.ecut.lrj.modules.pms.dto.ProductParamsDto;
import cn.ecut.lrj.modules.pms.model.PmsProduct;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
public interface PmsProductService extends IService<PmsProduct> {
    /**
     *
     * @param conditionDto
     * @return
     */
    Page<PmsProduct> list(ProductConditionDto conditionDto);

    /**
     *新品，上架，推荐公共方法
     * @param ids
     * @param status
     * @param getPublicStatus
     * @return
     */
    Boolean updateStatus(List<Long> ids, Integer status, SFunction<PmsProduct, ?> getPublicStatus);

    /**
     * 新增商品
     * @param productParamsDto
     * @return
     */
    Boolean create(ProductParamsDto productParamsDto);
}
