package com.modern.model.vo;

import com.modern.common.core.domain.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspUseVehicleRecordPageListVO
 * @Date：2024/11/18 13:38
 * @Filename：TspUseVehicleRecordPageListVO
 */

@ApiModel("绑定记录- 请求对象- 分页列表")
@Data
public class TspUseVehicleRecordPageListVO extends BaseVO {
    @ApiModelProperty("用户ID")
    private Long tspUserId;

    @ApiModelProperty("车辆ID")
    private Long tspVehicleId;
}
