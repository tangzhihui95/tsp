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
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;

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

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "主键ID", isAutoIncrement = true, isKey = true, type = MySqlTypeConstant.BIGINT)
    @ApiModelProperty(value = "ID", required = true, example = "ID")
    private Long id;

    @Column(comment = "创建者", type = MySqlTypeConstant.VARCHAR, length = 55)
    @ApiModelProperty(value = "创建者", required = true, example = "创建者")
    private String createBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Excel(name = "创建时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM - dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", required = true, example = "创建时间")
    private LocalDateTime createTime;

    @Column(comment = "修改者", type = MySqlTypeConstant.VARCHAR, length = 55)
    @ApiModelProperty(value = "修改者", required = true, example = "修改者")
    private String updateBy;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Excel(name = "更新时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM - dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间", required = true, example = "更新时间")
    private LocalDateTime updateTime;

    @TableLogic(value = "0", delval = "1")
    @TableField(select = false)
    @Column(comment = "是否删除 1-是 0-否", type = MySqlTypeConstant.INT, defaultValue = "0")
    @ApiModelProperty(value = "是否删除 1-是 0-否", required = true, example = "0")
    private Integer isDelete;
}
