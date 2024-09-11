package com.modern.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.page.PageInfo;
import com.modern.domain.FrontQuery;
import com.modern.domain.TspEquipmentModel;
import com.modern.mapper.TspEquipmentModelMapper;
import com.modern.model.dto.TspEquipmentModelPageListDTO;
import com.modern.repository.TspEquipmentModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspEquipmentModelService
 * @Date：2024/9/11 15:29
 * @Filename：TspEquipmentModelService
 */
@Service
public class TspEquipmentModelService extends TspBaseService {

    @Autowired
    private TspEquipmentModelRepository tspEquipmentModelRepository;

    @Autowired
    private TspEquipmentModelMapper tspEquipmentModelMapper;


    public PageInfo<TspEquipmentModelPageListDTO> getPageList(FrontQuery vo) {
        QueryWrapper<TspEquipmentModel> ew = this.tspEquipmentModelRepository.getPageList(vo);
        IPage<TspEquipmentModelPageListDTO> pageList = this.tspEquipmentModelMapper.getPageList(
                Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
        return PageInfo.of(pageList);
    }
    
}
