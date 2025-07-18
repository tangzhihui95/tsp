package com.modern.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.annotation.DataScope;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.domain.TspUser;
import com.modern.model.dto.TspUserPageListDTO;
import com.modern.model.vo.TspUserPageListVO;
import com.modern.repository.TspUserRepository;
import com.modern.repository.TspVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspUserService
 * @Date：2025/7/2 14:11
 * @Filename：TspUserService
 */
@Service
public class TspUserService extends TspBaseService {

    @Autowired
    private TspUserRepository tspUserRepository;

    @Autowired
    private TspVehicleRepository tspVehicleRepository;

    @DataScope(userAlias = "sysu", deptAlias = "sysd")
    public PageInfo<TspUserPageListDTO> getPageList(TspUserPageListVO vo) {
        QueryWrapper<TspUser> listEw = tspUserRepository.getPageListEw(vo);
        Page<TspUser> page = tspUserRepository.page(Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), listEw);
        List<TspUserPageListDTO> dtos = new ArrayList<>();
        page.getRecords().forEach(item -> {
            TspUserPageListDTO dto = new TspUserPageListDTO();
            BeanUtils.copyProperties(item, dto);
            Integer count = tspVehicleRepository.countByTspUserId(item.getId());
            dto.setVehicleCount(count);
            dto.setRegTime(item.getCreateTime());
            dtos.add(dto);
        });
        return PageInfo.of(dtos, vo.getPageNum().intValue(), vo.getPageSize().intValue(), page.getRecords().size());
    }
}
