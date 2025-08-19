package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspUserAddVO
 * @Date：2025/7/22 9:25
 * @Filename：TspUserAddVO
 */
@ApiModel("用户-请求对象 -添加")
@Data
public class TspUserAddVO {
    @ApiModelProperty(value = "用户ID", required = true, example = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspUserId;

    @NotEmpty(message = "手机号不存在")
    @ApiModelProperty(value = "手机号(账号)", required = true, example = "手机号(账号)")
    private String mobile;

    @NotEmpty(message = "真实姓名不能为空")
    @ApiModelProperty(value = "真实姓名", required = true, example = "真实姓名")
    private String realName;

    @NotEmpty(message = "身份证号不能为空")
    @ApiModelProperty(value = "身份证号", required = true, example = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "用户性别1-男 2-女 0-未知", required = true, example = "1")
    private Integer sex;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期", required = true, example = "出生日期")
    private LocalDate birthday;

    @ApiModelProperty(value = "身份证正面", required = true, example = "身份证正面")
    private String userCardFrontImg;

    @ApiModelProperty(value = "身份证反面", required = true, example = "身份证反面")
    private String userCardBackImg;

    @ApiModelProperty(value = "注册地址(省)", required = true, example = "注册地址(省)")
    private String province;

    @ApiModelProperty(value = "注册地址(市)", required = true, example = "注册地址(市)")
    private String city;

    @ApiModelProperty(value = "注册地址(区)", required = true, example = "注册地址(区)")
    private String area;

    @ApiModelProperty(value = "详细地址", required = true, example = "详细地址")
    private String address;

    @ApiModelProperty(value = "用户标签", required = true, example = "用户标签")
    @JsonSerialize(contentUsing = ToStringSerializer.class)
    private List<Long> label;
}
