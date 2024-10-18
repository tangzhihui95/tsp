package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicleLicense;
import com.modern.mapper.TspVehicleLicenseMapper;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleLicenseRepository
 * @Date：2024/10/16 17:06
 * @Filename：TspVehicleLicenseRepository
 */
@Service
public class TspVehicleLicenseRepository extends ServicePlusImpl<TspVehicleLicenseMapper, TspVehicleLicense, TspVehicleLicense> {

    public TspVehicleLicense getByTspVehicleId(Long tspVehicleId) {
        QueryWrapper<TspVehicleLicense> ew = new QueryWrapper();
        ew.eq("tsp_vehicle_id", tspVehicleId);
        return (TspVehicleLicense)getOne((Wrapper)ew);
    }

    public TspVehicleLicense getByPlateCode(String plateCode) {
        QueryWrapper<TspVehicleLicense> ew = new QueryWrapper();
        ew.eq("plate_code", plateCode);
        return (TspVehicleLicense)getOne((Wrapper)ew);
    }
}
