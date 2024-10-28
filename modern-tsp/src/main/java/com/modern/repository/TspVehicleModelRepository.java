package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.domain.TspVehicleModel;
import com.modern.domain.TspVehicleStdModel;
import com.modern.mapper.TspVehicleModelMapper;
import com.modern.model.dto.TspVehicleModelSelectDTO;
import com.modern.model.dto.TspVehicleStdModelExListDTO;
import com.modern.model.vo.TspVehicleModelPageListVO;
import com.modern.model.vo.TspVehiclePageListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleModelRepository
 * @Date：2024/10/14 16:08
 * @Filename：TspVehicleModelRepository
 */
@Service
public class TspVehicleModelRepository extends ServicePlusImpl<TspVehicleModelMapper, TspVehicleModel, TspVehicleModel> {
    private final TspVehicleStdModeRepository tspVehicleStdModeRepository;

    @Autowired
    public TspVehicleModelRepository(TspVehicleStdModeRepository tspVehicleStdModeRepository) {
        this.tspVehicleStdModeRepository = tspVehicleStdModeRepository;
    }

    public List<TspVehicleModel> findByTspVehicleTypeId(Long tspVehicleTypeId) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper();
        ew.eq("tsp_vehicle_type_id", tspVehicleTypeId);
        return list((Wrapper) ew);
    }

    public TspVehicleModel getByVehicleModelName(String vehicleModelName) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper();
        ew.eq("vehicle_model_name", vehicleModelName);
        return (TspVehicleModel) getOne((Wrapper) ew);
    }

    public List<TspVehicleModel> selectList(Long tspVehicleModelId) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper();
        ew.eq(StringUtils.isNotNull(tspVehicleModelId), "id", tspVehicleModelId);
        return list((Wrapper) ew);
    }

    public List<TspVehicleModel> getByParentModelId(Long id) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper();
        ew.eq("parent_model_id", id);
        return list((Wrapper) ew);
    }

    public QueryWrapper<TspVehicleModel> getStdModelList(TspVehicleModelPageListVO vo) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper();
        ew.in(CollectionUtils.isNotEmpty(vo.getIds()), "a.id", vo.getIds());
        ew.eq((vo.getTspVehicleStdModelId() != null), "t.id", vo.getTspVehicleStdModelId());
        ew.like(StringUtils.isNotEmpty(vo.getVehicleModelName()), "a.vehicle_model_name", vo.getVehicleModelName());
        ew.orderByDesc("t.create_time", new String[0]);
        return ew;
    }

    public List<TspVehicleModelSelectDTO> selectChildrenList(TspVehiclePageListVO vo) {
        ArrayList<TspVehicleModelSelectDTO> dtos = new ArrayList<>();
        TspVehicleModelSelectDTO selectDTO = new TspVehicleModelSelectDTO();
        if (vo.getNeedAll() != null && vo.getNeedAll().intValue() == 1) {
            selectDTO.setLabel("");
            selectDTO.setValue(new Long(0L));
            dtos.add(selectDTO);
        }
        List<TspVehicleModel> models = list();
        for (TspVehicleModel model : models) {
            TspVehicleModelSelectDTO dto = new TspVehicleModelSelectDTO();
            dto.setLabel(model.getVehicleModelName());
            dto.setValue(model.getId());
            dto.setChildren(supplierChildren(model.getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    private List<TspVehicleModelSelectDTO> supplierChildren(Long tspVehicleModelId) {
        List<TspVehicleStdModel> models = tspVehicleStdModeRepository.getByTspVehicleModelId(tspVehicleModelId);
        ArrayList<TspVehicleModelSelectDTO> suppliersDTOS = new ArrayList<>();
        for (TspVehicleStdModel model : models) {
            TspVehicleModelSelectDTO dto = new TspVehicleModelSelectDTO();
            dto.setLabel(model.getStdModeName());
            dto.setValue(model.getId());
            suppliersDTOS.add(dto);
        }
        return suppliersDTOS;
    }

  /*

    public QueryWrapper<TspVehicleModel> getStdModelList(TspVehicleModelPageListVO vo) {
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper();
        ew.in(CollectionUtils.isNotEmpty(vo.getIds()), "a.id", vo.getIds());
        ew.eq((vo.getTspVehicleStdModelId() != null), "t.id", vo.getTspVehicleStdModelId());
        ew.like(StringUtils.isNotEmpty(vo.getVehicleModelName()), "a.vehicle_model_name", vo.getVehicleModelName());
        ew.orderByDesc("t.create_time", (Object[])new String[0]);
        return ew;
    }*/

}
