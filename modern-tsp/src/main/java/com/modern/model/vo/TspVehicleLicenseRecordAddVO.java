package com.modern.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspVehicleLicenseRecordAddVO
 * @Date：2024/10/18 13:25
 * @Filename：TspVehicleLicenseRecordAddVO
 */
@ApiModel("车辆上牌记录- 请求对象- 添加")
@Data
public class TspVehicleLicenseRecordAddVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("车辆ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleId;

    @ApiModelProperty("上牌车管所名称")
    private String awardPlaceName;

    @ApiModelProperty("省份")
    private String awardProvince;

    @ApiModelProperty("城市")
    private String awardCity;

    @ApiModelProperty("区域")
    private String awardArea;

    @ApiModelProperty("上牌详细地址")
    private String awardPlaceAddress;

    @ApiModelProperty("上牌地区 如:京")
    private String plateCodeName;

    @ApiModelProperty("车牌号")
    private String plateCode;

    @ApiModelProperty("车牌颜色")
    private String plateColour;

    @TableField(value = "plate_img_urls", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("车牌照片")
    private List<String> plateImgUrls;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("当前上牌日期")
    private LocalDate currentUpPlaceDate;
}
