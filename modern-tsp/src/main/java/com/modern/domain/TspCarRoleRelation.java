package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspCarRoleRelation
 * @Date：2024/10/16 15:18
 * @Filename：TspCarRoleRelation
 */

@TableName("tsp_car_role_relation")
@Data
public class TspCarRoleRelation extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long carId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;

    @TableField(exist = false)
    private List<Long> carIds;

    @TableField(exist = false)
    private String roleName;
}
