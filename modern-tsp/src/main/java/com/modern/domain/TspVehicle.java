package com.modern.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.core.domain.BaseModel;
import com.modern.enums.TspVehicleStateEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspVehicle
 * @Date：2024/9/12 15:11
 * @Filename：TspVehicle
 */
@Table(name = "tsp_vehicle")
@Data
@Entity
@Alias("TspVehicle")
public class TspVehicle extends BaseModel {
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "user_id")
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "dept_id")
    private Long deptId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "tsp_user_id")
    private Long tspUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "tsp_vehicle_model_id")
    private Long tspVehicleStdModelId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "tsp_equipment_id")
    private Long tspEquipmentId;

    @Column(name = "vin")
    private String vin;

    @Column(name = "operator")
    private String operator;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "configure_name")
    private String configureName;

    @Column(name = "color")
    private String color;

    @Column(name = "batch_no")
    private String batchNo;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "engine_num")
    private String engineNum;

    @Column(name = "motor_num")
    private String motorNum;

    @Column(name = "ess_num")
    private String essNum;

    @Column(name = "cdu_num")
    private String cduNum;

    @Column(name = "ess_model")
    private String essModel;

    @Column(name = "motor_brand")
    private String motorBrand;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ex_factory_date")
    private LocalDate exFactoryDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "operate_date")
    private LocalDate operateDate;

    @Column(name = "label")
    private String label;

    //@JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    //@Column(name = "certification_state")
    //private TspVehicleEnumCertificationState certificationState;

    //@JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    @Column(name = "state")
    private TspVehicleStateEnum state;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "current_bind_time")
    private LocalDateTime currentBindTime;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "is_complete")
    private Boolean isComplete;

    @Column(name = "send_status")
    private Integer sendStatus;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "scrap_time")
    private LocalDateTime scrapTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "dealer_id")
    private Long dealerId;

    @Column(name = "remark")
    private String remark;

    public static final String ID = "id";

    public static final String TSP_USER_ID = "tsp_user_id";

    public static final String TSP_VEHICLE_STD_MODEL_ID = "tsp_vehicle_std_model_id";

    public static final String TSP_EQUIPMENT_ID = "tsp_equipment_id";

    public static final String CONFIGURE_NAME = "configure_name";

    public static final String COLOR = "color";

    public static final String BATCH_NO = "batch_no";

    public static final String PURPOSE = "purpose";

    public static final String EX_FACTORY_DATE = "ex_factory_date";

    public static final String OPERATE_DATE = "operate_date";

    public static final String LABEL = "label";

    public static final String STATE = "state";

    public static final String INVOICING_DATE = "Invoicing_date";

    public static final String CURRENT_BIND_TIME = "current_bind_time";

    public static final String PROGRESS = "progress";

    public static final String IS_COMPLETE = "is_complete";

    public static final String REMARK = "remark";

    public static final String CERTIFICATION_STATE = "certification_state";

    public static final String VIN = "vin";

    public static final String SCRAP_TIME = "scrap_time";
}
