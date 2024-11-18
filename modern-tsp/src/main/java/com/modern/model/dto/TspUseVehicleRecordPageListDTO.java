package com.modern.model.dto;

import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspUseVehicleRecordPageListDTO
 * @Date：2024/11/18 13:35
 * @Filename：TspUseVehicleRecordPageListDTO
 */
@ApiModel("用户绑定记录- 数据传输对象- 分页列表")
@Data
public class TspUseVehicleRecordPageListDTO extends BaseDto {
    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("备注")
    private String remark;
}
