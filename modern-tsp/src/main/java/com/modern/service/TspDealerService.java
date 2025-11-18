package com.modern.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.BaseModel;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.DateUtils;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.domain.TspDealer;
import com.modern.mapper.TspDealerMapper;
import com.modern.model.dto.TspDealerInfoDTO;
import com.modern.model.dto.TspDealerPageListDTO;
import com.modern.model.vo.TspDealerAddVO;
import com.modern.model.vo.TspDealerPageListVO;
import com.modern.repository.TspDealerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspDealerService
 * @Date：2025/11/5 10:31
 * @Filename：TspDealerService
 */
@Service
public class TspDealerService {
    private static final Logger log = LoggerFactory.getLogger(TspDealerService.class);

    @Resource
    private TspDealerRepository tspDealerRepository;

    @Resource
    private TspDealerMapper tspDealerMapper;

    public PageInfo<TspDealerPageListDTO> list(TspDealerPageListVO vo) {
        Wrapper<TspDealer> ew = tspDealerRepository.PageListEw(vo);
        Page<TspDealer> page = tspDealerRepository.page(Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
        ArrayList<TspDealerPageListDTO> dtos = new ArrayList<>();
        for (TspDealer record : page.getRecords()) {
            TspDealerPageListDTO dto = new TspDealerPageListDTO();
            BeanUtils.copyProperties(record, dto);
            dtos.add(dto);
        }
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getRecords().size());
    }

    public TspDealerInfoDTO get(Long tspDealerId) {
        log.info("{根据经销商id查询经销商详情-----------------}", tspDealerId);
        TspDealer tspDealer = tspDealerRepository.getById(tspDealerId);
        if (Objects.isNull(tspDealer))
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        TspDealerInfoDTO dto = new TspDealerInfoDTO();
        BeanUtils.copyProperties(tspDealer, dto);
        Map<String, String> dealerLngLat = new HashMap<>();
        dealerLngLat.put("lat", tspDealer.getDealerLat());
        dealerLngLat.put("lng", tspDealer.getDealerLng());
        dto.setDealerLngLat(dealerLngLat);
        return dto;
    }

    public JsonResult add(TspDealerAddVO vo) {
        if (tspDealerRepository.getByDealerName(vo.getDealerName()) != null)
            ErrorEnum.TSP_DEALER_NAME_REPEAT_ERROR.throwErr();
        if (tspDealerRepository.getByCode(vo.getDealerCode()) != null)
            ErrorEnum.TSP_DEALER_CODE_REPEAT_ERROR.throwErr();
        TspDealer tspDealer = new TspDealer();
        BeanUtils.copyProperties(vo, tspDealer);
        tspDealer.setIsDelete(Integer.valueOf(0));
        tspDealer.setCreateBy(SecurityUtils.getUsername());
        tspDealer.setCreateTime(DateUtils.getCurrentTime());
        return JsonResult.getResult(tspDealerRepository.save(tspDealer));
    }

    public JsonResult edit(TspDealerAddVO vo) {
        TspDealer tspDealer = tspDealerRepository.getById(vo.getTspDealerId());
        if (Objects.isNull(tspDealer))
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        BeanUtils.copyProperties(vo, tspDealer);
        tspDealer.setUpdateBy(SecurityUtils.getUsername());
        tspDealer.setUpdateTime(DateUtils.getCurrentTime());
        return JsonResult.getResult(tspDealerRepository.updateById(tspDealer));
    }

    public JsonResult delete(Long tspDealerId) {
        TspDealer tspDealer = tspDealerRepository.getById(tspDealerId);
        if (Objects.isNull(tspDealer))
            ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        return JsonResult.getResult(tspDealerMapper.deleteById(tspDealerId));
    }

    public JsonResult batchDelete(Long[] tspDealerIds) {
        for (Long dealerId : tspDealerIds) {
            TspDealer tspDealer = tspDealerRepository.getById(dealerId);
            if (Objects.isNull(tspDealer))
                ErrorEnum.TSP_DEALER_NULL_ERROR.throwErr();
        }
        List<TspDealer> tspDealers = (tspDealerRepository.lambdaQuery().eq(BaseModel::getIsDelete, Integer.valueOf(0))).list();
        for (Long tspDealerId : tspDealerIds) {
            if (CollectionUtil.isNotEmpty(tspDealers))
                tspDealerRepository.removeById(tspDealerId);
        }
        return JsonResult.getResult(true);
    }

}
