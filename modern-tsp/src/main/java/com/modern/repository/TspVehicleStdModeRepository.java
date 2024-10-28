package com.modern.repository;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicleStdModel;
import com.modern.mapper.TspVehicleStdModeMapper;
import com.modern.model.dto.TspVehicleStdModelLabelMapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private TspVehicleStdModeMapper tspVehicleStdModeMapper;
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

    public TspVehicleStdModelLabelMapDTO getLabelMap() {
        List<String> labelMap = tspVehicleStdModeMapper.getLabelMap();
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


}
