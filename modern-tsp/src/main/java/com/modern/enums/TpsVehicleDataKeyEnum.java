package com.modern.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

/**
 * @Author：tzh
 * @Package：com.modern.enums
 * @Project：tsp
 * @name：TpsVehicleDataKeyEnum
 * @Date：2024/10/14 15:14
 * @Filename：TpsVehicleDataKeyEnum
 */

@ApiModel("车辆型号- 能源型号")
public enum TpsVehicleDataKeyEnum implements IEnum<Integer> {
    UNDEFINE("未定义", 0),
    HYBRID("混合动力", 1),
    BE_VS("纯电动", 2),
    FUEL_CELL_ELECTRIC("燃料电池电动", 3),
    PLUG_IN_HYBRID("插电式混动", 4),
    INCREMENTAL_HYBRID("增程式混动", 5);

    @JsonValue
    private String key;

    @EnumValue
    private int value;


    TpsVehicleDataKeyEnum(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public Integer getValue() {
        return Integer.valueOf(this.value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return "TpsVehicleDataKeyEnum{key='" + this.key + '\'' + ", value=" + this.value + '}';
    }

}
