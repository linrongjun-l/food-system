package cn.ecut.lrj.modules.pms.dto;


import cn.ecut.lrj.modules.pms.model.PmsProductCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品分类的数据传输对象", description = "用于商品分类添加、修改")
public class ProductCategoryDto extends PmsProductCategory {

    private List<Long> productAttributeIdList;
}
