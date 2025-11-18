package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.modern.common.core.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.domain.TspDealer;
import com.modern.mapper.TspDealerMapper;
import com.modern.model.vo.TspDealerPageListVO;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspDealerRepository
 * @Date：2025/11/5 10:35
 * @Filename：TspDealerRepository
 */
@Service
public class TspDealerRepository extends ServicePlusImpl<TspDealerMapper, TspDealer,TspDealer> {

    public Wrapper<TspDealer> PageListEw(TspDealerPageListVO vo) {
        QueryWrapper<TspDealer> ew = new QueryWrapper();
        ew.and(StringUtils.isNotEmpty(vo.getSearch()), q -> q.like("dealer_name", vo.getSearch()));
        ew.and(StringUtils.isNotEmpty(vo.getDealerAddress()), q -> q.like("dealer_address", vo.getDealerAddress()));
        ew.eq("is_delete", Integer.valueOf(0));
        ew.orderByDesc("create_time", new String[0]);
        return (Wrapper<TspDealer>)ew;
    }

    public TspDealer getByDealerName(String dealerName) {
        QueryWrapper<TspDealer> ew = new QueryWrapper();
        ew.eq("dealer_name", dealerName);
        return (TspDealer)getOne((Wrapper)ew);
    }

    public TspDealer getByCode(String dealerCode) {
        QueryWrapper<TspDealer> ew = new QueryWrapper();
        ew.eq("dealer_code", dealerCode);
        return (TspDealer)getOne((Wrapper)ew);
    }
}
