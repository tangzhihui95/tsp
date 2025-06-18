package com.modern.exinterface.modern.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleCommandDTO
 * @Date：2024/11/28 14:08
 * @Filename：TspVehicleCommandDTO
 */
@Data
public class TspVehicleCommandDTO {

    private String vin;

    private LocalDateTime time;

    private Long id;

    private String commandId;

    private String command;
}
