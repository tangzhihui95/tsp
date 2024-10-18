package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;


/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspVehicleModel
 * @Date：2024/10/14 15:49
 * @Filename：TspVehicleModel
 */
@Table(name = "tsp_vehicle_model",comment = "摩登-TSP-车型")
@Alias("TspVehicleModel")
@TableName("tsp_vehicle_model")
@Data
public class TspVehicleModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Column(comment = "一级车型", type = MySqlTypeConstant.VARCHAR, length = 55)
    @Excel(name = "一级车型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 1)
    private String vehicleModelName;

    public static final String TSP_VEHICLE_TYPE_ID = "tsp_vehicle_type_id";

    public static final String VEHICLE_MODEL_NAME = "vehicle_model_name";

    public static final String PARENT_MODEL_ID = "parent_model_id";

}
