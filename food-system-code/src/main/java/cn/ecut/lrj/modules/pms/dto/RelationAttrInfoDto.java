package cn.ecut.lrj.modules.pms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品分类中获取类型id,属性id对象", description = "用于筛选属性以保存的初始化")
public class RelationAttrInfoDto {
    //商品类型id
    private Long attributeCategoryId;

    //商品属性id
    private Long attributeId;
}
