package com.modern.service;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.utils.DateUtils;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanCopyUtils;
import com.modern.domain.TspEquipmentModel;
import com.modern.mapper.TspEquipmentModelMapper;
import com.modern.mapper.TspEquipmentTypeMapper;
import com.modern.model.dto.TspEquipmentModelPageListDTO;
import com.modern.model.vo.FrontQuery;
import com.modern.model.vo.TspEquipmentModelAddVO;
import com.modern.repository.TspEquipmentModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspEquipmentModelService
 * @Date：2024/9/11 15:29
 * @Filename：TspEquipmentModelService
 */
@Service
public class TspEquipmentModelService extends TspBaseService {

    @Autowired
    private TspEquipmentModelRepository tspEquipmentModelRepository;

    @Resource
    private TspEquipmentModelMapper tspEquipmentModelMapper;

    @Resource
    private TspEquipmentTypeMapper tspEquipmentTypeMapper;

    public PageInfo<TspEquipmentModelPageListDTO> getPageList(FrontQuery vo) {
        QueryWrapper<TspEquipmentModel> ew = tspEquipmentModelRepository.getPageList(vo);
        IPage<TspEquipmentModelPageListDTO> pageList = tspEquipmentModelMapper.getPageList(
                Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
        return PageInfo.of(pageList, (long) pageList.getRecords().size());
    }

    public JsonResult add(TspEquipmentModelAddVO vo) {
        if (vo.getTspEquipmentTypeId() == null)
            throw new RuntimeException("设备分类ID不能玩为空");
        TspEquipmentModel model = tspEquipmentModelRepository.getByModelName(vo.getModelName());
        if (Objects.nonNull(model))
            ErrorEnum.TSP_EQUIPMENT_MODEL_NOT_NULL_ERR.throwErr();
        TspEquipmentModel copy = BeanCopyUtils.oneCopy(vo, CopyOptions.create(), TspEquipmentModel.class);
        copy.setCreateBy(SecurityUtils.getUsername());
        copy.setCreateTime(DateUtils.getCurrentTime());
        this.tspEquipmentModelRepository.save(copy);
        return JsonResult.getResult(true);
    }

    public JsonResult edit(TspEquipmentModelAddVO vo) {
        if (vo.getTspEquipmentModelId() == null)
            throw new RuntimeException("设备型号不能为空");
        TspEquipmentModel nameNotId = this.tspEquipmentModelRepository.getByModelNameNotId(vo.getModelName(), vo.getTspEquipmentModelId());
        if (nameNotId != null && nameNotId.getTspEquipmentTypeId().equals(vo.getTspEquipmentTypeId()))
            ErrorEnum.TSP_EQUIPMENT_MODEL_NOT_NULL_ERR.throwErr();
        TspEquipmentModel copy = BeanCopyUtils.oneCopy(vo, CopyOptions.create(), TspEquipmentModel.class);
        copy.setUpdateBy(SecurityUtils.getUsername());
        copy.setUpdateTime(DateUtils.getCurrentTime());
        copy.setId(vo.getTspEquipmentModelId());
        this.tspEquipmentModelRepository.updateById(copy);
        return JsonResult.getResult(true);
    }

    public JsonResult delete(Long tspEquipmentModelId) {
        int count = tspEquipmentTypeMapper.countByTspEquipmentModelId(tspEquipmentModelId);
        if (count > 0)
            ErrorEnum.TSP_EQUIPMENT_MODEL_DELETE_ERR.throwErr();
        this.tspEquipmentModelMapper.deleteById(tspEquipmentModelId);
        return JsonResult.getResult(true);
    }

}
