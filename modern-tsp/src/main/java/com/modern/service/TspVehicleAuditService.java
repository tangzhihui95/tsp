package com.modern.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.domain.TspVehicleAudit;
import com.modern.repository.TspVehicleAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspVehicleAuditService
 * @Date：2024/10/23 17:52
 * @Filename：TspVehicleAuditService
 */
@Service
public class TspVehicleAuditService extends TspVehicleService{

    @Autowired
    private TspVehicleAuditRepository tspVehicleAuditRepository;

    public TspVehicleAudit getByTspVehicleId(Long tspVehicleId) {
        QueryWrapper<TspVehicleAudit> ew = new QueryWrapper();
        ew.eq("tsp_vehicle_id", tspVehicleId);
        ew.orderByDesc("create_time", new String[0]);
        ew.last("limit 1");
        return tspVehicleAuditRepository.getOne((Wrapper)ew);
    }
}
