package com.modern.repository;

import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspUserVehicle;
import com.modern.mapper.TspUserVehicleMapper;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspUserVehicleRepository
 * @Date：2024/10/24 16:17
 * @Filename：TspUserVehicleRepository
 */
@Service
public class TspUserVehicleRepository extends ServicePlusImpl<TspUserVehicleMapper, TspUserVehicle,TspUserVehicle> {
}
