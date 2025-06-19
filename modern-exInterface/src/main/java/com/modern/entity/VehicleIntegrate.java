package com.modern.entity;

import com.modern.common.core.domain.BaseExModel;
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
public class VehicleIntegrate extends BaseExModel {
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

    public static final String VEHICLE_STATE = "vehicle_state";

    public static final String CHARGE_STATE = "charge_state";

    public static final String RUN_MODE = "run_mode";

    public static final String SPEED = "speed";

    public static final String MILEAGE = "mileage";

    public static final String TOTAL_VOLTAGE = "total_voltage";

    public static final String TOTAL_CURRENT = "total_current";

    public static final String SOC = "soc";

    public static final String DC_DC_STATE = "dc_dc_state";

    public static final String GEAR = "gear";

    public static final String INSULATION_RESISTANCE = "insulation_resistance";

    public static final String ACCELERATOR_PEDAL_POSITION = "accelerator_pedal_position";

    public static final String BRAKE_PEDAL_POSITION = "brake_pedal_position";

}
