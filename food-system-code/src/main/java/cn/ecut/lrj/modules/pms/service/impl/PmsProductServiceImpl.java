package cn.ecut.lrj.modules.pms.service.impl;

import cn.ecut.lrj.modules.pms.dto.ProductConditionDto;
import cn.ecut.lrj.modules.pms.dto.ProductParamsDto;
import cn.ecut.lrj.modules.pms.model.PmsProduct;
import cn.ecut.lrj.modules.pms.mapper.PmsProductMapper;
import cn.ecut.lrj.modules.pms.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author LinRJ
 * @since 2021-12-21
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Autowired
    private PmsMemberPriceService pmsMemberPriceService;

    @Autowired
    private PmsProductFullReductionService pmsProductFullReductionService;

    @Autowired
    private PmsProductLadderService pmsProductLadderService;

    @Autowired
    private PmsSkuStockService pmsSkuStockService;

    @Autowired
    private PmsProductAttributeValueService pmsProductAttributeValueService;


    @Override
    public Page<PmsProduct> list(ProductConditionDto conditionDto) {
        Page<PmsProduct> page = new Page<>(conditionDto.getPageNum(), conditionDto.getPageSize());


        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PmsProduct> lambda = queryWrapper.lambda();

        //商品名称
        if (!StrUtil.isBlank(conditionDto.getKeyword())) {
            lambda.like(PmsProduct::getName, conditionDto.getKeyword());
        }

        //商品货号
        if (!StrUtil.isBlank(conditionDto.getProductSn())) {
            lambda.eq(PmsProduct::getProductSn, conditionDto.getProductSn());
        }
        //商品分类
        if (conditionDto.getProductCategoryId() != null && conditionDto.getProductCategoryId() > 0) {
            lambda.eq(PmsProduct::getProductCategoryId, conditionDto.getProductCategoryId());
        }
        //品牌分类
        if (conditionDto.getBrandId() != null && conditionDto.getBrandId() > 0) {
            lambda.eq(PmsProduct::getBrandId, conditionDto.getBrandId());
        }
        //上架状态
        if (conditionDto.getPublishStatus() != null && (conditionDto.getPublishStatus() == 0 || conditionDto.getPublishStatus() == 1)) {
            lambda.eq(PmsProduct::getPublishStatus, conditionDto.getPublishStatus());
        }
        //审核状态
        if (conditionDto.getVerifyStatus() != null && (conditionDto.getVerifyStatus() == 0 || conditionDto.getVerifyStatus() == 1)) {
            lambda.eq(PmsProduct::getVerifyStatus, conditionDto.getVerifyStatus());
        }
        lambda.orderByAsc(PmsProduct::getSort);
        return this.page(page, queryWrapper);
    }

    @Override
    public Boolean updateStatus(List<Long> ids, Integer status, SFunction<PmsProduct, ?> getPublicStatus) {

        UpdateWrapper<PmsProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(getPublicStatus, status)
                .in(PmsProduct::getId, ids);

        return this.update(updateWrapper);
    }

    @Override
    @Transactional
    public Boolean create(ProductParamsDto productParamsDto) {
        //1.添加商品主表
        PmsProduct pmsProduct = productParamsDto;
        pmsProduct.setId(null);
        boolean save = this.save(pmsProduct);
        if (save) {
            /**
             * 促销类型：0->没有促销使用原价;1->使用促销价；
             * 2->使用会员价；3->使用阶梯价格；4->使用满减价格；
             * 5->限时购"
             */
            switch (productParamsDto.getPromotionType()) {
                case 2:
                    //2.添加会员价格
                    saveManyList(productParamsDto.getMemberPriceList(), pmsProduct.getId(), pmsMemberPriceService);
                    break;
                case 3:
                    //3.添加商品阶梯价格
                    saveManyList(productParamsDto.getProductLadderList(), pmsProduct.getId(), pmsProductLadderService);
                    break;
                case 4:
                    //4.添加满减价格
                    saveManyList(productParamsDto.getProductFullReductionList(), pmsProduct.getId(), pmsProductFullReductionService);
                    break;
            }

            //5.添加sku
            saveManyList(productParamsDto.getSkuStockList(), pmsProduct.getId(), pmsSkuStockService);

            //6.添加spu
            saveManyList(productParamsDto.getProductAttributeValueList(), pmsProduct.getId(), pmsProductAttributeValueService);

        }
        return save;
    }

    /**
     * 公共方法： 添加会员价格、商品阶梯价格、满减价格 sku、spu 的关联数据
     * 统一： 都需要设置商品id 都需要批量保存
     *
     * @param list
     * @param productId 商品id
     * @param service
     */
    private void saveManyList(List<?> list, Long productId, IService service) {
        if (CollectionUtil.isEmpty(list)) return;

        try {
            //循环 反射 赋值商品id
            for (Object obj : list) {
                Method setProductIdMethod = obj.getClass().getMethod("setProductId", Long.class);

                //修改时调用（把id设置为null）
                Method setIdMethod = obj.getClass().getMethod("setId", Long.class);
                setIdMethod.invoke(obj, (Long) null);

                //调用setProductId
                setProductIdMethod.invoke(obj, productId);
            }
            //批量添加
            service.saveBatch(list);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void deleteManyList(Long productId, IService service) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", productId);
        service.remove(queryWrapper);
    }
}
