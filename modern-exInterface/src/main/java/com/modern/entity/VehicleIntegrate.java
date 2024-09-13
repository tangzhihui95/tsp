package com.modern.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 整车实体
 *
 * @Author：tzh
 * @Package：com.modern.async
 * @Project：tsp
 * @name：VehicleIntegrate
 * @Date：2024/9/12 17:30
 * @Filename：VehicleIntegrate
 */
@Table(name = "vehicle_integrate")
@Data
@Entity
@Alias("VehicleIntegrate")
public class VehicleIntegrate extends BaseModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "vin")
    private String vin;

    @Column(name = "data_type")
    private Integer dataType;

    @Column(name = "vehicle_state")
    private Integer vehicleState;
    @Column(name = "charge_state")
    private Integer chargeState;
    @Column(name = "run_mode")
    private Integer runMode;
    @Column(name = "speed")
    private Integer speed;
    @Column(name = "mileage")
    private Integer mileage;
    @Column(name = "total_voltage")
    private Integer totalVoltage;
    @Column(name = "total_current")
    private Integer totalCurrent;
    @Column(name = "soc")
    private Integer soc;
    @Column(name = "dc_dc_state")
    private Integer dcDcState;
    @Column(name = "gear")
    private Integer gear;
    @Column(name = "insulation_resistance")
    private Integer insulationResistance;
    @Column(name = "accelerator_pedal_position")
    private Integer acceleratorPedalPosition;
    @Column(name = "brake_pedal_position")
    private Integer brakePedalPosition;

    @Column(name = "collect_time")
    @ApiModelProperty("数据采集时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Integer collectTime;

}
