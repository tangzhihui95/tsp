package com.modern.common.core.domain.dto;

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
 * @Package：com.modern.common.core.domain.vo
 * @Project：tsp
 * @name：BaseVO
 * @Date：2024/8/28 14:56
 * @Filename：BaseVO
 */
@Data
@Entity
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("创建人")
    private String createBy;
    @ApiModelProperty("创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @ApiModelProperty("更新人")
    private String updateBy;
    @ApiModelProperty("更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除 1-是 0-否")
    // @Column(name = "是否删除 1-是 0-否", columnDefinition = "INTdefaultValue = 0")
    private Integer isDelete;
}
