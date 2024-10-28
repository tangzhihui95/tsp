package com.modern.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.domain.TspUseVehicleRecord;
import com.modern.model.vo.TspUseVehicleRecordAddVO;
import com.modern.repository.TspUseVehicleRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspUseVehicleRecordService
 * @Date：2024/10/23 15:13
 * @Filename：TspUseVehicleRecordService
 */
@Service
public class TspUseVehicleRecordService extends TspVehicleService {

    @Autowired
    private TspUseVehicleRecordRepository tspUseVehicleRecordRepository;

    public void add(TspUseVehicleRecordAddVO recordAddVO) {
        List<TspUseVehicleRecord> mobiles = tspUseVehicleRecordRepository.findByMobile(recordAddVO.getMobile());
        if (!CollectionUtils.isEmpty(mobiles)) {
            mobiles.stream().filter(a -> (a.getVersion().intValue() == 1)).forEach(b -> b.setVersion(Integer.valueOf(0)));
            tspUseVehicleRecordRepository.updateBatchById(mobiles);
        }
        TspUseVehicleRecord vehicleRecord = new TspUseVehicleRecord();
        vehicleRecord.setCreateBy(SecurityUtils.getUsername());
        vehicleRecord.setUpdateBy(SecurityUtils.getUsername());
        vehicleRecord.setVersion(Integer.valueOf(1));
        BeanUtils.copyProperties(recordAddVO, vehicleRecord);
        tspUseVehicleRecordRepository.save(vehicleRecord);
    }
}
