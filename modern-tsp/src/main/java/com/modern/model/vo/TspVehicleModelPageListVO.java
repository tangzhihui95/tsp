package com.modern.model.vo;

import com.modern.common.core.domain.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspVehicleModelPageListVO
 * @Date：2024/10/14 15:04
 * @Filename：TspVehicleModelPageListVO
 */
@ApiModel("车辆车型- 请求对象- 分页列表")
@Data
public class TspVehicleModelPageListVO extends BaseVO {

    @ApiModelProperty("车型")
    private String vehicleModelName;

    @ApiModelProperty("型号")
    private Long tspVehicleStdModelId;

    @ApiModelProperty("需要导出的id集合")
    private List<Long> ids;
}
