package com.modern.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modern.domain.TspCarRoleRelation;
import com.modern.model.vo.VehicleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.mapper
 * @Project：tsp
 * @name：TspCarRoleRelationMapper
 * @Date：2024/10/16 15:18
 * @Filename：TspCarRoleRelationMapper
 */
public interface TspCarRoleRelationMapper extends BaseMapper<TspCarRoleRelation> {
    List<VehicleVo> selectCarInfo(@Param("vo") VehicleVo paramVehicleVo);

    void deleteByRoleId(@Param("roleId") Long paramLong);

    List<TspCarRoleRelation> selectCarInfoByRoleIds(@Param("ids") List<Long> paramList);

    //List<CarRoleVo> queryCarInfoByRoleIds(@Param("roleIds") Set<Long> paramSet);

    List<VehicleVo> queryCarInfoByRoleId(@Param("roleId") Long paramLong);
}
