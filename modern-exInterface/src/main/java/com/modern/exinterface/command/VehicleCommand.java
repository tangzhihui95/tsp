package com.modern.exinterface.command;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.entity.command
 * @Project：tsp
 * @name：VehicleCommand
 * @Date：2024/11/28 14:14
 * @Filename：VehicleCommand
 */
@Data
public class VehicleCommand extends Serializers.Base {
    protected Integer commandId;

    protected byte[] command;

    protected LocalDateTime sendTime;

    protected LocalDateTime vehicleReceiveTime;

    protected LocalDateTime vehicleExecuteTime;

    protected Integer vehicleExecuteResult;

}
