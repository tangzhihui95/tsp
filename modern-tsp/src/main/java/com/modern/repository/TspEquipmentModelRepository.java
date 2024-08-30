package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.domain.TspEquipmentModel;
import com.modern.mapper.TspEquipmentModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspEquipmentModelRepository
 * @Date：2024/8/29 10:25
 * @Filename：TspEquipmentModelRepository
 */
@Service
public class TspEquipmentModelRepository extends ServicePlusImpl<TspEquipmentModelMapper, TspEquipmentModel, TspEquipmentModel> {


    public List<TspEquipmentModel> findByTspModelId(Long tspEquipmentTypeId, Long tspEquipmentModelId) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper();
        ew.eq("tsp_equipment_type_id", tspEquipmentTypeId);
        ew.eq("id", tspEquipmentModelId);
        ew.eq("is_delete", Integer.valueOf(0));
        return list((Wrapper) ew);
    }

    public List<TspEquipmentModel> findByTspEquipmentTypeId(Long id) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper();
        ew.eq("tsp_equipment_type_id", id);
        ew.eq("is_delete", Integer.valueOf(0));
        return list((Wrapper) ew);
    }

    public int countByTspEquipmentModelId(Long id) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper();
        ew.eq("tsp_equipment_type_id", id);
        return (int) count((Wrapper) ew);
    }

}