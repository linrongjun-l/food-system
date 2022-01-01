package cn.ecut.lrj.modules.pms.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductUpdateInitDto数据修改初始化传输对象", description = "用于商品的修改，初始化数据")
public class ProductUpdateInitDto extends ProductParamsDto {

    private Long cateParentId;
}
