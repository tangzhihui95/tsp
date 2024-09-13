package com.modern.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

/**
 * @Author：tzh
 * @Package：com.modern.enums
 * @Project：tsp
 * @name：TspVehicleStateEnum
 * @Date：2024/9/12 14:48
 * @Filename：TspVehicleStateEnum
 */
@ApiModel("车辆信息-车辆状态")
public enum TspVehicleStateEnum implements IEnum<Integer> {

    ALL("全部", 0),
    CREATED("", 1),
    SOLD("", 2),
    BOUND("", 3),
    UNBOUND("", 4),
    SCRAPPED("", 5),
    ALREADY("", 6);

    @JsonValue
    private String key;

    @EnumValue
    private int value;

    TspVehicleStateEnum(String key, int value) {
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

    @Override
    public String toString() {
        return "TspVehicleStateEnum{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

}
