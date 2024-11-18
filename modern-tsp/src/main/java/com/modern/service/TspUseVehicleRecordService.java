package com.modern.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.domain.TspUseVehicleRecord;
import com.modern.mapper.TspUseVehicleRecordMapper;
import com.modern.model.dto.TspUseVehicleRecordPageListDTO;
import com.modern.model.vo.TspUseVehicleRecordAddVO;
import com.modern.model.vo.TspUseVehicleRecordPageListVO;
import com.modern.repository.TspUseVehicleRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private TspUseVehicleRecordMapper tspUseVehicleRecordMapper;

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

    public PageInfo<TspUseVehicleRecordPageListDTO> getPageList(TspUseVehicleRecordPageListVO frontQuery) {
        QueryWrapper<TspUseVehicleRecord> ew = new QueryWrapper();
        ew.eq(StringUtils.isNotNull(frontQuery.getTspVehicleId()), "tsp_vehicle_id", frontQuery.getTspVehicleId());
        ew.eq("is_delete", Integer.valueOf(0));
        ew.eq("version", Integer.valueOf(1));
        ew.orderByDesc("update_time", new String[0]);
        Integer count = tspUseVehicleRecordMapper.getCount(ew);
        if (count.intValue() / 10 < frontQuery.getPageNum().intValue() - 1)
            frontQuery.setPageNum(Integer.valueOf(1));
        ArrayList<TspUseVehicleRecordPageListDTO> dtos = new ArrayList<>();
        IPage<TspUseVehicleRecordPageListDTO> page = tspUseVehicleRecordMapper.getUseVehicleRecordPageList(Page.of(frontQuery.getPageNum().intValue(), frontQuery.getPageSize().intValue()), ew);
        for (TspUseVehicleRecordPageListDTO record : page.getRecords()) {
            TspUseVehicleRecordPageListDTO dto = new TspUseVehicleRecordPageListDTO();
            BeanUtils.copyProperties(record, dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());
    }

}
