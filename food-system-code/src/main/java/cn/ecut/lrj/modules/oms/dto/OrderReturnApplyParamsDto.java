package cn.ecut.lrj.modules.oms.dto;



import cn.ecut.lrj.modules.oms.model.OmsOrderReturnApply;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrderReturnApplyParamsDto退货申请初始化传输对象", description = "退货申请初始化")
public class OrderReturnApplyParamsDto extends OmsOrderReturnApply {

    private Integer pageNum;

    private Integer pageSize;

    @ApiModelProperty(value = "退货人姓名 或 退货人电话")
    private String receiverKeyword;

    /*@ApiModelProperty(value = "(修改收货人id)订单id")
    private Long orderId;

    @ApiModelProperty(value = "（关闭订单）订单ids")
    private List<Long> ids;*/
}



