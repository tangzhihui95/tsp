package com.modern.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.core.BaseMapperPlus;
import com.modern.domain.TspUseVehicleRecord;
import com.modern.model.dto.TspUseVehicleRecordPageListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspUseVehicleRecordMapper
 * @Date：2024/10/23 15:19
 * @Filename：TspUseVehicleRecordMapper
 */
public interface TspUseVehicleRecordMapper extends BaseMapperPlus<TspUseVehicleRecord> {
    @Select({"SELECT COUNT(id) FROM tsp_use_vehicle_record"})
    Integer getCount(@Param("ew") QueryWrapper<TspUseVehicleRecord> paramQueryWrapper);

    @Select({"SELECT mobile,real_name,id_card,update_by,update_time,remark FROM tsp_use_vehicle_record ${ew.customSqlSegment}"})
    IPage<TspUseVehicleRecordPageListDTO> getUseVehicleRecordPageList(Page<TspUseVehicleRecordPageListDTO> paramPage, @Param("ew") QueryWrapper<TspUseVehicleRecord> paramQueryWrapper);

}
