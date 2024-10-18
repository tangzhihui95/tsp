package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspVehicleModel;
import com.modern.model.dto.TspVehicleStdModelExListDTO;
import com.modern.repository.TspVehicleStdModeRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sun.tools.attach.VirtualMachine.list;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspVehicleModelMapper
 * @Date：2024/10/14 16:09
 * @Filename：TspVehicleModelMapper
 */
public interface TspVehicleModelMapper extends BaseMapperPlus<TspVehicleModel> {
    @Select({"SELECT t.std_mode_name,t.data_key,t.notice_batch,t.notice_model,t.id as 'tspStdModelId', a.vehicle_model_name FROM tsp_vehicle_std_model t LEFT JOIN tsp_vehicle_model a ON a.id = t.tsp_vehicle_model_id ${ew.customSqlSegment}"})
    IPage<TspVehicleStdModelExListDTO> getPageList(Page<Object> paramPage, @Param("ew") QueryWrapper<TspVehicleModel> paramQueryWrapper);
}
