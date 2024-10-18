package com.modern.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

/**
 * @Author：tzh
 * @Package：com.modern.enums
 * @Project：tsp
 * @name：TspVehicleEnumCertificationState
 * @Date：2024/10/15 16:18
 * @Filename：TspVehicleEnumCertificationState
 */
@ApiModel("车辆信息-认证状态")
public enum TspVehicleEnumCertificationState implements IEnum<Integer> {
    ALL(0, "全部"),
    NOT_CERTIFIED(1, "未认证"),
    UNDER_CERTIFICATION(2, "认证中"),
    FAILED_CERTIFICATION(3, "认证失败"),
    COMPLETE_CERTIFICATION(4, "已认证");

    @EnumValue
    private int value;

    @JsonValue
    private String key;

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    TspVehicleEnumCertificationState(int value, String key) {
        this.value = value;
        this.key = key;
    }

    public Integer getValue() {
        return Integer.valueOf(this.value);
    }
}
