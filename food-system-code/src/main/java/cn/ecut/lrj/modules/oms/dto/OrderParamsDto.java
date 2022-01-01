package cn.ecut.lrj.modules.oms.dto;


import cn.ecut.lrj.modules.oms.model.OmsOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrderParamsDto订单表初始化传输对象", description = "订单列表初始化")
public class OrderParamsDto extends OmsOrder {

    private Integer pageNum;
    private Integer pageSize;

    @ApiModelProperty(value = "收货人姓名 或 收货人电话")
    private String receiverKeyword;

    @ApiModelProperty(value = "(修改收货人id)订单id")
    private Long orderId;

    @ApiModelProperty(value = "（关闭订单）订单ids")
    private List<Long> ids;
}



