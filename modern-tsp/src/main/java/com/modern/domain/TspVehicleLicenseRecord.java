package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspVehicleLicenseRecord
 * @Date：2024/10/18 13:40
 * @Filename：TspVehicleLicenseRecord
 */

@Alias("TspVehicleLicenseRecord")
@Table(value = "Tsp_vehicle_license_record", comment = "摩登- TSP - 车辆操作记录")
@TableName("Tsp_vehicle_license_record")
@Data
public class TspVehicleLicenseRecord extends BaseModel {
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleId;

    @Column(comment = "车管所名称", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardPlaceName;

    @Column(comment = "上牌省份", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardProvince;

    @Column(comment = "上牌城市", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardCity;

    @Column(comment = "上牌区域", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String awardArea;

    @Column(comment = "上牌详细地址", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String awardPlaceAddress;

    @Column(comment = "版本号", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer version;

    @Column(comment = "车牌号", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String plateCode;

    @Column(comment = "车辆颜色", type = MySqlTypeConstant.VARCHAR, length = 22)
    private String plateColour;

    @TableField(value = "plate_img_urls", typeHandler = FastjsonTypeHandler.class)
    @Column(comment = "车辆照片", type = MySqlTypeConstant.TEXT)
    private List<String> plateImgUrls = new ArrayList<>();

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "上牌日期", type = MySqlTypeConstant.DATE)
    private LocalDate upPlaceDate;
}
