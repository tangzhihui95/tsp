package com.modern.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.BaseModel;
import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspDealerInfoDTO
 * @Date：2025/11/5 17:04
 * @Filename：TspDealerInfoDTO
 */
@ApiModel("经销商管理- 数据传输对象- 详情")
@Data
public class TspDealerInfoDTO extends BaseDto {

    @ApiModelProperty(value = "经销商名称", required = true, example = "经销商名称")
    private String dealerName;

    @ApiModelProperty(value = "经销商编码", required = true, example = "经销商编码")
    private String dealerCode;

    @ApiModelProperty(value = "经销商电话", required = true, example = "经销商电话")
    private String dealerPhone;


    @ApiModelProperty(value = "经销商所在省", required = true, example = "经销商所在省")
    private String dealerProvince;

    @ApiModelProperty(value = "经销商所在市", required = true, example = "经销商所在市")
    private String dealerCity;

    @ApiModelProperty(value = "经销商所在区", required = true, example = "经销商所在区")
    private String dealerDistrict;

    @ApiModelProperty(value = "经销商所在街道地址", required = true, example = "经销商所在街道地址")
    private String dealerStreetAndStreetNumber;

    @ApiModelProperty(value = "经销商地址", required = true, example = "经销商地址")
    private String dealerAddress;

    @ApiModelProperty(value = "经销商经度", required = true, example = "经销商经度")
    private Map<String, String> dealerLngLat;
}
