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
 * @name：TspUseVehicleRecord
 * @Date：2024/10/23 15:17
 * @Filename：TspUseVehicleRecord
 */
@Table(name = "tsp_use_vehicle_record", comment = "摩登- TSP - 车辆绑定记录")
@Alias("TspUseVehicleRecord")
@TableName("tsp_use_vehicle_record")
@Data
public class TspUseVehicleRecord extends BaseModel {
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleId;

    @Column(comment = "", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String mobile;

    @Column(comment = "", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String realName;

    @Column(comment = "", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String idCard;

    @Column(comment = "", type = MySqlTypeConstant.INT)
    private Integer version;

    @Column(comment = "", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String remark;
}
