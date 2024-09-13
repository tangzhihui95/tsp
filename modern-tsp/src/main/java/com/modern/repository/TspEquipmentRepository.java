package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.domain.TspEquipment;
import com.modern.mapper.TspEquipmentMapper;
import com.modern.model.dto.TspEquipmentExcelDTO;
import com.modern.model.vo.TspEquipmentPageListVO;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspEquipmentRepository
 * @Date：2024/9/12 15:01
 * @Filename：TspEquipmentRepository
 */
@Service
public class TspEquipmentRepository extends ServicePlusImpl<TspEquipmentMapper, TspEquipment, TspEquipment> {

    public QueryWrapper<TspEquipment> getPageListEw(TspEquipmentPageListVO vo) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper();
        ew.eq("t.is_delete", Integer.valueOf(0));
        ew.isNotNull((StringUtils.isNotNull(vo.getBindStatus()) && "1".equals(vo.getBindStatus())), "tv.tsp_equipment_id");
        ew.isNull((StringUtils.isNotNull(vo.getBindStatus()) && "0".equals(vo.getBindStatus())), "tv.id");
        ew.eq(StringUtils.isNotNull(vo.getTspEquipmentModelId()), "t.tsp_equipment_model_id", vo
                .getTspEquipmentModelId());
        ew.and(StringUtils.isNotEmpty(vo.getSn()), q1 -> (q1.like("t.sn", vo.getSn())).or().like("t.sim", vo.getSn()));
        ew.eq(StringUtils.isNotNull(vo.getShowScrap()), "t.is_scrap", Integer.valueOf(0));
        ew.orderByDesc("t.create_time", new String[0]);
        return ew;
    }

    public TspEquipment getBySn(String sn) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper();
        ew.eq(!StringUtils.isEmpty(sn), "sn", sn);
        return (TspEquipment) getOne((Wrapper) ew);
    }

    public TspEquipment getByICCID(String iccid) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper();
        ew.eq(!StringUtils.isEmpty(iccid), "icc_id", iccid);
        return (TspEquipment) getOne((Wrapper) ew);
    }

    public TspEquipment getByIMEI(String imei) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper();
        ew.eq(!StringUtils.isEmpty(imei), "imei", imei);
        return (TspEquipment) getOne((Wrapper) ew);
    }

    public TspEquipment getByIMSI(String imsi) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper();
        ew.eq(!StringUtils.isEmpty(imsi), "imsi", imsi);
        return (TspEquipment) getOne((Wrapper) ew);
    }

    public TspEquipment getBySIM(String sim) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper();
        ew.eq(!StringUtils.isEmpty(sim), "sim", sim);
        return (TspEquipment) getOne((Wrapper) ew);
    }

    public TspEquipment getByDTO(TspEquipmentExcelDTO dto) {
        QueryWrapper<TspEquipment> ew = new QueryWrapper();
        ew.eq(!StringUtils.isEmpty(dto.getSn()), "sn", dto.getSn());
        ew.eq(!StringUtils.isEmpty(dto.getIccId()), "icc_id", dto.getIccId());
        ew.eq(!StringUtils.isEmpty(dto.getImsi()), "imsi", dto.getImsi());
        ew.eq(!StringUtils.isEmpty(dto.getSim()), "sim", dto.getSim());
        ew.eq(!StringUtils.isEmpty(dto.getImei()), "imei", dto.getImei());
        return (TspEquipment) getOne((Wrapper) ew);
    }

}
