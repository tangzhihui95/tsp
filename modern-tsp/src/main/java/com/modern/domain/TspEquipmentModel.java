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
 * @name：TspEquipmentModel
 * @Date：2024/8/28 17:30
 * @Filename：TspEquipmentModel
 */
@Data
@Alias("TspEquipmentModel")
@Table(name = "tsp_equipment_model",comment = "摩登-TSP-型号")
@TableName(value = "tsp_equipment_model", autoResultMap = true)
public class TspEquipmentModel extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "设备分类ID", type = MySqlTypeConstant.BIGINT, length = 22, isNull = false)
    private Long tspEquipmentTypeId;

    @Column(comment = "型号名称", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    private String modelName;
    @Column(comment = "供应商", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String suppliers;
    @Column(comment = "生产批次", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String batchNumber;
}
