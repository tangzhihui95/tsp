package com.modern.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspUserDTO
 * @Date：2025/8/1 14:26
 * @Filename：TspUserDTO
 */
@ApiModel("一般账户- 传输对象- 详情")
@Data
public class TspUserDTO extends BaseModel {

    @ApiModelProperty(value = "手机号(账号)", required = true, example = "手机号(账号)")
    private String mobile;

    @ApiModelProperty(value = "真实名字", required = true, example = "真实名字")
    private String realName;

    @ApiModelProperty(value = "用户头像", required = true, example = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "身份证号", required = true, example = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "性别", required = true, example = "1")
    private Integer sex;

    @ApiModelProperty(value = "身份证号", required = true, example = "身份证号")
    private Integer hasBind;

    @ApiModelProperty(value = "类型", required = true, example = "类型")
    private Integer type;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "出生日期", type = MySqlTypeConstant.DATE)
    @ApiModelProperty(value = "出生日期", required = true, example = "出生日期")
    private LocalDate birthday;

    @Column(comment = "极光推送ID", type = MySqlTypeConstant.BIGINT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "极光推送ID", required = true, example = "极光推送ID")
    private Long registrationId;

    @Column(comment = "注册地址(省)", type = MySqlTypeConstant.VARCHAR, length = 55)
    @ApiModelProperty(value = "注册地址(省)", required = true, example = "注册地址(省)")
    private String province;

    @Column(comment = "注册地址(市)", type = MySqlTypeConstant.VARCHAR, length = 255)
    @ApiModelProperty(value = "注册地址(市)", required = true, example = "注册地址(市)")
    private String city;

    @Column(comment = "注册地址(区)", type = MySqlTypeConstant.VARCHAR, length = 255)
    @ApiModelProperty(value = "注册地址(区)", required = true, example = "注册地址(区)")
    private String area;

    @Column(comment = "实名认证:0-未认证 1-已认证 (默认为0)", type = MySqlTypeConstant.TINYINT, defaultValue = "0")
    @ApiModelProperty(value = "实名认证:0-未认证 1-已认证 (默认为0)", required = true, example = "0")
    private Integer realNameAudit;

    @Column(comment = "注册渠道(0平台账号)", type = MySqlTypeConstant.TINYINT, defaultValue = "0")
    @ApiModelProperty(value = "注册渠道(0平台账号)", required = true, example = "0")
    private Integer registeredChannels;

    @Column(comment = "详细地址", type = MySqlTypeConstant.VARCHAR, length = 255)
    @ApiModelProperty(value = "详细地址", required = true, example = "详细地址")
    private String address;

    @Column(comment = "身份证正面", type = MySqlTypeConstant.VARCHAR)
    @ApiModelProperty(value = "身份证正面", required = true, example = "身份证正面")
    private String userCardFrontImg;

    @Column(comment = "身份证反面", type = MySqlTypeConstant.VARCHAR)
    @ApiModelProperty(value = "身份证反面", required = true, example = "身份证反面")
    private String userCardBackImg;

    @Column(comment = "用户标签", type = MySqlTypeConstant.VARCHAR, length = 255)
    @JsonSerialize(contentUsing = ToStringSerializer.class)
    @ApiModelProperty(value = "用户标签", required = true, example = "用户标签")
    private List<Long> label;

    @Column(comment = "是否启用 0-否 1-是", type = MySqlTypeConstant.TINYINT, isNull = false, defaultValue = "1")
    @ApiModelProperty(value = "是否启用 0-否 1-是", required = true, example = "1")
    private Boolean isEnable;

    @Column(comment = "用户来源 0-未知 1-商店 2-公众号 3-带三方", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    @ApiModelProperty(value = "用户来源 0-未知 1-商店 2-公众号 3-带三方", required = true, example = "1")
    private Integer source;
}
