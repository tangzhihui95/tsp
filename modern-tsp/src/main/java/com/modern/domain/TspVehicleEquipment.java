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
 * @name：TspVehicleEquipment
 * @Date：2024/10/17 17:15
 * @Filename：TspVehicleEquipment
 */
@Alias("TspVehicleEquipment")
@Table(name = "tsp_vehicle_equipment", comment = "摩登- TSP - 行车记录表")
@TableName(value = "tsp_vehicle_equipment", autoResultMap = true)
@Data
public class TspVehicleEquipment extends BaseModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆ID", type = MySqlTypeConstant.BIGINT)
    private Long tspVehicleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "设备ID", type = MySqlTypeConstant.BIGINT)
    private Long tspEquipmentId;

    @ApiModelProperty("上传时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "上传时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime uploadTime;

    @ApiModelProperty("解绑时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "解绑时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime unBindTime;
}
