package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicleStdModel;
import com.modern.mapper.TspVehicleStdModeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleStdModeRepository
 * @Date：2024/10/14 15:54
 * @Filename：TspVehicleStdModeRepository
 */
@Service
public class TspVehicleStdModeRepository extends ServicePlusImpl<TspVehicleStdModeMapper, TspVehicleStdModel, TspVehicleStdModel> {

    public List<TspVehicleStdModel> getByTspVehicleModelId(Long id) {
        LambdaQueryWrapper<TspVehicleStdModel> ew = new LambdaQueryWrapper();
        ew.eq(TspVehicleStdModel::getTspVehicleModelId, id);
        return list((Wrapper) ew);
    }

    public List<TspVehicleStdModel> findByTspVehicleModelId(Long tspVehicleModelId) {
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper();
        ew.eq("tsp_vehicle_model_id", tspVehicleModelId);
        return list((Wrapper) ew);
    }

    public QueryWrapper<TspVehicleStdModel> getByStdModeName(String stdModeName, String vehicleModelName) {
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper();
        ew.eq("a.std_mode_name", stdModeName);
        ew.eq("b.vehicle_model_name", vehicleModelName);
        return ew;
    }

    public TspVehicleStdModel getByStdModeNameAndModelId(String stdModeName, Long modelId) {
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper();
        ew.eq("std_mode_name", stdModeName);
        ew.eq("tsp_vehicle_model_id", modelId);
        return (TspVehicleStdModel) getOne((Wrapper) ew);
    }

   /* public TspVehicleStdModelLabelMapDTO getLabelMap() {
        List<String> labelMap = this.tspVehicleStdModeMapper.getLabelMap();
        Map<String, String> map = new HashMap<>();
        String jsonStr = "";
        for (String s : labelMap) {
            map.put(s, s);
            jsonStr = JSON.toJSONString(map);
        }
        TspVehicleStdModelLabelMapDTO dto = new TspVehicleStdModelLabelMapDTO();
        dto.setCount("");
        dto.setDate("");
        dto.setModeNames(jsonStr);
        return dto;
    }

    public Wrapper<TspVehicleStdModel> getAllStdCount(String date) {
        QueryWrapper<TspVehicleStdModel> ew = new QueryWrapper();
        ew.groupBy("DATE_FORMAT( t.create_time, '%Y-%m-%d' )", (Object[]) new String[0]);
        ew.le("t.create_time", date + " 00:00:00");
        ew.ge("t.create_time", date + " 23:59:59");
        return (Wrapper<TspVehicleStdModel>) ew;
    }*/

}
