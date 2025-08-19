package com.modern.mapper;

import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspUserMapper
 * @Date：2024/10/23 14:11
 * @Filename：TspUserMapper
 */
public interface TspUserMapper extends BaseMapperPlus<TspUser> {

    @Select({"SELECT t.id,c.plate_code AS 'plateCode',a.vin AS 'vin',CONCAT(m.vehicle_model_name,b.std_mode_name)  AS 'vehicleModel',CONCAT(f.name,e.model_name) AS 'equipmentModel',d.icc_id AS 'ICCID',d.sn AS 'SN',d.imei AS 'IMEI',d.sim AS 'sim', (CASE g.status WHEN 'true' THEN '已实名认证' WHEN 'false' THEN '未实名认证' ELSE '未实名认证'END) AS 'status', g.card_auth_info AS 'cardInfo',g.receive_time AS date,a.motor_num AS 'motorNum' FROM tsp_user t LEFT JOIN tsp_vehicle a ON a.tsp_user_id = t.id LEFT JOIN tsp_vehicle_std_model b ON b.id = a.tsp_vehicle_std_model_id LEFT JOIN tsp_vehicle_model m ON m.id = b.tsp_vehicle_model_id LEFT JOIN tsp_vehicle_license c ON c.tsp_vehicle_id = a.id LEFT JOIN tsp_equipment d ON d.id = a.tsp_equipment_id LEFT JOIN tsp_equipment_model e ON e.id = d.tsp_equipment_model_id LEFT JOIN tsp_equipment_type f ON f.id = e.tsp_equipment_type_id LEFT JOIN tsp_vehicle_identification_receive g ON g.vin = a.vin WHERE t.id = #{tspUserId}"})
    List<Map<String, Object>> findCarInfo(Long paramLong);

    @Select({"SELECT DISTINCT c.plate_code AS 'plateCode',a.vin AS 'vin',CONCAT(m.vehicle_model_name,b.std_mode_name)  AS 'vehicleModel', CONCAT(f.name,e.model_name) AS 'equipmentModel',d.icc_id AS 'ICCID',d.sn AS 'SN',d.imei AS 'IMEI',d.sim AS 'sim',(CASE g.status WHEN 'true' THEN '已实名认证' WHEN 'false' THEN '未实名认证' ELSE '未实名认证'END) AS 'status', g.card_auth_info AS 'cardInfo',g.receive_time AS date,a.motor_num AS 'motorNum' FROM tsp_user_vehicle t LEFT JOIN tsp_vehicle a ON a.id = t.tsp_vehicle_id LEFT JOIN tsp_vehicle_std_model b ON b.id = a.tsp_vehicle_std_model_id LEFT JOIN tsp_vehicle_model m ON m.id = b.tsp_vehicle_model_id LEFT JOIN tsp_vehicle_license c ON c.tsp_vehicle_id = a.id LEFT JOIN tsp_vehicle_equipment h ON h.tsp_vehicle_id = a.id LEFT JOIN tsp_equipment d ON d.id = h.tsp_equipment_id LEFT JOIN tsp_equipment_model e ON e.id = d.tsp_equipment_model_id LEFT JOIN tsp_equipment_type f ON f.id = e.tsp_equipment_type_id LEFT JOIN tsp_vehicle_identification_receive g ON g.vin = a.vin WHERE t.tsp_user_id = #{tspUserId}"})
    List<Map<String, Object>> findHistory(Long paramLong);
}
