package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;


/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspVehicleOther
 * @Date：2024/10/22 15:59
 * @Filename：TspVehicleOther
 */
@Table(name = "tsp_vehicle_other", comment = "摩登- TSP - 车卡信息推送补充数据")
@Alias("TspVehicleOther")
@TableName("tsp_vehicle_other")
@Data
public class TspVehicleOther extends BaseModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "", type = MySqlTypeConstant.INT, length = 11)
    private Integer vehicleOrigin;

    @Column(comment = "", type = MySqlTypeConstant.VARCHAR, length = 100)
    private String salesChannel;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "", type = MySqlTypeConstant.INT, length = 11)
    private Integer channelType;

    @Column(comment = "", type = MySqlTypeConstant.VARCHAR, length = 36)
    private String employeeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "", type = MySqlTypeConstant.INT, length = 11)
    private Integer fuelType;

    @Column(comment = "", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String engineNum;

    @Column(comment = "", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String motorNum;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "", type = MySqlTypeConstant.INT, length = 11, isNull = false)
    private Integer vehicleStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "", type = MySqlTypeConstant.INT, length = 11, isNull = false)
    private Integer newVehicleFlag;
}
