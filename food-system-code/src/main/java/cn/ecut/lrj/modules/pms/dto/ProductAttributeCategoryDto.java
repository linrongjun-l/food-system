package cn.ecut.lrj.modules.pms.dto;


import cn.ecut.lrj.modules.pms.model.PmsProductAttribute;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductAttributeCategoryDto", description = "初始化筛选属性级联数据-DTO")
public class ProductAttributeCategoryDto {
    private Long id;

    private String name;

    private List<PmsProductAttribute> productAttributeList;
}
