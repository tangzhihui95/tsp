package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspVehicle;
import com.modern.model.dto.TspVehiclePageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select({"SELECT t.id,t.tsp_equipment_id,t.tsp_vehicle_std_model_id,t.certification_state,t.vin,t.state,t.send_status,t.send_time,t.create_time,a.tsp_vehicle_model_id,a.std_mode_name,b.mobile,b.real_name,c.sim,c.is_online,e.plate_code,g.vehicle_model_name FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id LEFT JOIN tsp_vehicle_model g ON g.id = a.tsp_vehicle_model_id LEFT JOIN tsp_user b on t.tsp_user_id = b.id LEFT JOIN tsp_vehicle_license e on e.tsp_vehicle_id = t.id LEFT JOIN tsp_equipment c on t.tsp_equipment_id = c.id ${ew.customSqlSegment} "})
    IPage<TspVehiclePageListDTO> getPageList(Page<TspVehiclePageListDTO> paramPage, @Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT COUNT(t.vin)  FROM tsp_vehicle t LEFT JOIN tsp_equipment a ON t.tsp_equipment_id = a.id ${ew.customSqlSegment} "})
    Integer countByIsOnline(@Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT id FROM tsp_vehicle where tsp_equipment_id = #{tspEquipmentId} limit 1"})
    Long getByEquipmentId(Long paramLong);

    @Select({"SELECT tsp_equipment_id FROM tsp_vehicle where id = #{id} limit 1"})
    Long getByVehicleId(Long paramLong);

    /*@Select({"SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str  FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count                     FROM (SELECT  DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date, COUNT(*)AS count,  a.std_mode_name FROM                      tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id                      WHERE t.create_time <= #{endTime} AND t.create_time >= #{startTime} GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr                      GROUP BY date,count) AS rs"})
    List<TspVehicleVolumeDataDTO> dataStartTimeAndEndTime(@Param("startTime") String paramString1, @Param("endTime") String paramString2);

    @Select({"SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str  FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count                     FROM (SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) as date,a.std_mode_name,count(t.tsp_vehicle_std_model_id) as count FROM tsp_vehicle t                     LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id                     GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr                     GROUP BY date) AS rs"})
    List<TspVehicleVolumeDataDTO> findVolumeData();

    @Select({"SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str  FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count                     FROM (SELECT  DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date, COUNT(*)AS count,  a.std_mode_name FROM                      tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id                      WHERE DATE_SUB( CURDATE( ), INTERVAL #{day} DAY ) <= DATE( t.create_time) GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr                      GROUP BY date) AS rs"})
    List<TspVehicleVolumeDataDTO> findVolumeDataDays(@Param("day") int paramInt);

    @Select({"SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str  FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count                     FROM (SELECT  DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date, COUNT(*)AS count,  a.std_mode_name FROM                      tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id                      WHERE DATE_SUB( CURDATE( ), INTERVAL #{month} MONTH ) <= DATE( t.create_time) GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr                      GROUP BY date) AS rs"})
    List<TspVehicleVolumeDataDTO> findVolumeDataMonth(@Param("month") int paramInt);

    @Select({"SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str  FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count                     FROM (SELECT  DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date, COUNT(*)AS count,  a.std_mode_name FROM                      tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id                      WHERE DATE_SUB( CURDATE( ), INTERVAL #{year} YEAR ) <= DATE( t.create_time) GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr                      GROUP BY date) AS rs"})
    List<TspVehicleVolumeDataDTO> findVolumeYear(@Param("year") int paramInt);

    @Select({"SELECT DATE_FORMAT( a.create_time, '%Y-%m-%d' ) AS date,IFNULL(SUM( a.mileage ),0) AS mileageTotal,IFNULL(a.mileage,0) AS mileage  FROM tsp_vehicle t RIGHT JOIN vehicle_integrate a ON t.vin = a.vin ${ew.customSqlSegment} "})
    List<TspVehicleRangeDataDTO> findRangeDataAreaVehicle(@Param("ew") Wrapper<TspVehicle> paramWrapper);

    @Select({"SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date,IFNULL( SUM( t.mileage ), 0 ) AS mileageTotal, IFNULL( t.mileage, 0 ) AS mileage,c.award_place_name  FROM vehicle_integrate t  LEFT JOIN tsp_vehicle a ON t.vin = a.vin  LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id  LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment} "})
    List<TspVehicleRangeDataDTO> findRangeDataSingleVehicle(@Param("ew") Wrapper<VehicleIntegrate> paramWrapper);

    @Select({"SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date,IFNULL( COUNT( t.id ), 0 ) AS count  FROM vehicle_integrate t  LEFT JOIN tsp_vehicle a ON t.vin = a.vin  LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id  LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment} "})
    List<TspVehicleOnlineDataDTO> findOnlineDataVehicle(@Param("ew") Wrapper<VehicleIntegrate> paramWrapper);

    @Select({"SELECT t.vin,c.sn,d.plate_code FROM tsp_vehicle t LEFT JOIN tsp_equipment c ON t.tsp_equipment_id = c.id LEFT JOIN tsp_vehicle_license d ON t.id = d.tsp_vehicle_id ${ew.customSqlSegment}"})
    List<VehicleGpsSelectListDTO> findSelectList(@Param("ew") Wrapper<TspVehicle> paramWrapper);

    @Select({"SELECT id FROM tsp_vehicle where tsp_equipment_id = #{tspEquipmentId} limit 1"})
    Long getByEquipmentId(Long paramLong);

    @Select({"SELECT tsp_equipment_id FROM tsp_vehicle where id = #{id} limit 1"})
    Long getByVehicleId(Long paramLong);

    @Update({"update tsp_vehicle set state = #{state} where id = #{tspVehicleId}"})
    int updateSetState(@Param("state") Integer paramInteger, @Param("tspVehicleId") Long paramLong);

    @Update({"update tsp_vehicle set tsp_equipment_id = null where id = #{tspVehicleId}"})
    int updateSetNull(Long paramLong);

    @Select({"SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date,IFNULL( COUNT( t.id ), 0 ) AS sale  FROM vehicle_integrate t  LEFT JOIN tsp_vehicle a ON t.vin = a.vin  LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id  LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment} "})
    List<TspVehicleActivityDataDTO> findActivityDataVehicle(Wrapper<VehicleIntegrate> paramWrapper);

    @Select({"SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date,IFNULL( COUNT( t.id ), 0 ) AS sale  FROM vehicle_integrate t  LEFT JOIN tsp_vehicle a ON t.vin = a.vin  LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id  LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment} "})
    List<TspVehicleOnlineDataDTO> getAllOnlineData();

    @Select({"SELECT DATE_FORMAT( create_time, '%Y-%m-%d' ) AS date FROM vehicle_integrate GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' )"})
    List<String> getAllDate();

    @Select({"SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date  FROM vehicle_integrate t  LEFT JOIN tsp_vehicle a ON t.vin = a.vin  LEFT JOIN tsp_equipment b ON a.tsp_equipment_id = b.id  LEFT JOIN tsp_vehicle_license c ON a.id = c.tsp_vehicle_id ${ew.customSqlSegment} "})
    List<String> getAllDateSearch(@Param("ew") Wrapper<VehicleIntegrate> paramWrapper);

    @Select({"SELECT t.vin FROM vehicle_integrate t LEFT JOIN tsp_vehicle a ON t.vin = a.vin ${ew.customSqlSegment}"})
    List<String> getAllVin(@Param("ew") Wrapper<VehicleIntegrate> paramWrapper);

    @Select({"SELECT t.mileage from vehicle_integrate t LEFT JOIN tsp_vehicle a ON a.vin = t.vin LEFT JOIN tsp_vehicle_license b ON a.id = b.tsp_vehicle_id LEFT JOIN tsp_equipment c ON c.id = a.tsp_equipment_id ${ew.customSqlSegment}"})
    Integer findEachMileage(@Param("ew") Wrapper<VehicleIntegrate> paramWrapper);

    @Select({"select count(vin) from tsp_vehicle ${ew.customSqlSegment}"})
    Integer getVehicleNumberByDate(@Param("ew") Wrapper<TspVehicle> paramWrapper);

    @Select({"SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date FROM vehicle_integrate t LEFT JOIN tsp_vehicle a ON a.vin = t.vin LEFT JOIN tsp_vehicle_license b ON a.id = b.tsp_vehicle_id LEFT JOIN tsp_equipment c ON c.id = a.tsp_equipment_id WHERE t.vin LIKE #{search} OR c.sn LIKE #{search} OR b.plate_code LIKE #{search} GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' )"})
    List<String> getVinDate(String paramString);

    @Select({"SELECT t.vin as 'vin',t.provider_name as 'providerName',t.configure_name as 'configureName',t.color as 'color',t.batch_no as 'batchNo',t.purpose as 'purpose',DATE_FORMAT( t.ex_factory_date, '%Y-%m-%d' ) AS 'exFactoryDate',DATE_FORMAT( t.operate_date, '%Y-%m-%d' ) AS 'operateDate',t.label as 'label',t.remark as 'remark',f.sn as 'sn',c.name as 'name',d.model_name as 'modelName',g.vehicle_model_name as 'vehicleModelName',a.std_mode_name as 'stdModelName',t.engine_num as 'engineNum',t.motor_num as 'motorNum',t.cdu_num as 'cduNum',t.motor_brand as 'motorBrand',t.ess_num as 'essNum',t.ess_model as 'essModel' FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON a.id = t.tsp_vehicle_std_model_id LEFT JOIN tsp_user b ON b.id = t.tsp_user_id LEFT JOIN tsp_vehicle_model g ON g.id = a.tsp_vehicle_model_id LEFT JOIN tsp_equipment f ON f.id = t.tsp_equipment_id LEFT JOIN tsp_equipment_model d ON d.id = f.tsp_equipment_model_id LEFT JOIN tsp_vehicle_license e ON e.tsp_vehicle_id = t.id LEFT JOIN tsp_equipment_type c ON c.id = d.tsp_equipment_type_id ${ew.customSqlSegment}"})
    List<TspVehicleExFactoryTemplateDTO> getExFactoryList(Page<Object> paramPage, @Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT t.vin as 'vin',t.purpose as 'purpose',x.purchaser_state as 'purchaserState',x.purchaser as 'purchaser',x.vehicle_id_card as 'vehicleIdCard',x.price_tax as 'priceTax',x.invoice_no as 'invoiceNo',DATE_FORMAT( x.invoicing_date, '%Y-%m-%d' ) AS 'invoicingDate',x.is_san_bao as 'isSanBao',x.sales_unit_name as 'salesUnitName',x.sales_unit_address 'salesUnitAddress',y.vehicle_status as 'vehicleStatus',y.sales_channel as 'salesChannel',y.channel_type as 'channelType',y.employee_name as 'employeeName',y.new_vehicle_flag as 'newVehicleFlag' FROM tsp_vehicle t RIGHT JOIN tsp_vehicle_market x ON x.tsp_vehicle_id = t.id LEFT JOIN tsp_vehicle_std_model a ON a.id = t.tsp_vehicle_std_model_id LEFT JOIN tsp_user b ON b.id = t.tsp_user_id LEFT JOIN tsp_equipment f ON f.id = t.tsp_equipment_id LEFT JOIN tsp_equipment_model d ON d.id = f.tsp_equipment_model_id LEFT JOIN tsp_vehicle_license e ON e.tsp_vehicle_id = t.id LEFT JOIN tsp_vehicle_other y ON t.id = y.tsp_vehicle_id ${ew.customSqlSegment}"})
    List<TspVehicleSaleTemplateDTO> getSalesList(Page<Object> paramPage, @Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT COUNT(t.id) FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id LEFT JOIN tsp_vehicle_model g ON g.id = a.tsp_vehicle_model_id LEFT JOIN tsp_user b on t.tsp_user_id = b.id LEFT JOIN tsp_equipment c on t.tsp_equipment_id = c.id LEFT JOIN tsp_vehicle_license e on e.tsp_vehicle_id = t.id LEFT JOIN tsp_equipment f on f.id = t.tsp_equipment_id LEFT JOIN tsp_vehicle_audit d on t.id = d.tsp_vehicle_id ${ew.customSqlSegment}"})
    Integer getCount(@Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT count(t.id) FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id LEFT JOIN tsp_vehicle_model b ON b.id = a.tsp_vehicle_model_id LEFT JOIN tsp_user c on t.tsp_user_id = c.id LEFT JOIN tsp_vehicle_license d on d.tsp_vehicle_id = t.id "})
    Integer getFlowDataCount(@Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT t.vin,c.real_name,c.mobile,d.plate_code,a.std_mode_name FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id LEFT JOIN tsp_vehicle_model b ON b.id = a.tsp_vehicle_model_id LEFT JOIN tsp_user c on t.tsp_user_id = c.id LEFT JOIN tsp_vehicle_license d on d.tsp_vehicle_id = t.id ${ew.customSqlSegment} "})
    IPage<TspVehicleFlowDataDTO> getPageListFlowData(Page<TspVehicleFlowDataDTO> paramPage, @Param("ew") QueryWrapper<TspVehicle> paramQueryWrapper);

    @Select({"SELECT COUNT(cr.vin) as 'count',cr.date as 'date' FROM (SELECT t.vin AS 'vin',DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date,b.plate_code AS 'plateCode',c.sn AS 'sn',b.award_province AS 'province',b.award_city AS 'city',b.award_area AS 'area',d.sales_unit_name AS 'dealerName' FROM vehicle_integrate t LEFT JOIN tsp_vehicle a ON t.vin = a.vin LEFT JOIN tsp_equipment c on a.tsp_equipment_id = c.id LEFT JOIN tsp_vehicle_license b on b.tsp_vehicle_id = a.id LEFT JOIN tsp_vehicle_market d on d.tsp_vehicle_id = a.id WHERE a.is_delete = 0 AND a.state != 5 GROUP BY t.vin,DATE_FORMAT( t.create_time, '%Y-%m-%d' ) ORDER BY DATE_FORMAT( t.create_time, '%Y-%m-%d' ) ASC) AS cr ${ew.customSqlSegment}"})
    List<TspVehicleOnlineDataDTO> getAllVehicleOnlineData(@Param("ew") QueryWrapper<VehicleIntegrate> paramQueryWrapper);

    @Select({"SELECT cr.date AS 'date',COUNT(cr.vin) AS 'count' FROM (SELECT t.vin AS 'vin', DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS 'date', b.plate_code AS 'plateCode',b.award_province AS 'province',b.award_city AS 'city',b.award_area AS 'area',d.sales_unit_name AS 'dealerName' FROM tsp_vehicle t LEFT JOIN vehicle_integrate a ON t.vin = a.vin LEFT JOIN tsp_vehicle_license b on b.tsp_vehicle_id = t.id LEFT JOIN tsp_vehicle_market d on d.tsp_vehicle_id = t.id WHERE t.is_delete = 0 GROUP BY t.vin,DATE_FORMAT( t.create_time, '%Y-%m-%d' ) ORDER BY DATE_FORMAT( t.create_time, '%Y-%m-%d' ) ASC) AS cr ${ew.customSqlSegment}"})
    List<TspVehicleOnlineDataDTO> getAllVehicleSignData(@Param("ew") QueryWrapper<VehicleIntegrate> paramQueryWrapper);

    @Select({"SELECT MAX(t.mileage) AS mileageTotal,DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date,t.vin FROM vehicle_integrate t LEFT JOIN tsp_vehicle a ON a.vin = t.vin LEFT JOIN tsp_vehicle_license b ON a.id = b.tsp_vehicle_id LEFT JOIN tsp_equipment c ON a.tsp_equipment_id = c.id ${ew.customSqlSegment}"})
    List<TspVehicleRangeDataDTO> getOneVehicleMileageData(@Param("ew") QueryWrapper<VehicleIntegrate> paramQueryWrapper);

    @Select({"SELECT t.vin AS 'vin',a.plate_code AS 'plateCode',b.sim AS 'sim',(CASE b.operator WHEN 1 THEN '2 THEN '3 THEN 'ELSE 'END) AS 'operator',b.sn AS 'sn',b.icc_id AS 'iccid',b.imei AS 'imei' FROM tsp_vehicle t LEFT JOIN tsp_vehicle_license a ON a.tsp_vehicle_id = t.id LEFT JOIN tsp_equipment b ON t.tsp_equipment_id = b.id WHERE t.id = #{tspVehicleId}"})
    Map<String, Object> getRealNameMessage(Long paramLong);

    @Select({"SELECT mobile as 'mobile',real_name as 'realName',id_card as 'idCard' FROM tsp_use_vehicle_record WHERE tsp_vehicle_id = #{tspVehicleId}"})
    List<Map<String, Object>> getBind(Long paramLong);

    List<TspVehicleOnlineDataDTO> selectNewWhere(@Param("vo") TspVehicleAlertDataVO paramTspVehicleAlertDataVO);

    List<String> selectVin(@Param("vo") TspVehicleOnlineDataVO paramTspVehicleOnlineDataVO);

    List<String> selectAllVin(@Param("vo") TspVehicleOnlineDataVO paramTspVehicleOnlineDataVO);

    List<String> selectVinByDealerId(@Param("dealerId") Long paramLong);

    List<String> selectVinByAddress(@Param("vo") AddressVo paramAddressVo);

    List<TspVehicleRangeDataDTO> selectChartMileage(TspVehicleRangeDataVO paramTspVehicleRangeDataVO);

    Long countAllCar(@Param("vo") TspVehicleOnlineDataVO paramTspVehicleOnlineDataVO);

    List<String> selectVinByDealerIds(@Param("dealerIds") Set<Long> paramSet);

    List<VolumeDTO> volumeChartData(@Param("vo") TspVehicleAlertDataVO paramTspVehicleAlertDataVO);*/
}
