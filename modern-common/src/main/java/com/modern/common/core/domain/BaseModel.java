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
    @ApiModelProperty("ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "主键ID", isAutoIncrement = true, isKey = true, type = MySqlTypeConstant.BIGINT)
    private Long id;

    @ApiModelProperty("创建者")
    @Column(comment = "创建者", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "创建时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime createTime;

    @ApiModelProperty("修改者")
    @Column(comment = "修改者", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(comment = "修改时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime updateTime;

    @TableLogic(value = "0", delval = "1")
    @TableField(select = false)
    @ApiModelProperty("是否删除 1-是 0-否")
    @Column(comment = "是否删除 1-是 0-否", type = MySqlTypeConstant.INT, defaultValue = "0")
    private Integer isDelete;
}
