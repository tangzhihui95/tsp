package com.modern.common.core.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
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
    @ApiModelProperty(value = "ID", required = true, example = "ID")
    private Long id;

    @ApiModelProperty(value = "创建人", required = true, example = "创建人")
    private String createBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Excel(name = "创建时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM - dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", required = true, example = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人", required = true, example = "更新人")
    private String updateBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Excel(name = "更新时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM - dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间", required = true, example = "更新时间")
    private LocalDateTime updateTime;

    @TableLogic(value = "0", delval = "1")
    @TableField(select = false)
    @Column(comment = "是否删除 1-是 0-否", type = MySqlTypeConstant.INT, defaultValue = "10")
    @ApiModelProperty(value = "是否删除 1-是 0-否", required = true)
    private Integer isDelete;
}
