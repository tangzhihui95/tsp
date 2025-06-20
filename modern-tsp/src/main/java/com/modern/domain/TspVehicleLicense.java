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
 * @name：TspVehicleLicense
 * @Date：2024/10/16 16:04
 * @Filename：TspVehicleLicense
 */
@Alias("TspVehicleLicense")
@Table(name = "tsp_vehicle_license", comment = "摩登- TSP -车牌信息")
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

    @TableField(value = "plate_img_urls", typeHandler = FastjsonTypeHandler.class)
    @Column(comment = "车辆照片", type = MySqlTypeConstant.TEXT)
    private List<String> plateImgUrls = new ArrayList<>();

/*    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "上牌日期", type = MySqlTypeConstant.DATE)
    private LocalDate upPlaceDate;*/

    public static final String TSP_VEHICLE_ID = "tsp_vehicle_id";

    public static final String AWARD_PLACE_NAME = "award_place_name";

    public static final String PLACE_AREA = "award_place_area";

    public static final String ADDRESS = "award_place_address";

    public static final String PLATE_CODE = "plate_code";

    public static final String PLATE_COLOUR = "plate_colour";

    public static final String PLATE_IMG_URLS = "plate_img_urls";

    public static final String UP_PLACE_DATE = "up_place_date";

    public static final String VERSION = "version";

}
