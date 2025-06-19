package com.modern.common.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;


/**
 * @Author：tzh
 * @Package：com.modern.common.core.domain
 * @Project：tsp
 * @name：BaseExModel
 * @Date：2025/6/12 17:55
 * @Filename：BaseExModel
 */
@Data
public class BaseExModel implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected Long id;

    @ApiModelProperty("vin")
    protected String vin;

    @ApiModelProperty("数据类型")
    protected Integer dataType;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("采集时间")
    protected LocalDateTime collectTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("接收时间")
    protected LocalDateTime createTime;

    @TableLogic(value = "0", delval = "1")
    @TableField(select = false)
    private Integer deleted;

    public static final String ID = "id";

    public static final String VIN = "vin";

    public static final String DATA_TYPE = "data_type";

    public static final String COLLECT_TIME = "collect_time";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETED = "deleted";
}
