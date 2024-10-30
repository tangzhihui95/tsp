package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspVehicle;
import com.modern.model.dto.TspVehiclePageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspVehicleMapper
 * @Date：2024/9/12 15:45
 * @Filename：TspVehicleMapper
 */
public interface TspVehicleMapper extends BaseMapperPlus<TspVehicle> {
    @Select({"SELECT e.icc_id AS 'iccid' FROM tsp_equipment e LEFT JOIN tsp_vehicle v ON e.id = v.tsp_equipment_id WHERE v.vin = '${vin}';"})
    String selectEquipmentICCIDByVin(@Param("vin") String paramString);

    @Select({"SELECT t.id,t.tsp_equipment_id,t.tsp_vehicle_std_model_id,t.certification_state,t.vin,t.state,t.send_status,t.send_time,t.create_time,a.tsp_vehicle_model_id,a.std_mode_name,b.mobile,b.real_name,c.sim,c.is_online,e.plate_code,g.vehicle_model_name,CONCAT(g.vehicle_model_name,' / ',a.std_mode_name) as 'vehicleType' FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id LEFT JOIN tsp_vehicle_model g ON g.id = a.tsp_vehicle_model_id LEFT JOIN tsp_user b on t.tsp_user_id = b.id LEFT JOIN tsp_vehicle_license e on e.tsp_vehicle_id = t.id LEFT JOIN tsp_equipment c on t.tsp_equipment_id = c.id ${ew.customSqlSegment} "})
    IPage<TspVehiclePageListDTO> getPageList(Page<TspVehiclePageListDTO> paramPage, @Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT COUNT(t.vin)  FROM tsp_vehicle t LEFT JOIN tsp_equipment a ON t.tsp_equipment_id = a.id ${ew.customSqlSegment} "})
    Integer countByIsOnline(@Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT id FROM tsp_vehicle where tsp_equipment_id = #{tspEquipmentId} limit 1"})
    Long getByEquipmentId(Long paramLong);

    @Select({"SELECT tsp_equipment_id FROM tsp_vehicle where id = #{id} limit 1"})
    Long getByVehicleId(Long paramLong);

    @Update({"update tsp_vehicle set state = #{state} where id = #{tspVehicleId}"})
    int updateSetState(@Param("state") Integer paramInteger, @Param("tspVehicleId") Long paramLong);

    @Update({"update tsp_vehicle set tsp_equipment_id = null where id = #{tspVehicleId}"})
    int updateSetNull(Long paramLong);

}
