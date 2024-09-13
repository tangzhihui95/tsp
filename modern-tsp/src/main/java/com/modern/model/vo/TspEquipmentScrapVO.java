package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspEquipmentScrapVO
 * @Date：2024/9/13 14:55
 * @Filename：TspEquipmentScrapVO
 */

@ApiModel("设备信息-请求对象 -报废")
@Data
public class TspEquipmentScrapVO {

    @NotEmpty(message = "登录密码不能为空")
    @ApiModelProperty("登录密码")
    private String password;

    @NotNull(message = "报废设备不能为空")
    @ApiModelProperty("报废设备")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long[] tspEquipmentIds;
}
