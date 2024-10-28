package com.modern.domain;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspVehicleStdModelExtra
 * @Date：2024/10/25 10:27
 * @Filename：TspVehicleStdModelExtra
 */
@Table(name = "tsp_vehicle_std_model_extra", comment = "摩登- TSP - 车辆型号扩展信息")
@Alias("TspVehicleStdModelExtra")
@TableName(value = "tsp_vehicle_std_model_extra", autoResultMap = true)
@Data
public class TspVehicleStdModelExtra extends BaseModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆车型ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleStdModelId;

    @Column(comment = "设备ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspEquipmentId;

    @Column(comment = "续航里程", type = MySqlTypeConstant.DOUBLE)
    private Double extensionMileage;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "慢充时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime slowChargeTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "快充时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime fastChargeTime;

    @Column(comment = "快充百分比(%)", type = MySqlTypeConstant.INT)
    private Integer fastPercentage;

    @Column(comment = "最高车速", type = MySqlTypeConstant.BIGINT)
    private Integer maxSpeed;

    @Column(comment = "驱动电机布置式/位置", type = MySqlTypeConstant.VARCHAR)
    private String drivingMotorType;

    @Column(comment = "电池容量", type = MySqlTypeConstant.BIGINT)
    private Integer batteryCapacity;

    @Column(comment = "电池类型", type = MySqlTypeConstant.TINYINT)
    private Integer batteryType;

    @Column(comment = "电池包个数", type = MySqlTypeConstant.BIGINT)
    private Integer batteryCount;

    @Column(comment = "电池组质保", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String batteryPackWarranty;

    @Column(comment = "里程检测方式", type = MySqlTypeConstant.VARCHAR)
    private String mileageDetectio;

    @Column(comment = "电池包串并联方式", type = MySqlTypeConstant.VARCHAR)
    private String batterySeriesParallel;

    @Column(comment = "驱动电机数", type = MySqlTypeConstant.BIGINT)
    private Integer drivingMotorNumber;

    @Column(comment = "电机类型", type = MySqlTypeConstant.VARCHAR)
    private String motorType;

    @Column(comment = "电机总功率", type = MySqlTypeConstant.BIGINT)
    private Integer totalPower;

    @Column(comment = "电机总扭矩", type = MySqlTypeConstant.BIGINT)
    private Integer totalTorqueMotor;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "综合油耗(L/100km)", type = MySqlTypeConstant.BIGINT)
    private Integer oilWear;

    @Column(comment = "环保标准", type = MySqlTypeConstant.VARCHAR)
    private String environmentalProtection;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "发动机型号", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String engineType;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "发动机排量(mL)", type = MySqlTypeConstant.BIGINT)
    private Integer displacement;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "气缸数", type = MySqlTypeConstant.BIGINT)
    private Integer cylinderNumber;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "最大功率(kw)", type = MySqlTypeConstant.BIGINT)
    private Integer maximumPower;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "最大扭矩", type = MySqlTypeConstant.BIGINT)
    private Integer maximumTorque;

    @Column(comment = "变速箱", type = MySqlTypeConstant.VARCHAR, length = 22)
    private String transmissionCase;

    @Column(comment = "定额电压", type = MySqlTypeConstant.VARCHAR)
    private String ratedVoltage;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "驱动方式", type = MySqlTypeConstant.VARCHAR)
    private String drivingMode;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "整车质保", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String vehicleWarranty;

    @Column(comment = "车身尺寸(长*宽*高)", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dimensions;

    @Column(comment = "数据来源", type = MySqlTypeConstant.BIGINT)
    private Integer dataSource;
}
