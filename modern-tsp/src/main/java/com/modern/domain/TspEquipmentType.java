package com.modern.domain;

import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspEquipmentType
 * @Date：2024/8/28 17:05
 * @Filename：TspEquipmentType
 */
@Data
@Alias("TspEquipmentType")
@Entity
@Table(name = "tsp_equipment_type")
public class TspEquipmentType extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "suppliers")
    private String suppliers;
    @Column(name = "is_terminal")
    private Integer isTerminal;
    @Column(name = "extra_type")
    private String extraType;


}
