package com.modern.exinterface.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.entity.VehicleIntegrate;
import com.modern.exinterface.mapper.VehicleIntegrateMapper;
import com.modern.exinterface.vo.VehicleSearchVO;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：VehicleIntegrateRepository
 * @Date：2025/6/13 10:36
 * @Filename：VehicleIntegrateRepository
 */
@Service
public class VehicleIntegrateRepository extends ServicePlusImpl<VehicleIntegrateMapper, VehicleIntegrate, VehicleIntegrate> {
    public Wrapper<VehicleIntegrate> getPageListEw(VehicleSearchVO vo) {
        QueryWrapper<VehicleIntegrate> ew = new QueryWrapper();
        ew.eq("t.isDelete", Integer.valueOf(0));
        ((QueryWrapper) ew.and(StringUtils.isNotEmpty(vo.getSearch()), q -> ((QueryWrapper) ((QueryWrapper) ((QueryWrapper) ((QueryWrapper) q.like("t.vin", vo.getSearch())).or()).like("b.sn", vo.getSearch())).or()).like("c.plate_code", vo.getSearch())))
                .between((Objects.nonNull(vo.getCollectStartTime()) && Objects.nonNull(vo.getCollectEndTime())), "t.collect_time", vo
                        .getCollectStartTime(), vo.getCollectEndTime());
        ew.orderByDesc("t.collect_time", new String[0]);
        return ew;
    }
}
