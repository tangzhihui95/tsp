package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.vo.BaseVO;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：VehicleVo
 * @Date：2024/10/16 15:20
 * @Filename：VehicleVo
 */
@Data
public class VehicleVo {
    private String vin;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String carType;

    private String dealerAddress;

    private String dealerId;

    private String roleNames;

    private String dealerName;

}
