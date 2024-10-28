package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicleStdModelExtra;
import com.modern.mapper.TspVehicleStdModelExtraMapper;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleStdModelExtraRepository
 * @Date：2024/10/25 10:32
 * @Filename：TspVehicleStdModelExtraRepository
 */
@Service
public class TspVehicleStdModelExtraRepository extends ServicePlusImpl<TspVehicleStdModelExtraMapper, TspVehicleStdModelExtra,TspVehicleStdModelExtra> {

    public TspVehicleStdModelExtra getByTspStdModelId(Long tspVehicleStdModelId) {
        QueryWrapper<TspVehicleStdModelExtra> ew = new QueryWrapper();
        ew.eq("tsp_vehicle_std_model_id", tspVehicleStdModelId);
        return getOne((Wrapper)ew);
    }
}
