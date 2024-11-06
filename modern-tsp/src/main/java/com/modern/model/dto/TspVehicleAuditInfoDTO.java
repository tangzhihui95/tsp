package com.modern.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.modern.common.core.domain.dto.BaseDto;
import com.modern.domain.TspUser;
import com.modern.domain.TspVehicle;
import com.modern.enums.TspVehicleEnumCertificationState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleAuditInfoDTO
 * @Date：2024/11/6 14:27
 * @Filename：TspVehicleAuditInfoDTO
 */
@ApiModel("车辆信息- 传输对象- 认证信息")
@Data
public class TspVehicleAuditInfoDTO extends BaseDto {
    @TableField(value = "card_img_urls", typeHandler = FastjsonTypeHandler.class)
    @ApiModelProperty("身份证正反面")
    private List<String> cardImgUrls;

    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    @ApiModelProperty("认证状态")
    private TspVehicleEnumCertificationState certificationState;

    @ApiModelProperty("不通过原因")
    private String auditRemark;

    @ApiModelProperty("用户信息")
    private TspUser tspUser;

    @ApiModelProperty("车辆及用户信息")
    private TspVehicle tspVehicle;

    @ApiModelProperty("车牌号")
    private String plateCode;
}
