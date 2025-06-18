package com.modern.exinterface.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.entity.VehicleIntegrate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：VehicleIntegrateMapper
 * @Date：2025/6/13 10:38
 * @Filename：VehicleIntegrateMapper
 */
public interface VehicleIntegrateMapper extends BaseMapperPlus<VehicleIntegrate> {
    @Select({"SELECT t.id,t.vin,t.vehicle_state,t.charge_state,t.run_mode,t.speed,t.mileage,t.total_voltage,t.total_current,t.soc,t.dc_dc_state,t.gear,t.insulation_resistance,t.accelerator_pedal_position,t.brake_pedal_position,t.data_type,t.collect_time,t.create_time FROM vehicle_integrate t LEFT  JOIN tsp_vehicle a ON t.vin = a.vin AND a.vin IS NOT NULL LEFT  JOIN tsp_equipment b ON a.tsp_equipment_id = b.id  LEFT  JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment}"})
    IPage<VehicleIntegrate> getPageList(Page<VehicleIntegrate> paramPage, @Param("ew") Wrapper<VehicleIntegrate> paramWrapper);

}
