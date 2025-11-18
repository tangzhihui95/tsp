package com.modern.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspDealerPageListDTO
 * @Date：2025/11/5 10:21
 * @Filename：TspDealerPageListDTO
 */
@ApiModel("经销商管理- 数据传输对象- 分页列表")
@Data
public class TspDealerPageListDTO extends BaseDto {

    @ApiModelProperty("主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspDealerId;

    @ApiModelProperty(value = "经销商名称", required = true, example = "经销商名称")
    private String dealerName;

    @ApiModelProperty(value = "经销商编码", required = true, example = "经销商编码")
    private String dealerCode;

    @ApiModelProperty(value = "经销商电话", required = true, example = "经销商电话")
    private String dealerPhone;

    @ApiModelProperty(value = "经销商地址", required = true, example = "经销商地址")
    private String dealerAddress;
}
