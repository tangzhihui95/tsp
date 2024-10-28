package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicleOther;
import com.modern.mapper.TspVehicleOtherMapper;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleOtherRepository
 * @Date：2024/10/23 13:50
 * @Filename：TspVehicleOtherRepository
 */
@Service
public class TspVehicleOtherRepository extends ServicePlusImpl<TspVehicleOtherMapper, TspVehicleOther,TspVehicleOther> {
    public TspVehicleOther getByTspVehicleId(Long tspVehicleId) {
        QueryWrapper<TspVehicleOther> ew = new QueryWrapper();
        ew.eq("tsp_vehicle_id", tspVehicleId);
        return getOne((Wrapper)ew);
    }
}
