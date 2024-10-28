package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspUseVehicleRecordAddVO
 * @Date：2024/10/22 17:35
 * @Filename：TspUseVehicleRecordAddVO
 */
@ApiModel("用户记录- 请求对象- 添加")
@Data
public class TspUseVehicleRecordAddVO extends BaseVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("用户ID")
    private Long tspUserId;

    @ApiModelProperty("车辆ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleId;

    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty("手机号(账号))")
    private String mobile;

    @NotEmpty(message = "真实姓名不能为空")
    @ApiModelProperty("真实姓名")
    private String realName;

    @NotEmpty(message = "身份证号不能为空")
    @ApiModelProperty("身份证号")
    private String idCard;
}
