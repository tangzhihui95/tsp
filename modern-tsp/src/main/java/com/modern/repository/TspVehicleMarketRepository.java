package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicleMarket;
import com.modern.mapper.TspVehicleMarketMapper;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleMarketRepository
 * @Date：2024/10/22 17:56
 * @Filename：TspVehicleMarketRepository
 */
@Service
public class TspVehicleMarketRepository extends ServicePlusImpl<TspVehicleMarketMapper, TspVehicleMarket,TspVehicleMarket> {
    public TspVehicleMarket getByTspVehicleId(Long tspVehicleId) {
        QueryWrapper<TspVehicleMarket> ew = new QueryWrapper();
        ew.eq("tsp_vehicle_id", tspVehicleId);
        return getOne((Wrapper)ew);
    }
}
