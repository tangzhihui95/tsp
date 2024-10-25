package com.modern.repository;

import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspVehicleAudit;
import com.modern.mapper.TspVehicleAuditMapper;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleAuditRepository
 * @Date：2024/10/23 15:07
 * @Filename：TspVehicleAuditRepository
 */
@Service
public class TspVehicleAuditRepository extends ServicePlusImpl<TspVehicleAuditMapper, TspVehicleAudit,TspVehicleAudit> {
}
