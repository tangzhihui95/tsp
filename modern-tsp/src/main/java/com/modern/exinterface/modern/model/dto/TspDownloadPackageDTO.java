package com.modern.exinterface.modern.model.dto;

import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspDownloadPackageDTO
 * @Date：2024/11/28 13:47
 * @Filename：TspDownloadPackageDTO
 */
@Data
public class TspDownloadPackageDTO {
    private String topic;

    private int qos;

    private String message;

    private String imei;
}
