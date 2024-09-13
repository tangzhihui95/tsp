package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.domain.TspEquipmentModel;
import com.modern.mapper.TspEquipmentModelMapper;
import com.modern.model.vo.FrontQuery;
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

    public TspEquipmentModel getByModelName(String modelName) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper();
        ew.eq("model_name", modelName);
        return (TspEquipmentModel) getOne((Wrapper) ew);
    }

    public TspEquipmentModel getByModelNameNotId(String modelName, Long tspEquipmentModelId) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper();
        ew.eq("model_name", modelName);
        ew.notIn("id", new Object[]{tspEquipmentModelId});
        return (TspEquipmentModel) getOne((Wrapper) ew);
    }

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

    public QueryWrapper<TspEquipmentModel> getPageList(FrontQuery vo) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper();
        ew.and(StringUtils.isNotEmpty(vo.getSearch()), q -> q.like("name", vo.getSearch())).or().like("extra_type", vo.getSearch());
        ew.eq((vo.getTspEquipmentModelId() != null), "t.id", vo.getTspEquipmentModelId());
        ew.eq("t.is_delete", "0");
        ew.orderByDesc("a.update_time", new String[0]);
        return ew;
    }

    public TspEquipmentModel getByModelNameAndType(String modelName, Long tspEquipmentTypeId) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper();
        ew.eq("model_name", modelName);
        ew.eq("tsp_equipment_type_id", tspEquipmentTypeId);
        return (TspEquipmentModel) getOne((Wrapper) ew);
    }

    public List<TspEquipmentModel> findByTspEquipmentIdLikeName(Long tspEquipmentId, String modelName) {
        QueryWrapper<TspEquipmentModel> ew = new QueryWrapper();
        ew.eq("id", tspEquipmentId);
        ew.like(!StringUtils.isEmpty(modelName), "model_name", modelName);
        return list((Wrapper) ew);
    }

    public TspEquipmentModel getByIdContainsDelete(Long tspEquipmentId) {
        return ((TspEquipmentModelMapper) this.baseMapper).getByIdContainsDelete(tspEquipmentId);
    }

}
