package com.modern.service;

import com.modern.common.utils.DateUtils;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.domain.TspVehicleStdModelExtra;
import com.modern.model.vo.TspVehicleStdModelExtraAddVO;
import com.modern.repository.TspVehicleStdModelExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspVehicleStdModelExtraService
 * @Date：2024/10/25 10:25
 * @Filename：TspVehicleStdModelExtraService
 */
@Service
public class TspVehicleStdModelExtraService extends TspBaseService{

    @Autowired
    private TspVehicleStdModelExtraRepository tspVehicleStdModelExtraRepository;

    public void add(TspVehicleStdModelExtraAddVO extraAddVO) {
        TspVehicleStdModelExtra extra = new TspVehicleStdModelExtra();
        BeanUtils.copyProperties(extraAddVO, extra);
        extra.setCreateBy(SecurityUtils.getUsername());
        extra.setUpdateBy(SecurityUtils.getUsername());
        extra.setCreateTime(DateUtils.getCurrentTime());
        tspVehicleStdModelExtraRepository.save(extra);
    }

    public void edit(TspVehicleStdModelExtraAddVO extraAddVO) {
        TspVehicleStdModelExtra extra = tspVehicleStdModelExtraRepository.getByTspStdModelId(extraAddVO.getTspVehicleStdModelId());
        BeanUtils.copyProperties(extraAddVO, extra);
        extra.setUpdateBy(SecurityUtils.getUsername());
        extra.setUpdateTime(DateUtils.getCurrentTime());
        this.tspVehicleStdModelExtraRepository.updateById(extra);
    }

    public TspVehicleStdModelExtra getByTspStdModelId(Long tspVehicleStdModelId){
        return tspVehicleStdModelExtraRepository.getByTspStdModelId(tspVehicleStdModelId);
    }

}
