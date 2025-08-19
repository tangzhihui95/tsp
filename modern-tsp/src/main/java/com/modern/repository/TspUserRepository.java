package com.modern.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.modern.common.core.ServicePlusImpl;
import com.modern.common.utils.StringUtils;
import com.modern.domain.TspUser;
import com.modern.mapper.TspUserMapper;
import com.modern.model.vo.TspUserPageListVO;
import org.springframework.stereotype.Service;

/**
 * @Author：tzh
 * @Package：com.modern.repository
 * @Project：tsp
 * @name：TspUserRepository
 * @Date：2024/10/23 14:12
 * @Filename：TspUserRepository
 */
@Service
public class TspUserRepository extends ServicePlusImpl<TspUserMapper, TspUser,TspUser> {

    public QueryWrapper<TspUser> getPageListEw(TspUserPageListVO vo) {
        QueryWrapper<TspUser> ew = new QueryWrapper();
        ew.in(CollectionUtils.isNotEmpty(vo.getIds()), "id", vo.getIds());
        ew.like(StringUtils.isNotEmpty(vo.getMobile()), "mobile", vo.getMobile());
        ew.like(StringUtils.isNotEmpty(vo.getRealName()), "real_name", vo.getRealName());
        ew.orderByDesc("create_time", new String[0]);
        return ew;
    }

    public TspUser getByMobile(String mobile) {
        QueryWrapper<TspUser> ew = new QueryWrapper();
        ew.eq("mobile", mobile);
        ew.orderByDesc("create_time", new String[0]);
        ew.last(" limit 1");
        return (TspUser)getOne((Wrapper)ew);
    }
}
