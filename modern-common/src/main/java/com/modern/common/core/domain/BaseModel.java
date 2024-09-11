package com.modern.common.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.common.core.domain
 * @Project：tsp
 * @name：BaseModel
 * @Date：2024/8/28 17:21
 * @Filename：BaseModel
 */
@Data
@Entity
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("创建者")
    //@Column(name = "create_by", columnDefinition = "VARCHAR", length = 64)
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    //@Column(name = "create_time", columnDefinition = "DATETIME")
    private LocalDateTime createTime;

    @ApiModelProperty("修改者")
    // @Column(name = "update_by", columnDefinition = "VARCHAR", length = 64)
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    // @Column(name = "update_time", columnDefinition = "DATETIME")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除 1-是 0-否")
    // @Column(name = "是否删除 1-是 0-否", columnDefinition = "INTdefaultValue = 0")
    private Integer isDelete;
}
