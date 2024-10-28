package com.modern.service;

import com.modern.common.utils.DateUtils;
import com.modern.common.utils.SecurityUtils;
import com.modern.domain.TspVehicleLicenseRecord;
import com.modern.model.vo.TspVehicleLicenseRecordAddVO;
import com.modern.repository.TspVehicleLicenseRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspVehicleLicenseRecordService
 * @Date：2024/10/18 13:32
 * @Filename：TspVehicleLicenseRecordService
 */
@Service
public class TspVehicleLicenseRecordService extends TspBaseService{

    @Autowired
    private TspVehicleLicenseRecordRepository tspVehicleLicenseRecordRepository;

    public void add(TspVehicleLicenseRecordAddVO recordAddVO) {
        TspVehicleLicenseRecord upPlateRecord = new TspVehicleLicenseRecord();
        BeanUtils.copyProperties(recordAddVO, upPlateRecord);
        upPlateRecord.setCreateBy(SecurityUtils.getUsername());
        upPlateRecord.setUpdateBy(SecurityUtils.getUsername());
        upPlateRecord.setVersion(Integer.valueOf(1));
        upPlateRecord.setUpPlaceDate(LocalDate.now());
        upPlateRecord.setCreateTime(DateUtils.getCurrentTime());
        upPlateRecord.setPlateCode(recordAddVO.getPlateCodeName() + recordAddVO.getPlateCode());
        tspVehicleLicenseRecordRepository.save(upPlateRecord);
    }
}
