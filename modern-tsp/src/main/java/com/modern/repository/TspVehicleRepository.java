package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.core.ServicePlusImpl;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.StringUtils;
import com.modern.domain.TspVehicle;
import com.modern.enums.TspVehicleEnumCertificationState;
import com.modern.enums.TspVehicleSendEnum;
import com.modern.enums.TspVehicleStateEnum;
import com.modern.mapper.TspVehicleMapper;
import com.modern.model.vo.TspVehicleAddVO;
import com.modern.model.vo.TspVehiclePageListVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspVehicleRepository
 * @Date：2024/9/12 15:43
 * @Filename：TspVehicleRepository
 */
@Service
public class TspVehicleRepository extends ServicePlusImpl<TspVehicleMapper, TspVehicle, TspVehicle> {


    public List<TspVehicle> findByTspEquipmentId(Long tspEquipmentId) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper();
        ew.eq("tsp_equipment_id", tspEquipmentId);
        return list((Wrapper) ew);
    }

    public List<TspVehicle> findInTspEquipmentIds(Long[] tspEquipmentIds) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper();
        ew.in("tsp_equipment_id", Arrays.asList(tspEquipmentIds));
        return list((Wrapper) ew);
    }

    public Integer countByTspVehicleStdModelId(Long tspVehicleStdModelId) {
        LambdaQueryWrapper<TspVehicle> ew = new LambdaQueryWrapper();
        ew.eq(TspVehicle::getTspVehicleStdModelId, tspVehicleStdModelId);
        return Integer.valueOf((int) count((Wrapper) ew));
    }

    public QueryWrapper<TspVehicle> getPageListEw(TspVehiclePageListVO vo) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper();
        ew.in(CollectionUtils.isNotEmpty(vo.getCarIds()), "t.id", vo.getCarIds());
        ew.isNotNull((StringUtils.isNotNull(vo.getBindStatus()) && "1".equals(vo.getBindStatus())), "t.tsp_equipment_id");
        ew.isNull((StringUtils.isNotNull(vo.getBindStatus()) && "0".equals(vo.getBindStatus())), "t.tsp_equipment_id");
        ew.eq("t.is_delete", Integer.valueOf(0));
        ew.eq((StringUtils.isNotNull(vo.getState()) && !vo.getState().equals(TspVehicleStateEnum.ALL)), "t.state", vo
                .getState());
        ew.eq((StringUtils.isNotNull(vo.getCertificationState()) && !vo.getCertificationState().equals(TspVehicleEnumCertificationState.ALL)), "t.certification_state", vo
                .getCertificationState());
        ew.and(StringUtils.isNotEmpty(vo.getSearch()), q1 -> ((QueryWrapper) ((QueryWrapper) ((QueryWrapper) ((QueryWrapper)
                ((QueryWrapper) ((QueryWrapper) ((QueryWrapper) ((QueryWrapper) q1.like("a.std_mode_name", vo.getSearch()))
                        .or()).like("t.vin", vo.getSearch())).or()).like("e.plate_code", vo.getSearch()))
                .or()).like("c.sn", vo.getSearch())).or()).like("c.sim", vo.getSearch()));
        ew.eq(StringUtils.isNotNull(vo.getTspVehicleStdModelId()), "t.tsp_vehicle_std_model_id", vo
                .getTspVehicleStdModelId());
        ew.eq((!StringUtils.isEmpty(vo.getMobile()) && !"全部".equals(vo.getMobile())), "b.mobile", vo
                .getMobile());
        ew.eq((StringUtils.isNotNull(vo.getSendStatus()) && !vo.getSendStatus().equals(TspVehicleSendEnum.ALL)), "t.send_status", vo
                .getSendStatus());
        ew.orderByDesc("t.create_time", new String[0]);
        return ew;
    }

    public TspVehicle getByVin(String vin) {
        QueryWrapper<TspVehicle> ew = new QueryWrapper();
        ew.eq("vin", vin);
        return (TspVehicle) getOne((Wrapper) ew);
    }

    public void isProgressCheck(TspVehicleAddVO vo) throws ServiceException {
        if (vo.getProgress().intValue() == 2) {
            if (StringUtils.isEmpty(vo.getPurchaser()))
                throw new ServiceException("购买方名称不能为空");
            if (StringUtils.isEmpty(vo.getVehicleIdCard()))
                throw new ServiceException("身份证号不能为空");
            if (vo.getPriceTax() == null)
                throw new ServiceException("价税合计不能为空");
            if (vo.getOperateDate() == null)
                throw new ServiceException("开票日期不能为空");
        }
    }

    public void isUserCheck(TspVehicleAddVO vo) throws ServiceException {
        if (vo.getProgress().intValue() == 4) {
            if (StringUtils.isEmpty(vo.getMobile()))
                throw new ServiceException("车主手机号不能为空");
            if (StringUtils.isEmpty(vo.getRealName()))
                throw new ServiceException("车主姓名不能为空");
            if (StringUtils.isEmpty(vo.getIdCard()))
                throw new ServiceException("身份证号不能为空");
        }
    }


}
