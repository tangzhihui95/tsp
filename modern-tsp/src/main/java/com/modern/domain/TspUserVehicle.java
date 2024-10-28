package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspUserVehicle
 * @Date：2024/10/24 16:14
 * @Filename：TspUserVehicle
 */
@Table(name = "tsp_user_vehicle", comment = "- TSP - ")
@Alias("TspUserVehicle")
@TableName("tsp_user_vehicle")
@Data
public class TspUserVehicle extends BaseModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "用户主键", type = MySqlTypeConstant.BIGINT)
    private Long tspUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车辆主键", type = MySqlTypeConstant.BIGINT)
    private Long tspVehicleId;
}
