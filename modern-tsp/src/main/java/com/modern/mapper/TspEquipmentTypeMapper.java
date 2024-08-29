package com.modern.mapper;

import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspEquipmentType;
import org.apache.ibatis.annotations.Select;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspEquipmentTypeMapper
 * @Date：2024/8/28 17:04
 * @Filename：TspEquipmentTypeMapper
 */
public interface TspEquipmentTypeMapper extends BaseMapperPlus<TspEquipmentType> {
    @Select({"SELECT count(t.id) FROM tsp_equipment t LEFT JOIN tsp_equipment_model a on a.id = t.tsp_equipment_model_id WHERE t.is_delete = 0 AND a.is_delete = 0 AND a.tsp_equipment_type_id = #{tspEquipmentTypeId}"})
    int countByTspEquipmentTypeId(Long paramLong);

    @Select({"SELECT count(t.id) FROM tsp_equipment t LEFT JOIN tsp_equipment_model a on a.id = t.tsp_equipment_model_id WHERE t.is_delete = 0 AND a.is_delete = 0 AND a.id = #{tspEquipmentModelId}"})
    int countByTspEquipmentModelId(Long paramLong);
}
