package com.modern.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.BaseModel;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.domain.TspEquipmentModel;
import com.modern.domain.TspEquipmentType;
import com.modern.mapper.TspEquipmentModelMapperNew;
import com.modern.mapper.TspEquipmentTypeMapper;
import com.modern.model.dto.*;
import com.modern.model.vo.FrontQuery;
import com.modern.model.vo.TspEquipmentTypeAddVO;
import com.modern.repository.TspEquipmentModelRepository;
import com.modern.repository.TspEquipmentTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：EquipmentTypeService
 * @Date：2024/8/28 16:36
 * @Filename：EquipmentTypeService
 */
@Service
public class TspEquipmentTypeService extends TspBaseService {
    private static final Logger log = LoggerFactory.getLogger(TspEquipmentTypeService.class);
    @Autowired
    private TspEquipmentModelRepository tspEquipmentModelRepository;

    @Autowired
    private TspEquipmentTypeRepository tspEquipmentTypeRepository;

    @Autowired
    private TspEquipmentTypeMapper tspEquipmentTypeMapper;
    @Autowired
    private TspEquipmentModelMapperNew tspEquipmentModelMapperNew;

    @Autowired
    private TspEquipmentModelService tspEquipmentModelService;

    /**
     * 设备分类-列表查询
     *
     * @param vo
     * @return
     */
    public PageInfo<TspEquipmentTypePageListDTO> getList(FrontQuery vo) {

        if (Objects.nonNull(vo.getTspEquipmentModelId())) {
            TspEquipmentModel tspEquipmentModel = tspEquipmentModelMapperNew.getById(vo.getTspEquipmentModelId());
        }
        return null;
    }

