package com.modern.domain;

import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspEquipment
 * @Date：2024/8/28 15:41
 * @Filename：TspEquipment
 */

@Data
@Alias("TspEquipment")
@Table(name = "tsp_equipment")
@Entity
public class TspEquipment extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tsp_equipment_model_id")
    private Long tspEquipmentModelId;
    @Column(name = "sn")
    private String sn;
    @Column(name = "icc_id")
    private String iccId;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "sim")
    private String sim;

    @Column(name = "imei")
    private String imei;

    @Column(name = "version")
    private String version;

    @Column(name = "is_terminal")
    private Integer isTerminal;
    @Column(name = "supplier_code")
    private String supplierCode;

    @Column(name = "operator")
    private Integer operator;

    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "is_register")
    private Boolean isRegister;

    @Column(name = "is_scrap")
    private Boolean isScrap;

    @Column(name = "scrap_time")
    private LocalDateTime scrapTime;
}
