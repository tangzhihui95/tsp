package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicleEquipment;
import com.modern.mapper.TspVehicleEquipmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleEquipmentRepository
 * @Date：2024/10/18 13:21
 * @Filename：TspVehicleEquipmentRepository
 */
@Service
public class TspVehicleEquipmentRepository extends ServicePlusImpl<TspVehicleEquipmentMapper, TspVehicleEquipment, TspVehicleEquipment> {
    public List<TspVehicleEquipment> getByVehicleId(Long id) {
        QueryWrapper<TspVehicleEquipment> ew = new QueryWrapper();
        ew.eq("tsp_vehicle_id", id);
        ew.orderByDesc("create_time", new String[0]);
        return list((Wrapper)ew);
    }
}
