package com.modern.exinterface.Service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.page.PageInfo;
import com.modern.entity.VehicleIntegrate;
import com.modern.exinterface.mapper.VehicleIntegrateMapper;
import com.modern.exinterface.repository.VehicleIntegrateRepository;
import com.modern.exinterface.vo.VehicleSearchVO;
import com.modern.exinterface.dto.BaseVehicleDataDTO;
import com.modern.exinterface.dto.VehicleIntegrateParsedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：tzh
 * @Package：Service
 * @Project：tsp
 * @name：VehicleIntegrateService
 * @Date：2025/6/13 9:43
 * @Filename：VehicleIntegrateService
 */
@Service
public class VehicleIntegrateService extends VehicleIntegrateRepository {

    @Autowired
    private VehicleIntegrateRepository vehicleIntegrateRepository;

    @Resource
    private VehicleIntegrateMapper vehicleIntegrateMappe;

    public PageInfo<VehicleIntegrateParsedDTO> getPageList(VehicleSearchVO vo) {
        Wrapper<VehicleIntegrate> ew = vehicleIntegrateRepository.getPageListEw(vo);
        IPage<VehicleIntegrate> pageList = vehicleIntegrateMappe.getPageList(Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
        HashMap<List<Long>, ArrayList<VehicleIntegrateParsedDTO>> hashMap = new HashMap<>();
        ArrayList<VehicleIntegrateParsedDTO> dtoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(pageList.getRecords()))
            pageList.getRecords().forEach(item -> {
                VehicleIntegrateParsedDTO dto = VehicleIntegrateParsedDTO.create(item);
                dtoList.add(dto);
            });
        List<Long> pageId = dtoList.stream().map(BaseVehicleDataDTO::getId).collect(Collectors.toList());
        hashMap.put(pageId, dtoList);
        return PageInfo.of(hashMap.get(pageId), pageList.getCurrent(), pageList.getSize(), pageList.getRecords().size());
    }


}
