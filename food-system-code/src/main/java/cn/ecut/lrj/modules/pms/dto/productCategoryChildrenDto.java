package cn.ecut.lrj.modules.pms.dto;


import cn.ecut.lrj.modules.pms.model.PmsProductCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "productCategoryChildrenDto商品一级分类和二级分类 的级联传输对象", description = "商品分类下拉级联初始化-DTO")
public class productCategoryChildrenDto {
    private Long id;

    private String name;

    private List<PmsProductCategory> children;
}
