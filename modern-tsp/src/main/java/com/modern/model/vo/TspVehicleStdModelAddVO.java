package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspVehicleStdModelAddVO
 * @Date：2024/10/24 17:23
 * @Filename：TspVehicleStdModelAddVO
 */
@ApiModel("车辆信息- 请求对象- 二级车型型号")
@Data
public class TspVehicleStdModelAddVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("型号ID")
    private Long tspVehicleStdModelId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleModelId;

    @NotEmpty(message = "型号名称不能为空")
    @ApiModelProperty("型号名称")
    private String stdModeName;

    @ApiModelProperty("能源类型")
    private Integer dataKey;

    @NotEmpty(message = "公告批次不能为空")
    @ApiModelProperty("公告批次")
    private String noticeBatch;

    @NotEmpty(message = "公告型号不能为空")
    @ApiModelProperty("公告型号")
    private String noticeModel;

    @ApiModelProperty("车辆厂商")
    private String firm;

    @Valid
    @ApiModelProperty("扩展信息")
    private TspVehicleStdModelExtraAddVO stdModelExtraAddVO;
}
