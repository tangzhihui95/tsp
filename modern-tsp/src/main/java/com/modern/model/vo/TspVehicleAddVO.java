package com.modern.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspVehicleAddVO
 * @Date：2024/10/17 14:20
 * @Filename：TspVehicleAddVO
 */
@ApiModel("车辆信息- 请求对象- 新增")
@Data
public class TspVehicleAddVO {
    @ApiModelProperty("车辆ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleStdModelId;

    @ApiModelProperty("车辆厂商")
    private String providerName;

    @ApiModelProperty("车辆用途")
    private String purpose;

    @ApiModelProperty("配置名称")
    private String configureName;

    @ApiModelProperty("外观颜色")
    private String color;

    @Length(min = 17, max = 17, message = "VIN必须17位")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "VIN格式必须17位数字或字母组成")
    @ApiModelProperty("vin")
    private String vin;

    @ApiModelProperty("批次号")
    private String batchNo;

    @ApiModelProperty("设备ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentId;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出厂日期")
    private LocalDate exFactoryDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("运营日期")
    private LocalDate operateDate;

    @ApiModelProperty("标签")
    private List<Long> label;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("购买方")
    private String purchaser;

    @ApiModelProperty("购买领域1-私人用车 2-单位用车 3-未知")
    private Integer purchaserState;

    @ApiModelProperty("车辆登记身份证")
    private String vehicleIdCard;

    @ApiModelProperty("价税合计")
    private BigDecimal priceTax;

    @ApiModelProperty("发票号码")
    private String invoiceNo;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("Invoicing_date")
    @ApiModelProperty("开发票日期")
    private LocalDate invoicingDate;

    @ApiModelProperty("是否三包")
    private Boolean isSanBao;

    @ApiModelProperty("销货单位名称")
    private String salesUnitName;

    @ApiModelProperty("销货单位地址")
    private String salesUnitAddress;

    @ApiModelProperty("燃料种类")
    private Integer fuelType;

    @ApiModelProperty("发动机号码")
    private String engineNum;

    @ApiModelProperty("电动机序列号")
    private String motorNum;

    @ApiModelProperty("电池包序列号")
    private String essNum;

    @ApiModelProperty("CDU序列号")
    private String cduNum;

    @ApiModelProperty("电池包规格")
    private String essModel;

    @ApiModelProperty("电动机品牌")
    private String motorBrand;

    @ApiModelProperty("车辆状态")
    private Integer vehicleStatus;

    @ApiModelProperty("新车标志 1:新车 2:非新车")
    private Integer newVehicleFlag;

    @ApiModelProperty("车辆产地")
    private Integer vehicleOrigin;

    @ApiModelProperty("车辆销售渠道名称(4s店，直销店等)")
    private String salesChannel;

    @ApiModelProperty("车辆销售渠道类型 1:实体渠道 2:人员上门 3:电子渠道")
    private Integer channelType;

    @ApiModelProperty("办理员工姓名")
    private String employeeName;

    @ApiModelProperty("发票地址")
    @TableField(value = "invoice_img_urls", typeHandler = FastjsonTypeHandler.class)
    private List<String> invoiceImgUrls;

    @ApiModelProperty("上牌车管所名称")
    private String awardPlaceName;

    @ApiModelProperty("省份")
    private String awardProvince;

    @ApiModelProperty("城市")
    private String awardCity;

    @ApiModelProperty("区域")
    private String awardArea;

    @ApiModelProperty("上牌详细地址")
    private String awardPlaceAddress;

    @ApiModelProperty("车牌号地区 如:京")
    private String plateCodeName;

    @ApiModelProperty("车牌号")
    private String plateCode;

    @ApiModelProperty("车牌颜色")
    private String plateColour;

    @TableField(value = "plate_img_urls", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("车牌照片")
    private List<String> plateImgUrls;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("当前上牌日期")
    private LocalDate currentUpPlaceDate;

    @Length(min = 11, max = 11, message = "格式错误，手机号应为11位数字组成")
    @ApiModelProperty("手机号(账号)")
    private String mobile;

    @ApiModelProperty("车辆审核认证ID")
    private Long tspVehicleAuditId;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("身份证号")
    @Pattern(regexp = "^\\d{15}|^\\d{17}([0-9]|X|x)$", message = "身份证长度为18位,由数组或数组+x组成")
    private String idCard;

    @ApiModelProperty("身份证正面")
    private String cardFrontImg;

    @ApiModelProperty("身份证反面")
    private String cardBackImg;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime currentBindTime;

    private Integer progress;

    private Boolean isComplete;

    @ApiModelProperty("修改状态")
    private Integer editType;

    @ApiModelProperty("经销商ID")
    private Long dealerId;
}
