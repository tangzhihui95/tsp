package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspVehicleStdModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspVehicleStdModeMapper
 * @Date：2024/10/14 15:56
 * @Filename：TspVehicleStdModeMapper
 */
public interface TspVehicleStdModeMapper extends BaseMapperPlus<TspVehicleStdModel> {

    @Select({"SELECT GROUP_CONCAT(std_mode_name) AS modeNames FROM tsp_vehicle_std_model WHERE is_delete = 0 GROUP BY std_mode_name"})
    List<String> getLabelMap();

    @Select({"SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) AS date FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id"})
    List<String> getAllDate();

    @Select({"SELECT a.std_mode_name AS name,count(t.tsp_vehicle_std_model_id) AS sale FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id ${ew.customSqlSegment}"})
    List<Map<String, Object>> getAllStdCount(@Param("ew") Wrapper<TspVehicleStdModel> paramWrapper);

    @Select({"SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) as date,a.std_mode_name as name,count(t.tsp_vehicle_std_model_id) as count FROM tsp_vehicle t LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id group by DATE_FORMAT( t.create_time, '%Y-%m-%d' )"})
    List<Map<String, Object>> getAllData();

    @Select({"SELECT CONCAT('{',`name`,',','\"date\":','\"',date,'\"',',','\"count\":','\"',count,'\"','}') AS str  FROM (SELECT CONCAT(GROUP_CONCAT( '\"',std_mode_name, '\":\"', count,'\"' )) AS name,date,SUM( count ) AS count                     FROM (SELECT DATE_FORMAT( t.create_time, '%Y-%m-%d' ) as date,a.std_mode_name,count(t.tsp_vehicle_std_model_id) as count FROM tsp_vehicle t                      LEFT JOIN tsp_vehicle_std_model a ON t.tsp_vehicle_std_model_id = a.id                      WHERE DATE_SUB( CURDATE( ), INTERVAL #{day} DAY ) <= DATE( t.create_time) AND t.is_delete = 0 AND t.state = 3  GROUP BY date,a.std_mode_name ORDER BY t.update_time ASC) AS cr                      GROUP BY date,count) AS rs"})
    List<String> getAllStrData();

    @Select({"SELECT * FROM tsp_vehicle_std_model a LEFT JOIN tsp_vehicle_model b ON a.tsp_vehicle_model_id = b.id ${ew.customSqlSegment}"})
    TspVehicleStdModel getByStdModeName(@Param("ew") Wrapper<TspVehicleStdModel> paramWrapper);
}
