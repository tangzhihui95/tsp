package com.modern.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.BaseModel;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.DateUtils;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.domain.TspVehicleModel;
import com.modern.domain.TspVehicleStdModel;
import com.modern.enums.TpsVehicleDataKeyEnum;
import com.modern.mapper.TspVehicleModelMapper;
import com.modern.model.dto.*;
import com.modern.model.vo.TspVehicleModelAddVO;
import com.modern.model.vo.TspVehicleModelPageListVO;
import com.modern.model.vo.TspVehiclePageListVO;
import com.modern.repository.TspVehicleModelRepository;
import com.modern.repository.TspVehicleRepository;
import com.modern.repository.TspVehicleStdModeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspVehicleModelService
 * @Date：2024/10/14 15:27
 * @Filename：TspVehicleModelService
 */
@Service
public class TspVehicleModelService extends TspBaseService {
    private static final Logger log = LoggerFactory.getLogger(TspVehicleModelService.class);

    @Autowired
    private TspVehicleStdModeRepository tspVehicleStdModeRepository;

    @Autowired
    private TspVehicleModelRepository tspVehicleModelRepository;

    @Autowired
    private TspVehicleRepository tspVehicleRepository;

    @Autowired
    private TspVehicleModelMapper tspVehicleModelMapper;

    public PageInfo<TspVehicleModelPageListDTO> getPageList(TspVehicleModelPageListVO vo) {
        log.info("查询车辆型号列表------{}", vo);
        QueryWrapper<TspVehicleModel> ew = new QueryWrapper<>();
        if (Objects.nonNull(vo.getTspVehicleStdModelId())) {
           /* Set<Long> modelIds = (Set<Long>) ((LambdaQueryChainWrapper) tspVehicleStdModeRepository.lambdaQuery()
                    .select(new SFunction[]{TspVehicleStdModel::getTspVehicleModelId})
                    .eq(BaseModel::getId, vo.getTspVehicleStdModelId())).list().stream()
                    .map(TspVehicleStdModel::getTspVehicleModelId).collect(Collectors.toSet());
            ew.in(CollectionUtils.isNotEmpty(modelIds), "id", modelIds);*/

            LambdaQueryChainWrapper<TspVehicleStdModel> queryWrapper = tspVehicleStdModeRepository.lambdaQuery()
                    .select(TspVehicleStdModel::getTspVehicleModelId)  // 选择特定字段
                    .eq(BaseModel::getId, vo.getTspVehicleStdModelId());
            List<TspVehicleStdModel> result = queryWrapper.list();
            // 处理结果集
            if (!CollectionUtils.isEmpty(result)) {
                Set<Long> modelIds = result.stream()
                        .map(TspVehicleStdModel::getTspVehicleModelId)
                        .collect(Collectors.toSet());
                ew.in(CollectionUtils.isNotEmpty(modelIds), "id", modelIds);
            }

        }

        ew.like(StringUtils.isNotEmpty(vo.getVehicleModelName()), "vehicle_model_name", vo.getVehicleModelName());
        ew.orderByDesc("update_time");
        Page<TspVehicleModel> page = (Page<TspVehicleModel>) tspVehicleModelRepository
                .page((IPage) Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), (Wrapper<TspVehicleModel>) ew);
        log.info("开始遍历整个车型分页数据(车型名称，关联车辆数，二级车型---------{}", page.getRecords());
        ArrayList<TspVehicleModelPageListDTO> dtos = new ArrayList<>();
        for (TspVehicleModel model : page.getRecords()) {
            TspVehicleModelPageListDTO dto = new TspVehicleModelPageListDTO();
            BeanUtils.copyProperties(model, dto);
            log.info("开始处理二级车型数据------");
            ArrayList<TspVehicleStdModePageListDTO> stdModePageListDTOS = new ArrayList<>();
            List<TspVehicleStdModel> stdModelList = this.tspVehicleStdModeRepository.getByTspVehicleModelId(model.getId());
            for (TspVehicleStdModel stdModel : stdModelList) {
                TspVehicleStdModePageListDTO modePageListDTO = new TspVehicleStdModePageListDTO();
                BeanUtils.copyProperties(stdModel, modePageListDTO);
                log.info("开始查询二级车型，用全局车型id来查询二级车型信息--------");
                Integer stdModelCount = this.tspVehicleRepository.countByTspVehicleStdModelId(stdModel.getId());
                modePageListDTO.setVehicleModelName(model.getVehicleModelName());
                modePageListDTO.setStdModeCount(stdModelCount);
                if (StringUtils.isNotNull(vo.getTspVehicleStdModelId())) {
                    if (vo.getTspVehicleStdModelId().equals(stdModel.getId()))
                        stdModePageListDTOS.add(modePageListDTO);
                    continue;
                }
                stdModePageListDTOS.add(modePageListDTO);
            }
            log.info("二级车型TspVehicleStdModePageListDTO处理完毕后,但最后返回的对象是TspVehicleModelPageListDTO");
            log.info("将TspVehicleModelPageListDTO类剩下的属性赋值(children vehicleCount)......");
            int sum = stdModePageListDTOS.stream().mapToInt(TspVehicleStdModePageListDTO::getStdModeCount).sum();
            if (StringUtils.isNotNull(vo.getTspVehicleStdModelId())) {
                if (CollectionUtils.isNotEmpty(stdModePageListDTOS) && stdModePageListDTOS.size() != 0) {
                    dto.setChildren(stdModePageListDTOS);
                    dto.setVehicleCount(Integer.valueOf(sum));
                    dtos.add(dto);
                }
                continue;
            }
            dto.setChildren(stdModePageListDTOS);
            dto.setVehicleCount(Integer.valueOf(sum));
            dtos.add(dto);
        }
        log.info("车辆型号转换完毕--------");
        return PageInfo.of(dtos, page.getCurrent(), page.getSize(), page.getTotal());
    }

    public JsonResult add(TspVehicleModelAddVO vo) {
        log.info("车型添加-------{}", vo);
        TspVehicleModel vehicleModel = this.tspVehicleModelRepository.getByVehicleModelName(vo.getVehicleModelName());
        if (Objects.nonNull(vehicleModel))
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_NOT_NULL_ERR.throwErr();
        vehicleModel = new TspVehicleModel();
        BeanUtils.copyProperties(vo, vehicleModel);
        vehicleModel.setCreateBy(SecurityUtils.getUsername());
        vehicleModel.setCreateTime(DateUtils.getCurrentTime());
        vehicleModel.setUpdateBy(SecurityUtils.getUsername());
        return JsonResult.getResult(tspVehicleModelRepository.save(vehicleModel));
    }

    public JsonResult edit(TspVehicleModelAddVO vo) {
        log.info("车辆编辑------{}", vo);
        if (null == vo.getTspVehicleModelId())
            throw new RuntimeException("车辆id不存在");
        TspVehicleModel model = tspVehicleModelRepository.getById(vo.getTspVehicleModelId());
        if (Objects.isNull(model))
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_NULL_ERR.throwErr();
        TspVehicleModel modelName = this.tspVehicleModelRepository.getByVehicleModelName(vo.getVehicleModelName());
        if (null != modelName && !modelName.getId().equals(model.getId()))
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_NOT_NULL_ERR.throwErr();
        BeanUtils.copyProperties(vo, model);
        model.setUpdateBy(SecurityUtils.getUsername());
        return JsonResult.getResult(tspVehicleModelRepository.updateById(model));
    }

    public JsonResult delete(Long tspVehicleModelId) {
        log.info("根据车型id进行删除---------{}", tspVehicleModelId);
        TspVehicleModel model = tspVehicleModelRepository.getById(tspVehicleModelId);
        if (Objects.isNull(model))
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_NULL_ERR.throwErr();
        List<TspVehicleStdModel> stdModelList = this.tspVehicleStdModeRepository.findByTspVehicleModelId(tspVehicleModelId);
        if (!CollectionUtils.isEmpty(stdModelList))
            ErrorEnum.TSP_VEHICLE_MODEL_VEHICLE_MODEL_DELETE_ERR.throwErr();
        return JsonResult.getResult(tspVehicleModelRepository.removeById(tspVehicleModelId));
    }

    public JsonResult deletes(Long[] tspVehicleModelIds) {
        for (Long tspVehicleModelId : tspVehicleModelIds) {
            TspVehicleModel model = tspVehicleModelRepository.getById(tspVehicleModelId);
            List<TspVehicleStdModel> stdModels = tspVehicleStdModeRepository.getByTspVehicleModelId(model.getId());
            for (TspVehicleStdModel stdModel : stdModels)
                tspVehicleStdModeRepository.removeById(stdModel.getId());
            tspVehicleModelRepository.removeById(model.getId());
        }
        return JsonResult.getResult(true);
    }

    public List<TspVehicleStdModelExListDTO> exportList(TspVehicleModelPageListVO vo) {
        vo.setPageNum(Integer.valueOf(1));
        vo.setPageSize(Integer.valueOf(2147483647));
        QueryWrapper<TspVehicleModel> ew = tspVehicleModelRepository.getStdModelList(vo);
        IPage<TspVehicleStdModelExListDTO> page = tspVehicleModelMapper.getPageList(Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
        List<TspVehicleStdModelExListDTO> dtos = page.getRecords();
        for (TspVehicleStdModelExListDTO dto : dtos) {
            Integer stdModelCount = this.tspVehicleRepository.countByTspVehicleStdModelId(dto.getTspStdModelId());
            dto.setVehicleCount(stdModelCount.toString());
            if (dto.getDataKey() != null)
                dto.setDataType(dto.getDataKey().getKey());
        }
        return dtos;
    }

    public String importVehicleModel(MultipartFile multipartFile, Boolean isUpdateSupport) throws IOException {
        try {
            ExcelUtil<TspVehicleModel> util = new ExcelUtil(TspVehicleModel.class);
            List<TspVehicleModel> dtos = util.importExcel(multipartFile.getInputStream());
            if (StringUtils.isNull(dtos) || dtos.size() == 0)
                throw new ServiceException("导入数据不能为空!");
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            for (TspVehicleModel dto : dtos) {
                try {
                    if (null == dto.getVehicleModelName()) {
                        failureNum++;
                        failureMsg.append("<br/>").append(successNum).append("、车辆一级车型名称不能为空!");
                        continue;
                    }
                    TspVehicleModel tspVehicleModel = this.tspVehicleModelRepository.getByVehicleModelName(dto.getVehicleModelName());
                    if (Objects.isNull(tspVehicleModel)) {
                        tspVehicleModel = new TspVehicleModel();
                        BeanUtils.copyProperties(dto, tspVehicleModel);
                        tspVehicleModel.setCreateBy(SecurityUtils.getUsername());
                        tspVehicleModel.setUpdateBy(SecurityUtils.getUsername());
                        this.tspVehicleModelRepository.save(tspVehicleModel);
                        successNum++;
                        successMsg.append("<br/>").append(successNum).append("、车辆一级车型").append(dto.getVehicleModelName()).append("导入成功！");
                        continue;
                    }
                    failureNum++;
                    failureMsg.append("<br/>").append(successNum).append("、车辆一级车型").append(dto.getVehicleModelName()).append("已存在！");
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、车辆一级车型" + dto.getVehicleModelName() + "导入失败！";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共" + failureNum + "条数据格式不正确,错误如下:");
                throw new ServiceException(failureMsg.toString());
            }
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共" + successNum + "条，数据如下:");
            return successMsg.toString();
        } catch (Throwable $ex) {
            throw $ex;
        }
    }

    public String importVehicleModelStd(MultipartFile multipartFile, Boolean isUpdateSupport) throws IOException {
        try {
            ExcelUtil<TspVehicleStdModelExportListDTO> util = new ExcelUtil(TspVehicleStdModelExportListDTO.class);
            List<TspVehicleStdModelExportListDTO> dtos = util.importExcel(multipartFile.getInputStream());
            if (StringUtils.isNull(dtos) || dtos.size() == 0)
                throw new ServiceException("导入数据不能为空！");
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            for (TspVehicleStdModelExportListDTO dto : dtos) {
                try {
                    Map<String, Object> checkMap = toCheckStdModel(dto, failureMsg, failureNum);
                    failureNum = ((Integer) checkMap.get("failureNum")).intValue();
                    failureMsg = (StringBuilder) checkMap.get("failureMsg");
                    if (failureNum == 0) {
                        TspVehicleModel tspVehicleModel = this.tspVehicleModelRepository.getByVehicleModelName(dto.getVehicleModelName());
                        TspVehicleStdModel tspVehicleStdModel = this.tspVehicleStdModeRepository.getByStdModeNameAndModelId(dto.getStdModeName(), tspVehicleModel.getId());
                        if (Objects.isNull(tspVehicleModel)) {
                            failureNum++;
                            failureMsg.append("<br/>").append(successNum).append("、车辆一级车型").append(dto.getVehicleModelName()).append("不存在");
                            continue;
                        }
                        if (Objects.isNull(tspVehicleModel)) {
                            tspVehicleStdModel = new TspVehicleStdModel();
                            BeanUtils.copyProperties(dto, tspVehicleStdModel);
                            if ("纯电动".equals(dto.getDataType())) {
                                tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.BE_VS);
                            } else if ("混合动力".equals(dto.getDataType())) {
                                tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.HYBRID);
                            } else if ("燃料电池电动".equals(dto.getDataType())) {
                                tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.FUEL_CELL_ELECTRIC);
                            } else if ("插电式混动".equals(dto.getDataType())) {
                                tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.PLUG_IN_HYBRID);
                            } else if ("增程式混动".equals(dto.getDataType())) {
                                tspVehicleStdModel.setDataKey(TpsVehicleDataKeyEnum.INCREMENTAL_HYBRID);
                            }
                            if (dto.getDataType() != null && !"".equals(dto.getDataType()) && tspVehicleStdModel.getDataKey() == null) {
                                failureNum++;
                                failureMsg.append("<br/>").append(failureNum).append("、车辆能源类型").append(dto.getDataType()).append("值异常");
                                continue;
                            }
                            tspVehicleStdModel.setCreateBy(SecurityUtils.getUsername());
                            tspVehicleStdModel.setUpdateBy(SecurityUtils.getUsername());
                            tspVehicleStdModel.setTspVehicleModelId(tspVehicleModel.getId());
                            this.tspVehicleStdModeRepository.save(tspVehicleStdModel);
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、车辆二级车型").append(dto.getStdModeName()).append("新增成功");
                            continue;
                        }
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、车辆二级车型").append(dto.getVehicleModelName()).append("已存在");
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、二级车型" + dto.getStdModeName() + "导入失败";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共" + failureNum + "条数据格式不正确，错误如下：");
                throw new ServiceException(failureMsg.toString());
            }
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共" + successNum + "条，数据如下：");
            return successMsg.toString();
        } catch (Throwable $ex) {
            throw $ex;
        }
    }

    private Map<String, Object> toCheckStdModel(TspVehicleStdModelExportListDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        if (dto.getVehicleModelName() == null || dto.getVehicleModelName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、一级车型不能为空值");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getStdModeName() == null || dto.getStdModeName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、二级车型不能为空值");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getNoticeModel() == null || dto.getNoticeModel().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、公告型号不能为空值");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getNoticeBatch() == null || dto.getNoticeBatch().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、公告批次号不能为空值");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", Integer.valueOf(failureNum));
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    public List<TspVehicleModelSelectDTO> selectChildrenList(TspVehiclePageListVO vo) {
        return tspVehicleModelRepository.selectChildrenList(vo);
    }


}
