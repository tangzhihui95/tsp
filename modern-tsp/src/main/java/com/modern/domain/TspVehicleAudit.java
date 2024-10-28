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
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspVehicleAudit
 * @Date：2024/10/22 17:32
 * @Filename：TspVehicleAudit
 */
@Alias("TspVehicleAudit")
@Table(name = "tsp_vehicle_audit", comment = "摩登- TSP - 车辆认证审核")
@TableName(value = "tsp_vehicle_audit", autoResultMap = true)
@Data
public class TspVehicleAudit extends BaseModel {
    @Column(comment = "用户ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspUserId;

    @Column(comment = "车辆ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleId;

    @Column(comment = "身份证正面", type = MySqlTypeConstant.VARCHAR)
    private String cardFrontImg;

    @Column(comment = "身份证反面", type = MySqlTypeConstant.VARCHAR)
    private String cardBackImg;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "申请时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime applyTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "通过时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime passTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "驳回时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime rejectTime;

    @Column(comment = "认证备注", type = MySqlTypeConstant.VARCHAR)
    private String auditRemark;
}
