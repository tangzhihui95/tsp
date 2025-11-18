package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspDealerAddVO
 * @Date：2025/11/5 14:39
 * @Filename：TspDealerAddVO
 */
@ApiModel("经销商-请求对象 -添加 ")
@Data
public class TspDealerAddVO {
    @ApiModelProperty("经销商主键ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspDealerId;

    @NotNull(message = "经销商名称不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商名称")
    private String dealerName;

    @NotNull(message = "经销商编码不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商编码")
    private String dealerCode;

    @NotNull(message = "经销商电话不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商电话")
    private String dealerPhone;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商所在省")
    private String dealerProvince;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商所在市")
    private String dealerCity;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商所在区")
    private String dealerDistrict;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商所在街道地址")
    private String dealerStreetAndStreetNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商地址")
    private String dealerAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商纬度")
    private String dealerLat;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("经销商经度")
    private String dealerLng;
}
