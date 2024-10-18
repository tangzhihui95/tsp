package com.modern.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author：tzh
 * @Package：com.modern.enums
 * @Project：tsp
 * @name：TspVehicleSendEnum
 * @Date：2024/10/15 16:16
 * @Filename：TspVehicleSendEnum
 */

public enum TspVehicleSendEnum implements IEnum<Integer> {
    NOSEND("未推送", 0),
    SENDED("已推送", 1),
    ALL("全部", 2);

    @JsonValue
    private String key;

    @EnumValue
    private int value;

    TspVehicleSendEnum(String key, int value) {
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
        return "TspVehicleEnum{key='" + this.key + '\'' + ", value=" + this.value + '}';
    }
}
