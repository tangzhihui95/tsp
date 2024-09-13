package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspEquipment;
import com.modern.model.dto.TspEquipmentPageListDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspEquipmentMapper
 * @Date：2024/9/12 14:57
 * @Filename：TspEquipmentMapper
 */
public interface TspEquipmentMapper extends BaseMapperPlus<TspEquipment> {

    @Select({"SELECT a.id FROM tsp_equipment a LEFT JOIN tsp_vehicle b ON a.id = b.tsp_equipment_id WHERE a.is_scrap = 0 AND a.is_delete = 0 AND b.is_delete = 0"})
    List<Long> getBindEquipments();

    @Select({"SELECT COUNT(t.id) FROM tsp_equipment t LEFT JOIN tsp_vehicle_equipment tve ON t.id = tve.tsp_equipment_id LEFT JOIN tsp_vehicle tv ON  tve.tsp_vehicle_id = tv.id LEFT JOIN tsp_equipment_model a ON t.tsp_equipment_model_id = a.id LEFT JOIN tsp_equipment_type b ON a.tsp_equipment_type_id = b.id ${ew.customSqlSegment}"})
    Integer getCount(@Param("ew") QueryWrapper<TspEquipment> paramQueryWrapper);

    @Select({"SELECT t.id,tv.state,t.tsp_equipment_model_id,t.version,t.sn,t.icc_id,t.sim,t.imsi,t.imei,t.is_terminal,t.supplier_code,t.scrap_time,b.extra_type,t.serial_number,t.operator,t.is_online,t.is_register,t.is_scrap,t.create_time,a.model_name,a.suppliers,a.id as tsp_equipment_type_id ,CONCAT(b.name,' / ',a.model_name) as 'typeModel' FROM tsp_equipment t LEFT JOIN tsp_vehicle tv ON  t.id = tv.tsp_equipment_id and tv.is_delete = 0 LEFT JOIN tsp_equipment_model a ON t.tsp_equipment_model_id = a.id LEFT JOIN tsp_equipment_type b ON a.tsp_equipment_type_id = b.id ${ew.customSqlSegment}"})
    IPage<TspEquipmentPageListDTO> getPageList(Page<TspEquipmentPageListDTO> paramPage, @Param("ew") Wrapper<TspEquipment> paramWrapper);


}
