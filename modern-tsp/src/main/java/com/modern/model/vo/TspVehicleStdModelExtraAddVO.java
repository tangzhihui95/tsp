package com.modern.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspVehicleStdModelExtraAddVO
 * @Date：2024/10/24 17:29
 * @Filename：TspVehicleStdModelExtraAddVO
 */
@ApiModel("车辆信息- 请求对象- 添加车型扩展信息")
@Data
public class TspVehicleStdModelExtraAddVO extends BaseVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车辆车型ID")
    private Long tspVehicleStdModelId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备ID")
    private Long tspEquipmentId = Long.valueOf(0L);

    @ApiModelProperty("续航里程")
    private Double extensionMileage;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("慢充时间")
    private LocalDateTime slowChargeTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("快充时间")
    private LocalDateTime fastChargeTime;

    @ApiModelProperty("快充百分比(%)")
    private Integer fastPercentage;

    @ApiModelProperty("最高车速")
    private Integer maxSpeed;

    @ApiModelProperty("驱动电机布置式/位置")
    private String drivingMotorType;

    @ApiModelProperty("电池容量")
    private Integer batteryCapacity;

    @ApiModelProperty("电池类型")
    private Integer batteryType;

    @ApiModelProperty("电池包个数")
    private Integer batteryCount;

    @ApiModelProperty("电池组质保")
    private String batteryPackWarranty;

    @ApiModelProperty("里程检测方式")
    private String mileageDetectio;

    @ApiModelProperty("电池包串并联方式")
    private String batterySeriesParallel;

    @ApiModelProperty("驱动电机数")
    private Integer drivingMotorNumber;

    @ApiModelProperty("电机类型")
    private String motorType;

    @ApiModelProperty("电机总功率")
    private Integer totalPower;

    @ApiModelProperty("电机总扭矩")
    private Integer totalTorqueMotor;

    @ApiModelProperty("综合油耗(L/100km)")
    private Integer oilWear;

    @NotEmpty(message = "环保标准不能为空")
    @ApiModelProperty("环保标准")
    private String environmentalProtection;

    @ApiModelProperty("发动机型号")
    private String engineType;

    @ApiModelProperty("发动机排量(mL)")
    private Integer displacement;

    @ApiModelProperty("气缸数")
    private Integer cylinderNumber;

    @ApiModelProperty("最大功率(kw)")
    private Integer maximumPower;

    @ApiModelProperty("最大扭矩")
    private Integer maximumTorque;

    @ApiModelProperty("变速箱")
    private String transmissionCase;

    @ApiModelProperty("定额电压")
    private String ratedVoltage;

    @ApiModelProperty("驱动方式")
    private String drivingMode;

    @ApiModelProperty("整车质保")
    private String vehicleWarranty;

    @ApiModelProperty("车身尺寸(长*宽*高)")
    private String dimensions;

    @ApiModelProperty("数据来源")
    private Integer dataSource;

    @TableField(value = "extra_images", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("车身图片")
    private List<String> extraImages;
}
