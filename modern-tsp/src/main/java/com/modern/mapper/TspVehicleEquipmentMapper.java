package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspVehicleEquipment;
import com.modern.model.dto.TspEquipmentPageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspVehicleEquipment
 * @Date：2024/10/18 13:22
 * @Filename：TspVehicleEquipment
 */
public interface TspVehicleEquipmentMapper extends BaseMapperPlus<TspVehicleEquipment> {

    @Select({"SELECT a.create_time,a.un_bind_time,b.sn,b.sim,b.icc_id,b.imei,CONCAT(d.name,' / ',c.model_name) AS 'modelName',a.upload_time FROM tsp_vehicle_equipment a LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id LEFT JOIN tsp_equipment_model c ON b.tsp_equipment_model_id = c.id LEFT JOIN tsp_equipment_type d ON c.tsp_equipment_type_id = d.id ${ew.customSqlSegment}"})
    IPage<TspEquipmentPageListDTO> getPageList(Page<TspEquipmentPageListDTO> paramPage, @Param("ew") QueryWrapper<TspVehicleEquipment> paramQueryWrapper);
}
