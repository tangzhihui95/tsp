package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspVehicleScrapVO
 * @Date：2024/10/28 11:28
 * @Filename：TspVehicleScrapVO
 */
@ApiModel("车辆管理-请求对象 -报废 ")
@Data
public class TspVehicleScrapVO extends BaseVO {
    @NotEmpty(message = "登录密码不能为空")
    @ApiModelProperty("登录密码")
    private String password;

    @NotNull(message = "报废车辆不能为空")
    @ApiModelProperty("报废车辆ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long[] tspVehicleIds;
}
