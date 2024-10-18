package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspEquipment
 * @Date：2024/8/28 15:41
 * @Filename：TspEquipment
 */

@Alias("TspEquipment")
@Table(name = "tsp_equipment", comment = "摩登- TSP - 设备")
@TableName("tsp_equipment")
@Data
public class TspEquipment extends BaseModel {
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "分类ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspEquipmentModelId;

    @Column(comment = "sn", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String sn;
    @Column(comment = "iccId", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String iccId;

    @Column(comment = "imsi", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String imsi;

    @Column(comment = "sim", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String sim;

    @Column(comment = "imei", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String imei;

    @Column(comment = "硬件版本号", type = MySqlTypeConstant.VARCHAR, length = 22)
    private String version;

    @Column(comment = "是否为终端1-是 0-否", type = MySqlTypeConstant.TINYINT, length = 1, isNull = false, defaultValue = "0")
    private Integer isTerminal;
    @Column(comment = "供应商代码", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String supplierCode;

    @Column(comment = "运营商0-未知 1-移动 2-联通 3-电信", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer operator;

    @Column(comment = "批次流水号", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String serialNumber;
    @Column(comment = "是否在线 1-在线 0-未在线", type = MySqlTypeConstant.TINYINT, length = 1, isNull = false, defaultValue = "0")
    private Boolean isOnline;

    @Column(comment = "是否注册 1-注册 0-未注册", type = MySqlTypeConstant.TINYINT, length = 1, isNull = false, defaultValue = "0")
    private Boolean isRegister;

    @Column(comment = "是否报废 1-是 0-否", type = MySqlTypeConstant.TINYINT, length = 1)
    private Boolean isScrap;

    @ApiModelProperty("设备报废时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "设备报废时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime scrapTime;
}
