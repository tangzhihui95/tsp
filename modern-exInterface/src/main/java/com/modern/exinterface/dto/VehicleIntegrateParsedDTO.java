package com.modern.exinterface.dto;

import com.modern.common.annotation.Excel;
import com.modern.entity.VehicleIntegrate;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @Author：tzh
 * @Package：com.modern.dto
 * @Project：tsp
 * @name：VehicleIntegrateParsedDTO
 * @Date：2025/6/12 16:26
 * @Filename：VehicleIntegrateParsedDTO
 */
@ApiModel("(整车数据)-数据传输对象 - 分页查询")
@Data
public class VehicleIntegrateParsedDTO extends BaseVehicleDataDTO {

    @Excel(name = "车辆状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 4)
    @ApiModelProperty("车辆状态")

    private String vehicleState;

    @Excel(name = "充电状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 5)
    @ApiModelProperty("充电状态")

    private Object chargeState;

    @Excel(name = "运行模式", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 6)
    @ApiModelProperty("运行模式")

    private String runMode;

    @Excel(name = "车速", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 7)
    @ApiModelProperty("车速")

    private String speed;

    @Excel(name = "里程", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 8)
    @ApiModelProperty("里程")

    private String mileage;

    @Excel(name = "总电压", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 9)
    @ApiModelProperty("总电压")

    private String totalVoltage;

    @Excel(name = "总电流", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 10)
    @ApiModelProperty("总电流")

    private String totalCurrent;

    @Excel(name = "剩余电量", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 11)
    @ApiModelProperty("剩余电量")

    private Integer soc;

    @Excel(name = "DC-DC状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 12)
    @ApiModelProperty("DC-DC状态")

    private Object dcDcState;

    @Excel(name = "档位", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 13)
    @ApiModelProperty("档位")

    private Object gear;

    @Excel(name = "制动力", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 14)
    @ApiModelProperty("制动力")

    private String gearBrakeForce;

    @Excel(name = "驱动力", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 15)
    @ApiModelProperty("驱动力")

    private String gearDriveForce;

    @Excel(name = "绝缘电阻", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 16)
    @ApiModelProperty("绝缘电阻")

    private Integer insulationResistance;

    @Excel(name = "加速踏板行程值", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 17)
    @ApiModelProperty("加速踏板行程值")

    private Integer acceleratorPedalPosition;

    @Excel(name = "制动踏板状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 18)
    @ApiModelProperty("制动踏板状态")

    private Integer brakePedalPosition;

    public static final Map<Integer, String> VEHICLE_STATE_MAP = ImmutableMap.<Integer, String>builder()
            .put(Integer.valueOf(1), "启动")
            .put(Integer.valueOf(2), "熄火")
            .put(Integer.valueOf(3), "其他").putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    public static final Map<Integer, String> CHARGE_STATE_MAP = ImmutableMap.<Integer, String>builder()
            .put(Integer.valueOf(1), "停车充电")
            .put(Integer.valueOf(2), "行驶充电")
            .put(Integer.valueOf(3), "未充电")
            .put(Integer.valueOf(4), "充电完成")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    public static final Map<Integer, String> RUN_MODE_MAP = ImmutableMap.<Integer, String>builder()
            .put(Integer.valueOf(1), "纯电")
            .put(Integer.valueOf(2), "混动")
            .put(Integer.valueOf(3), "燃油")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    public static final Map<Integer, String> DC_DC_STATE_MAP = ImmutableMap.<Integer, String>builder()
            .put(Integer.valueOf(1), "工作")
            .put(Integer.valueOf(2), "断开")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    public static final Map<Integer, String> GEAR_PART_MAP = ImmutableMap.<Integer, String>builder()
            .put(Integer.valueOf(0), "空挡")
            .put(Integer.valueOf(13), "倒挡")
            .put(Integer.valueOf(14), "D挡").put(Integer.valueOf(15), "P挡")
            .putAll(BYTE_EXCEPTION_INVALID_MAP).build();

    @Nullable
    public static VehicleIntegrateParsedDTO create(VehicleIntegrate unparsedData) {
        try {
            VehicleIntegrateParsedDTO dto = new VehicleIntegrateParsedDTO();
            dto.parseBaseData(unparsedData.getId(), unparsedData.getVin(), unparsedData.getDataType(), unparsedData.getCollectTime(), unparsedData.getCreateTime());
            dto.vehicleState = (VEHICLE_STATE_MAP.get(unparsedData.getVehicleState()) != null) ? VEHICLE_STATE_MAP.get(unparsedData.getVehicleState()) : unparsedData.getVehicleState().toString();
            dto.chargeState = (CHARGE_STATE_MAP.get(unparsedData.getChargeState()) != null) ? CHARGE_STATE_MAP.get(unparsedData.getChargeState()) : unparsedData.getChargeState().toString();
            dto.runMode = (RUN_MODE_MAP.get(unparsedData.getRunMode()) != null) ? RUN_MODE_MAP.get(unparsedData.getRunMode()) : unparsedData.getRunMode().toString();
            dto.speed = df1.format(unparsedData.getSpeed().intValue() / 10.0D);
            dto.mileage = df1.format((unparsedData.getMileage().intValue() / 10));
            dto.totalVoltage = df1.format((unparsedData.getTotalVoltage().intValue() / 10));
            dto.totalCurrent = df1.format(unparsedData.getTotalCurrent().intValue() / 10.0D - 1000.0D);
            dto.soc = unparsedData.getSoc();
            dto.dcDcState = (DC_DC_STATE_MAP.get(unparsedData.getDcDcState()) != null) ? DC_DC_STATE_MAP.get(unparsedData.getDcDcState()) : unparsedData.getDcDcState().toString();
            dto.gear = (GEAR_PART_MAP.get(Integer.valueOf(unparsedData.getGear().intValue() & 0xF)) != null) ? GEAR_PART_MAP.get(Integer.valueOf(unparsedData.getGear().intValue() & 0xF)) : ((unparsedData.getGear().intValue() & 0xF) + "挡")
            ;
            dto.gearBrakeForce = ((unparsedData.getGear().intValue() >>> 4 & 0x1) == 1) ? "有" : "无";
            dto.gearDriveForce = ((unparsedData.getGear().intValue() >>> 5 & 0x1) == 1) ? "有" : "无";
            dto.insulationResistance = unparsedData.getInsulationResistance();
            dto.acceleratorPedalPosition = unparsedData.getAcceleratorPedalPosition();
            dto.brakePedalPosition = unparsedData.getBrakePedalPosition();
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
