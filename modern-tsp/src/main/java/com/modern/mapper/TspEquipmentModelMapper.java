package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspEquipmentModel;
import com.modern.model.dto.TspEquipmentModelPageListDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspEquipmentModelMapper
 * @Date：2024/8/29 10:29
 * @Filename：TspEquipmentModelMapper
 */
public interface TspEquipmentModelMapper extends BaseMapperPlus<TspEquipmentModel> {
    @Select({"SELECT t.id,t.model_name,t.suppliers,t.batch_number,a.name,a.is_terminal,a.extra_type FROM tsp_equipment_model t LEFT JOIN tsp_equipment_type a ON a.id = t.tsp_equipment_type_id ${ew.customSqlSegment}"})
    IPage<TspEquipmentModelPageListDTO> getPageList(Page<Object> paramPage, @Param("ew") QueryWrapper<TspEquipmentModel> paramQueryWrapper);

    @Select({"select * from tsp_equipment_model where id = #{id}"})
    TspEquipmentModel getByIdContainsDelete(@Param("id") Long paramLong);
}
