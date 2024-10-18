package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspVehicleLicense
 * @Date：2024/10/16 16:04
 * @Filename：TspVehicleLicense
 */
@Alias("TspVehicleLicense")
@Table(name = "tsp_vehicle_license",comment = "摩登- TSP -车牌信息")
@TableName(value = "tsp_vehicle_license", autoResultMap = true)
@Data
public class TspVehicleLicense extends BaseModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "vin", type = MySqlTypeConstant.VARCHAR, length = 255, isNull = false)
    private String vin;

    @Column(comment = "上牌车管所名称", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardPlaceName;

    @Column(comment = "上牌省份", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardProvince;

    @Column(comment = "上牌城市", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardCity;

    @Column(comment = "上牌区域", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardArea;

    @Column(comment = "上牌详细地址", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String awardPlaceAddress;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "当前上牌日期", type = MySqlTypeConstant.DATE)
    private LocalDate currentUpPlaceDate;

    //@Index("idx_plate_code")
    @Column(comment = "车牌号", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String plateCode;

    @Column(comment = "车牌颜色", type = MySqlTypeConstant.VARCHAR, length = 22)
    private String plateColour;

}
