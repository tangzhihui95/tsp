package com.modern.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.StringUtils;
import com.modern.domain.FrontQuery;
import com.modern.domain.TspEquipmentModel;
import com.modern.domain.TspEquipmentType;
import com.modern.mapper.TspEquipmentModelMapperNew;
import com.modern.mapper.TspEquipmentTypeMapper;
import com.modern.model.dto.TspEquipmentModelPageListDTO;
import com.modern.model.dto.TspEquipmentTypePageListDTO;
import com.modern.repository.TspEquipmentModelRepository;
import com.modern.repository.TspEquipmentTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：EquipmentTypeService
 * @Date：2024/8/28 16:36
 * @Filename：EquipmentTypeService
 */
@Service
public class EquipmentTypeService extends TspBaseService {

    @Autowired
    public TspEquipmentModelRepository tspEquipmentModelRepository;

    @Autowired
    public TspEquipmentTypeRepository tspEquipmentTypeRepository;


    @Autowired
    public TspEquipmentTypeMapper tspEquipmentTypeMapper;
    @Autowired
    public TspEquipmentModelMapperNew tspEquipmentModelMapperNew;

    /**
     * 设备分类-列表查询
     *
     * @param vo
     * @return
     */
    public PageInfo<TspEquipmentTypePageListDTO> getList(FrontQuery vo) {

        if (Objects.nonNull(vo.getTspEquipmentModelId())) {
            TspEquipmentModel tspEquipmentModel = tspEquipmentModelMapperNew.getById(vo.getTspEquipmentModelId());
        }
        return null;
    }

    /**
     * 设备分类-列表查询
     *
     * @param vo
     * @return
     */
    public PageInfo<TspEquipmentTypePageListDTO> getPageList(FrontQuery vo) {
        List<TspEquipmentTypePageListDTO> dtos = new ArrayList<>();
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper();
        if (Objects.nonNull(vo.getTspEquipmentModelId())) {
            TspEquipmentModel tspEquipmentModel = tspEquipmentModelRepository.getById(vo.getTspEquipmentModelId());
            ew.eq("id", tspEquipmentModel.getTspEquipmentTypeId());
        } else if (Objects.nonNull(vo.getTspEquipmentTypeId())) {
            ew.eq("id", vo.getTspEquipmentTypeId());
        }
        ew.eq("is_delete", Integer.valueOf(0));
        ew.and(StringUtils.isNotEmpty(vo.getSearch()), q ->
                ((QueryWrapper) ((QueryWrapper) q.like("name", vo.getSearch())).or()).like("extra_type", vo.getSearch()));
        ew.orderByDesc("create_time", new String[0]);
        Page<TspEquipmentType> page = (Page<TspEquipmentType>) this.tspEquipmentTypeRepository.page((IPage) new Page(vo.getPageNum().intValue(), vo.getPageSize().intValue()), (Wrapper) ew);
        for (TspEquipmentType type : page.getRecords()) {
            List<TspEquipmentModel> modelList;
            TspEquipmentTypePageListDTO dto = new TspEquipmentTypePageListDTO();
            BeanUtils.copyProperties(type, dto);
            int count = this.tspEquipmentTypeMapper.countByTspEquipmentTypeId(type.getId());
            if (vo.getTspEquipmentModelId() != null) {
                modelList = this.tspEquipmentModelRepository.findByTspModelId(type.getId(), vo.getTspEquipmentModelId());
            } else {
                modelList = this.tspEquipmentModelRepository.findByTspEquipmentTypeId(type.getId());
            }
            List<TspEquipmentModelPageListDTO> modelDTOs = new ArrayList<>();
            for (TspEquipmentModel tspEquipmentModel : modelList) {
                int modelCount = this.tspEquipmentTypeMapper.countByTspEquipmentModelId(tspEquipmentModel.getId());
                TspEquipmentModelPageListDTO modelDTO = new TspEquipmentModelPageListDTO();
                BeanUtils.copyProperties(tspEquipmentModel, modelDTO);
                modelDTO.setCount(Integer.valueOf(modelCount));
                modelDTOs.add(modelDTO);
            }
            if (CollectionUtils.isNotEmpty(modelList)) {
                dto.setChildren(modelDTOs);
                dto.setCount(Integer.valueOf(count));
            }
            dtos.add(dto);
        }
        return PageInfo.of(dtos, vo.getPageNum().intValue(), vo.getPageSize().intValue(), page.getTotal());
    }


}
