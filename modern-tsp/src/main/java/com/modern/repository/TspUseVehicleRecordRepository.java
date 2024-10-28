package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspUseVehicleRecord;
import com.modern.mapper.TspUseVehicleRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspUseVehicleRecordRepository
 * @Date：2024/10/23 15:20
 * @Filename：TspUseVehicleRecordRepository
 */
@Service
public class TspUseVehicleRecordRepository extends ServicePlusImpl<TspUseVehicleRecordMapper, TspUseVehicleRecord,TspUseVehicleRecord> {
    public List<TspUseVehicleRecord> findByMobile(String mobile) {
        QueryWrapper<TspUseVehicleRecord> ew = new QueryWrapper();
        ew.eq("mobile", mobile);
        return list((Wrapper)ew);
    }
}