    /**
     * 设备分类-列表查询
     *
     * @param vo
     * @return
     */
    public PageInfo<TspEquipmentTypePageListDTO> getPageList(FrontQuery vo) {
        List<TspEquipmentTypePageListDTO> dtos = new ArrayList<>();
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper();
        if (Objects.nonNull(vo.getTspEquipmentModelId())) {
            TspEquipmentModel tspEquipmentModel = tspEquipmentModelRepository.getById(vo.getTspEquipmentModelId());
            ew.eq("id", tspEquipmentModel.getTspEquipmentTypeId());
        } else if (Objects.nonNull(vo.getTspEquipmentTypeId())) {
            ew.eq("id", vo.getTspEquipmentTypeId());
        }
        ew.eq("is_delete", Integer.valueOf(0));
        ew.and(StringUtils.isNotEmpty(vo.getSearch()), q ->
                ((QueryWrapper) ((QueryWrapper) q.like("name", vo.getSearch())).or()).like("extra_type", vo.getSearch()));
        ew.orderByDesc("create_time", new String[0]);
        Page<TspEquipmentType> page = tspEquipmentTypeRepository.page(new Page(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
        for (TspEquipmentType type : page.getRecords()) {
            List<TspEquipmentModel> modelList;
            TspEquipmentTypePageListDTO dto = new TspEquipmentTypePageListDTO();
            BeanUtils.copyProperties(type, dto);
            int count = tspEquipmentTypeMapper.countByTspEquipmentTypeId(type.getId());
            if (Objects.nonNull(vo.getTspEquipmentModelId())) {
                modelList = tspEquipmentModelRepository.findByTspModelId(type.getId(), vo.getTspEquipmentModelId());
            } else {
                modelList = tspEquipmentModelRepository.findByTspEquipmentTypeId(type.getId());
            }
            List<TspEquipmentModelPageListDTO> modelDTOs = new ArrayList<>();
            for (TspEquipmentModel tspEquipmentModel : modelList) {
                int modelCount = tspEquipmentTypeMapper.countByTspEquipmentModelId(tspEquipmentModel.getId());
                TspEquipmentModelPageListDTO modelDTO = new TspEquipmentModelPageListDTO();
                BeanUtils.copyProperties(tspEquipmentModel, modelDTO);
                modelDTO.setCount(Integer.valueOf(modelCount));
                modelDTOs.add(modelDTO);
            }
            if (CollectionUtils.isNotEmpty(modelList)) {
                dto.setChildren(modelDTOs);
                dto.setCount(Integer.valueOf(count));
            }
            dtos.add(dto);
        }
        return PageInfo.of(dtos, vo.getPageNum().intValue(), vo.getPageSize().intValue(), page.getRecords().size());
    }

    /**
     * 设备分类-添加
     *
     * @param vo
     * @return
     */
    public JsonResult add(TspEquipmentTypeAddVO vo) {
        TspEquipmentType type = tspEquipmentTypeRepository.getByName(vo.getName(), vo.getExtraType());
        if (Objects.nonNull(type))
            ErrorEnum.TSP_EQUIPMENT_TYPE_NOT_NULL_ERR.throwErr();
        type = new TspEquipmentType();
        BeanUtils.copyProperties(vo, type);
        type.setCreateBy(SecurityUtils.getUsername());
        type.setUpdateBy(SecurityUtils.getUsername());
        tspEquipmentTypeRepository.save(type);
        return JsonResult.getResult(true);
    }


    public JsonResult edit(TspEquipmentTypeAddVO vo) {
        if (Objects.isNull(vo.getEquipmentTypeId()))
            throw new RuntimeException("设备分类ID不能为空");
        TspEquipmentType equipmentType = tspEquipmentTypeRepository.getById(vo.getEquipmentTypeId());
        if (Objects.isNull(equipmentType))
            ErrorEnum.TSP_EQUIPMENT_TYPE_NOT_NULL_ERR.throwErr();
        QueryWrapper<TspEquipmentType> ew = new QueryWrapper();
        ew.eq("name", vo.getName());
        ew.notIn("id", new Object[]{vo.getEquipmentTypeId()});
        TspEquipmentType one = tspEquipmentTypeRepository.getOne(ew);
        if (Objects.nonNull(one))
            ErrorEnum.TSP_EQUIPMENT_TYPE_NOT_NULL_ERR.throwErr();
        BeanUtils.copyProperties(vo, equipmentType);
        equipmentType.setUpdateBy(SecurityUtils.getUsername());
        tspEquipmentTypeRepository.updateById(equipmentType);
        return JsonResult.getResult(true);
    }

    public JsonResult delete(Long equipmentTypeId) {
        TspEquipmentType equipmentType = tspEquipmentTypeRepository.getById(equipmentTypeId);
        if (Objects.isNull(equipmentType))
            ErrorEnum.TSP_EQUIPMENT_TYPE_NULL_ERR.throwErr();
        List<Long> models = tspEquipmentModelRepository.findByTspEquipmentTypeId(equipmentTypeId).stream().map(BaseModel::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(models))
            ErrorEnum.TSP_EQUIPMENT_TYPE_DELETE_ERR.throwErr();
        this.tspEquipmentTypeRepository.removeById(equipmentTypeId);
        return JsonResult.getResult(true);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public JsonResult deletes(Long[] equipmentTypeIds) {
        if (equipmentTypeIds.length == 0)
            throw new RuntimeException("设备分类ID不能为空");
        for (Long equipmentTypeId : equipmentTypeIds) {
            TspEquipmentType equipmentType = tspEquipmentTypeRepository.getById(equipmentTypeId);
            if (equipmentType == null)
                ErrorEnum.TSP_EQUIPMENT_TYPE_NULL_ERR.throwErr();
            List<Long> models = tspEquipmentModelRepository.findByTspEquipmentTypeId(equipmentTypeId).stream().map(BaseModel::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(models))
                ErrorEnum.TSP_EQUIPMENT_TYPE_DELETE_ERR.throwErr();
            this.tspEquipmentTypeRepository.removeById(equipmentTypeId);
        }
        return JsonResult.getResult(true);
    }

    public List<TspEquipmentTypeSelectDTO> selectList(FrontQuery vo) {
        return this.tspEquipmentTypeRepository.selectList(vo);
    }

    public List<TspEquipmentTypeExcelDTO> exportList(FrontQuery vo) {
        vo.setPageNum(Integer.valueOf(1));
        vo.setPageSize(Integer.valueOf(2147483647));
        ArrayList<TspEquipmentTypeExcelDTO> dtos = new ArrayList<>();
        List<TspEquipmentModelPageListDTO> list = tspEquipmentModelService.getPageList(vo).getList();
        for (TspEquipmentModelPageListDTO tspEquipmentModelPageListDTO : list) {
            int count = tspEquipmentTypeMapper.countByTspEquipmentModelId(tspEquipmentModelPageListDTO.getId());
            TspEquipmentTypeExcelDTO dto = new TspEquipmentTypeExcelDTO();
            BeanUtils.copyProperties(tspEquipmentModelPageListDTO, dto);
            dto.setCount(Integer.valueOf(count));
            dtos.add(dto);
        }
        return dtos;
    }

    public String importEquipmentModel(MultipartFile file, Boolean isUpdateSupport) throws IOException {
        try {
            ExcelUtil<TspEquipmentTypeExcelDTO> util = new ExcelUtil(TspEquipmentTypeExcelDTO.class);
            List<TspEquipmentTypeExcelDTO> dtos = util.importExcel(file.getInputStream());
            if (StringUtils.isNull(dtos) || dtos.size() == 0)
                throw new ServiceException("导入数据不能为空！");
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            for (TspEquipmentTypeExcelDTO dto : dtos) {
                try {
                    Map<String, Object> checkMap = toCheckModel(dto, failureMsg, failureNum);
                    failureNum = ((Integer) checkMap.get("failureNum")).intValue();
                    failureMsg = (StringBuilder) checkMap.get("failureMsg");
                    if (failureNum == 0) {
                        TspEquipmentType tspEquipmentType = tspEquipmentTypeRepository.getByNameAndExtraType(dto.getName(), dto.getExtraType());
                        if (Objects.isNull(tspEquipmentType)) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、设备类型").append(dto.getExtraType()).append("不存在");
                            continue;
                        }
                        TspEquipmentModel tspEquipmentModel = tspEquipmentModelRepository.getByModelNameAndType(dto.getModelName(), tspEquipmentType.getId());
                        if (Objects.nonNull(tspEquipmentModel)) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、设备类型下的型号").append(dto.getModelName()).append("已存在");
                            continue;
                        }
                        tspEquipmentModel = new TspEquipmentModel();
                        BeanUtils.copyProperties(dto, tspEquipmentModel);
                        tspEquipmentModel.setTspEquipmentTypeId(tspEquipmentType.getId());
                        tspEquipmentModel.setCreateBy(SecurityUtils.getUsername());
                        tspEquipmentModel.setUpdateBy(SecurityUtils.getUsername());
                        tspEquipmentModelRepository.save(tspEquipmentModel);
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、设备型号").append(dto.getModelName()).append("更新成功");
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、设备类型" + dto.getExtraType() + "导入失败";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共" + failureNum + "条数据格式不正确,错误如下:");
                throw new ServiceException(failureMsg.toString());
            }
            successMsg.insert(0, "恭喜您,数据已全部导入成功！共" + successNum + "条,数据如下:");
            return successMsg.toString();
        } catch (Throwable $ex) {
            throw $ex;
        }
    }

    private Map<String, Object> toCheckModel(TspEquipmentTypeExcelDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        if (dto.getName() == null || dto.getName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备类型").append(dto.getName()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getExtraType() == null || dto.getExtraType().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备扩展信息").append(dto.getExtraType()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getModelName() == null || dto.getModelName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备型号").append(dto.getModelName()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getSuppliers() == null || dto.getSuppliers().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、供应商").append(dto.getSuppliers()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getBatchNumber() == null || dto.getBatchNumber().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、生产批次").append(dto.getBatchNumber()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", Integer.valueOf(failureNum));
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    public String importEquipmentType(MultipartFile file, Boolean isUpdateSupport) {
        try {
            ExcelUtil<TspEquipmentTypeImportDTO> util = new ExcelUtil(TspEquipmentTypeImportDTO.class);
            List<TspEquipmentTypeImportDTO> dtos = null;
            try {
                dtos = util.importExcel(file.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (StringUtils.isNull(dtos) || dtos.size() == 0)
                throw new ServiceException("导入数据不能为空！");
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            for (TspEquipmentTypeImportDTO dto : dtos) {
                try {
                    Map<String, Object> checkMap = toCheckType(dto, failureMsg, failureNum);
                    failureNum = ((Integer) checkMap.get("failureNum")).intValue();
                    failureMsg = (StringBuilder) checkMap.get("failureMsg");
                    if (failureNum == 0) {
                        TspEquipmentType tspEquipmentType = tspEquipmentTypeRepository.getByNameAndExtraType(dto.getName(), dto.getExtraType());
                        if (tspEquipmentType != null) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、设备类型").append(dto.getName()).append("已存在");
                            continue;
                        }
                        tspEquipmentType = new TspEquipmentType();
                        BeanUtils.copyProperties(dto, tspEquipmentType);
                        if (dto.getTerminal() == null || "".equals(dto.getTerminal()) || dto.getTerminal().equals("是")) {
                            tspEquipmentType.setIsTerminal(Integer.valueOf(1));
                        } else {
                            tspEquipmentType.setIsTerminal(Integer.valueOf(0));
                        }
                        tspEquipmentType.setCreateBy(SecurityUtils.getUsername());
                        tspEquipmentType.setUpdateBy(SecurityUtils.getUsername());
                        tspEquipmentTypeRepository.save(tspEquipmentType);
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、设备类型").append(dto.getName()).append("添加成功");
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、设备类型" + dto.getExtraType() + "导入失败";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共" + failureNum + "条数据格式不正确,错误如下:");
                throw new ServiceException(failureMsg.toString());
            }
            successMsg.insert(0, "恭喜您,数据已全部导入成功！共" + successNum + "条,数据如下:");
            return successMsg.toString();
        } catch (Throwable $ex) {
            throw $ex;
        }
    }

    private Map<String, Object> toCheckType(TspEquipmentTypeImportDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        if (dto.getName() == null || dto.getName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备类型").append(dto.getName()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getExtraType() == null || dto.getExtraType().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备扩展信息").append(dto.getExtraType()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getTerminal() != null && !dto.getTerminal().equals("") && !".equals(dto.getTerminal()) && !".equals(dto.getTerminal())) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、是否为终端").append(dto.getTerminal()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", Integer.valueOf(failureNum));
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }


}
