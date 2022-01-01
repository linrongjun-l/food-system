package cn.ecut.lrj.modules.pms.dto;


import cn.ecut.lrj.modules.pms.model.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductParamsDto数据保存传输对象", description = "用于商品的添加、修改")
public class ProductParamsDto extends PmsProduct {

    //会员价格{memberLevelId: 0,memberPrice: 0,memberLevelName: null}
    private List<PmsMemberPrice> memberPriceList;

    //商品满减
    private List<PmsProductFullReduction> productFullReductionList;

    //商品阶梯价格
    private List<PmsProductLadder> productLadderList;

    //商品属性相关{productAttributeId: 0, value: ''}
    private List<PmsProductAttributeValue> productAttributeValueList;

    //商品sku库存信息{lowStock: 0, pic: '', price: 0, sale: 0, skuCode: '', spData: '', stock: 0}
    @Size(min = 1, message = "SKU至少一项")
    @Valid
    private List<PmsSkuStock> skuStockList;
}
