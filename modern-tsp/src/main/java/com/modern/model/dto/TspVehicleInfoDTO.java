package com.modern.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleInfoDTO
 * @Date：2024/10/17 14:43
 * @Filename：TspVehicleInfoDTO
 */
@ApiModel("车辆信息- 传输对象- 详细信息")
@Data
public class TspVehicleInfoDTO extends BaseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("用户ID")
    private Long tspUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车辆ID")
    private Long tspVehicleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleStdModelId;

    @ApiModelProperty("型号名称")
    private String stdModeName;

    @ApiModelProperty("车辆名称")
    private String vehicleModelName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleModelId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备ID")
    private Long tspEquipmentId;

    @ApiModelProperty("车辆审核认证ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleAuditId;

    @ApiModelProperty("车辆厂商")
    private String providerName;

    @ApiModelProperty("配置名称")
    private String configureName;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("VIN")
    private String vin;

    @ApiModelProperty("批次号")
    private String batchNo;

    @ApiModelProperty("用途")
    private String purpose;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出厂日期")
    private LocalDate exFactoryDate;

    @ApiModelProperty("经销商ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dealerId;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("运营日期")
    private LocalDate operateDate;

    @ApiModelProperty("标签")
    @JsonSerialize(contentUsing = ToStringSerializer.class)
    private List<Long> label;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("购买领域1-私人用车 2-单位用车 3-未知")
    private Integer purchaserState;

    @ApiModelProperty("购买方")
    private String purchaser;

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

    @TableField(value = "invoice_img_urls", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("发票地址")
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

    @ApiModelProperty("车牌照片")
    @TableField(value = "plate_img_urls", typeHandler = FastjsonTypeHandler.class)
    private List<String> plateImgUrls;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("当前上牌日期")
    private LocalDate currentUpPlaceDate;

    @ApiModelProperty("手机号(账号)")
    private String mobile;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("身份证正面")
    private String cardFrontImg;

    @ApiModelProperty("身份证反面")
    private String cardBackImg;

    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    @ApiModelProperty("状态1:已创建 2:已销售 3:已绑定 4:已解绑 5:已报废 6:已注册")
    private Integer state;

    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    @ApiModelProperty("认证状态 1:未认证 2:认证中 3:认证失败 4:已认证")
    private Integer certificationState;

    @ApiModelProperty("信息完成进度 0:基本信息 1:出厂信息 2:销售信息 3:上牌信息 4:绑定及MNO信息 5:出入库记录")
    private Integer progress;

    @ApiModelProperty("是否完成车辆资料信息 1:完成 0:未完成")
    private Boolean isComplete;
}
