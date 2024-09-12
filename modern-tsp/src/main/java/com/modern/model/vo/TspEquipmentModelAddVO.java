package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspEquipmentModelAddVO
 * @Date：2024/9/12 10:51
 * @Filename：TspEquipmentModelAddVO
 */

@ApiModel("设备型号-请求对象 -添加")
@Data
public class TspEquipmentModelAddVO {

    @ApiModelProperty("设备分类ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentTypeId;

    @ApiModelProperty("设备型号ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentModelId;

    @NotEmpty(message = "设备型号不能为空")
    @ApiModelProperty("设备型号")
    private String modelName;

    @NotEmpty(message = "供应商不能为空")
    @ApiModelProperty("供应商")
    private String suppliers;

    @NotEmpty(message = "批次号流水号不能为空")
    @Length(max = 24, message = "批次号流水号不能24位、区分大小写")
    @ApiModelProperty("生产批次")
    private String batchNumber;
}
