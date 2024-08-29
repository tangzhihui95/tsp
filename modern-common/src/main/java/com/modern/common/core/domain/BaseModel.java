package com.modern.common.core.domain;

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

    // private Long id;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private Integer isDelete;
}
