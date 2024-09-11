package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.common.core.domain.BaseModel;
import com.modern.domain.FrontQuery;
import com.modern.domain.TspEquipmentModel;
import com.modern.domain.TspEquipmentType;
import com.modern.mapper.TspEquipmentTypeMapper;
import com.modern.model.dto.TspEquipmentTypeSelectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspEquipmentTypeRepository
 * @Date：2024/8/28 16:42
 * @Filename：TspEquipmentTypeRepository
 */
@Service
public class TspEquipmentTypeRepository extends ServicePlusImpl<TspEquipmentTypeMapper, TspEquipmentType, TspEquipmentType> {

    private final TspEquipmentModelRepository tspEquipmentModelRepository;

    @Autowired
    public TspEquipmentTypeRepository(TspEquipmentModelRepository tspEquipmentModelRepository) {
        this.tspEquipmentModelRepository = tspEquipmentModelRepository;
    }

    public TspEquipmentType getByName(String tspEquipmentName) {
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper();
        ew.eq("name", tspEquipmentName);
        return (TspEquipmentType) getOne((Wrapper) ew);
    }

    public TspEquipmentType getByNameAndExtraType(String tspEquipmentName, String extraType) {
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper();
        ew.eq("name", tspEquipmentName);
        ew.eq("extra_type", extraType);
        return (TspEquipmentType) getOne((Wrapper) ew);
    }

    @Override
    public boolean save(TspEquipmentType entity) {
        return super.save(entity);
    }

    public TspEquipmentType getByName(String name, @NotNull(message = "设备扩展信息不能为空") String extraType) {
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper();
        ew.eq("name", name);
        ew.eq("extra_type", extraType);
        return (TspEquipmentType)
                getOne((Wrapper) ew);
    }

    public List<TspEquipmentTypeSelectDTO> selectList(FrontQuery vo) {
        ArrayList<TspEquipmentTypeSelectDTO> dtos = new ArrayList<>();
        List<TspEquipmentType> types = ((LambdaQueryChainWrapper) lambdaQuery().eq(BaseModel::getIsDelete, Integer.valueOf(0))).list();
        TspEquipmentTypeSelectDTO selectDTO = new TspEquipmentTypeSelectDTO();
        if (vo.getNeedAll() != null && vo.getNeedAll().intValue() == 1) {
            selectDTO.setLabel("全部");
            selectDTO.setValue(new Long(0L));
            dtos.add(selectDTO);
        }
        for (TspEquipmentType type : types) {
            TspEquipmentTypeSelectDTO dto = new TspEquipmentTypeSelectDTO();
            dto.setLabel(type.getName());
            dto.setValue(type.getId());
            dto.setChildren(supplierChildren(type.getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    public List<TspEquipmentTypeSelectDTO> supplierChildren(Long tspEquipmentTypeId) {
        List<TspEquipmentModel> models = this.tspEquipmentModelRepository.findByTspEquipmentTypeId(tspEquipmentTypeId);
        ArrayList<TspEquipmentTypeSelectDTO> suppliersDTOS = new ArrayList<>();
        for (TspEquipmentModel model : models) {
            TspEquipmentTypeSelectDTO dto = new TspEquipmentTypeSelectDTO();
            ArrayList<TspEquipmentTypeSelectDTO> modelDTOS = new ArrayList<>();
            TspEquipmentTypeSelectDTO modelDTO = new TspEquipmentTypeSelectDTO();
            modelDTO.setLabel(model.getModelName());
            modelDTO.setValue(model.getId());
            modelDTOS.add(modelDTO);
            dto.setLabel(model.getSuppliers());
            dto.setValue(model.getId());
            dto.setChildren(modelDTOS);
            suppliersDTOS.add(dto);
        }
        return suppliersDTOS;
    }

}
