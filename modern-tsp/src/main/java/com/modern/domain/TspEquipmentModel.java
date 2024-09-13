package com.modern.domain;

import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Entity
@Table(name = "tsp_equipment_model")
public class TspEquipmentModel extends BaseModel {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/
    @Column(name = "model_name")
    private String modelName;
    @Column(name = "suppliers")
    private String suppliers;
    @Column(name = "batch_number")
    private String batchNumber;
    @Column(name = "tsp_equipment_type_id")
    private Long tspEquipmentTypeId;
}
