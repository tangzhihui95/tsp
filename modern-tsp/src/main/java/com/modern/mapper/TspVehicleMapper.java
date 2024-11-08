package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspVehicle;
import com.modern.model.dto.TspVehicleExFactoryTemplateDTO;
import com.modern.model.dto.TspVehiclePageListDTO;
import com.modern.model.dto.TspVehicleSaleTemplateDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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

    @Select({"SELECT t.vin as 'vin',t.provider_name as 'providerName',t.configure_name as 'configureName',t.color as 'color',t.batch_no as 'batchNo',t.purpose as 'purpose',DATE_FORMAT( t.ex_factory_date, '%Y-%m-%d' ) AS 'exFactoryDate',DATE_FORMAT( t.operate_date, '%Y-%m-%d' ) AS 'operateDate',t.label as 'label',t.remark as 'remark',f.sn as 'sn',c.name as 'name',d.model_name as 'modelName',g.vehicle_model_name as 'vehicleModelName',a.std_mode_name as 'stdModelName',t.engine_num as 'engineNum',t.motor_num as 'motorNum',t.cdu_num as 'cduNum',t.motor_brand as 'motorBrand',t.ess_num as 'essNum',t.ess_model as 'essModel' FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON a.id = t.tsp_vehicle_std_model_id LEFT JOIN tsp_user b ON b.id = t.tsp_user_id LEFT JOIN tsp_vehicle_model g ON g.id = a.tsp_vehicle_model_id LEFT JOIN tsp_equipment f ON f.id = t.tsp_equipment_id LEFT JOIN tsp_equipment_model d ON d.id = f.tsp_equipment_model_id LEFT JOIN tsp_vehicle_license e ON e.tsp_vehicle_id = t.id LEFT JOIN tsp_equipment_type c ON c.id = d.tsp_equipment_type_id ${ew.customSqlSegment}"})
    List<TspVehicleExFactoryTemplateDTO> getExFactoryList(Page<Object> paramPage, @Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT t.vin as 'vin',t.purpose as 'purpose',x.purchaser_state as 'purchaserState',x.purchaser as 'purchaser',x.vehicle_id_card as 'vehicleIdCard',x.price_tax as 'priceTax',x.invoice_no as 'invoiceNo',DATE_FORMAT( x.invoicing_date, '%Y-%m-%d' ) AS 'invoicingDate',x.is_san_bao as 'isSanBao',x.sales_unit_name as 'salesUnitName',x.sales_unit_address 'salesUnitAddress',y.vehicle_status as 'vehicleStatus',y.sales_channel as 'salesChannel',y.channel_type as 'channelType',y.employee_name as 'employeeName',y.new_vehicle_flag as 'newVehicleFlag' FROM tsp_vehicle t RIGHT JOIN tsp_vehicle_market x ON x.tsp_vehicle_id = t.id LEFT JOIN tsp_vehicle_std_model a ON a.id = t.tsp_vehicle_std_model_id LEFT JOIN tsp_user b ON b.id = t.tsp_user_id LEFT JOIN tsp_equipment f ON f.id = t.tsp_equipment_id LEFT JOIN tsp_equipment_model d ON d.id = f.tsp_equipment_model_id LEFT JOIN tsp_vehicle_license e ON e.tsp_vehicle_id = t.id LEFT JOIN tsp_vehicle_other y ON t.id = y.tsp_vehicle_id ${ew.customSqlSegment}"})
    List<TspVehicleSaleTemplateDTO> getSalesList(Page<Object> paramPage, @Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT mobile as 'mobile',real_name as 'realName',id_card as 'idCard' FROM tsp_use_vehicle_record WHERE tsp_vehicle_id = #{tspVehicleId}"})
    List<Map<String, Object>> getBind(Long paramLong);

    @Select({"SELECT COUNT(t.id) FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id LEFT JOIN tsp_vehicle_model g ON g.id = a.tsp_vehicle_model_id LEFT JOIN tsp_user b on t.tsp_user_id = b.id LEFT JOIN tsp_equipment c on t.tsp_equipment_id = c.id LEFT JOIN tsp_vehicle_license e on e.tsp_vehicle_id = t.id LEFT JOIN tsp_equipment f on f.id = t.tsp_equipment_id LEFT JOIN tsp_vehicle_audit d on t.id = d.tsp_vehicle_id ${ew.customSqlSegment}"})
    Integer getCount(@Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

}
