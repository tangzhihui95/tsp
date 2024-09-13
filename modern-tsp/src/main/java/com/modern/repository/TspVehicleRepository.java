package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicle;
import com.modern.mapper.TspVehicleMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleRepository
 * @Date：2024/9/12 15:43
 * @Filename：TspVehicleRepository
 */
@Service
public class TspVehicleRepository extends ServicePlusImpl<TspVehicleMapper, TspVehicle, TspVehicle> {

    public List<TspVehicle> findByTspEquipmentId(Long tspEquipmentId) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper();
        ew.eq("tsp_equipment_id", tspEquipmentId);
        return list((Wrapper) ew);
    }

    public List<TspVehicle> findInTspEquipmentIds(Long[] tspEquipmentIds) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper();
        ew.in("tsp_equipment_id", Arrays.asList(tspEquipmentIds));
        return list((Wrapper) ew);
    }

}
