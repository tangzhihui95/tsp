package com.modern.service;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.model.LoginUser;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.domain.TspEquipment;
import com.modern.domain.TspEquipmentModel;
import com.modern.domain.TspEquipmentType;
import com.modern.domain.TspVehicle;
import com.modern.entity.VehicleIntegrate;
import com.modern.framework.config.RedisConfig;
import com.modern.mapper.TspEquipmentMapper;
import com.modern.model.dto.TspEquipmentExcelDTO;
import com.modern.model.dto.TspEquipmentLikeSelectDTO;
import com.modern.model.dto.TspEquipmentPageListDTO;
import com.modern.model.vo.TspEquipmentAddVO;
import com.modern.model.vo.TspEquipmentPageListVO;
import com.modern.model.vo.TspEquipmentScrapVO;
import com.modern.repository.TspEquipmentModelRepository;
import com.modern.repository.TspEquipmentRepository;
import com.modern.repository.TspEquipmentTypeRepository;
import com.modern.repository.TspVehicleRepository;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspEquipmentService
 * @Date：2024/9/12 14:23
 * @Filename：TspEquipmentService
 */
@Service
public class TspEquipmentService extends TspBaseService {
    private static final Logger log = LoggerFactory.getLogger(TspEquipmentService.class);

    @Autowired
    private TspEquipmentMapper tspEquipmentMapper;

    @Autowired
    private TspEquipmentRepository tspEquipmentRepository;

    @Autowired
    private TspEquipmentModelRepository tspEquipmentModelRepository;

    @Autowired
    private TspVehicleRepository tspVehicleRepository;

    @Autowired
    private TspEquipmentTypeRepository tspEquipmentTypeRepository;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private RedisConfig redisConfig;


    public PageInfo<TspEquipmentPageListDTO> getPageList(TspEquipmentPageListVO vo) {
        List<Long> bindEquipmentIds = tspEquipmentMapper.getBindEquipments();
        vo.setTspEquipmentIds(bindEquipmentIds);
        QueryWrapper<TspEquipment> listEw = tspEquipmentRepository.getPageListEw(vo);
        Integer count = tspEquipmentMapper.getCount(listEw);
        IPage<TspEquipmentPageListDTO> pageList = tspEquipmentMapper.getPageList(Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), listEw);
        for (TspEquipmentPageListDTO record : pageList.getRecords()) {
            TspEquipmentModel byId = tspEquipmentModelRepository.getById(record.getTspEquipmentModelId());
            List<TspVehicle> vehicleList = tspVehicleRepository.findByTspEquipmentId(record.getId());
            if (CollectionUtils.isEmpty(vehicleList) || vehicleList.size() == 0) {
                record.setBindStatus("未绑定");
            } else {
                record.setBindStatus("已绑定");
            }
            if (Objects.nonNull(byId)) {
                TspEquipmentType equipmentType = tspEquipmentTypeRepository.getById(byId.getTspEquipmentTypeId());
                if (Objects.nonNull(equipmentType)) {
                    record.setTspEquipmentTypeId(equipmentType.getId());
                    record.setName(equipmentType.getName());
                }
            }
            List<TspVehicle> vehicles = tspVehicleRepository.findByTspEquipmentId(record.getId());
            if (!CollectionUtils.isEmpty(vehicles) && vehicles.size() != 0) {
                TspVehicle tspVehicle = vehicles.get(0);
                RedisConfig redisConfig = new RedisConfig();
                RedisTemplate<Object, Object> redisTemplate = redisConfig.redisTemplate(redisConnectionFactory);
                HashOperations<Object, Object, Object> hashOps = redisTemplate.opsForHash();
                List<Object> values = hashOps.values("VehicleRealtimeData:" + tspVehicle.getVin());
                if (values.size() != 0) {
                    LocalDateTime collectTime = null;
                    Map<String, Object> integrateJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + tspVehicle.getVin(), "VehicleIntegrate");
                    if (!CollectionUtils.isEmpty(integrateJson)) {
                        collectTime = convertToLocalDateTime((JSONArray) integrateJson.get("collectTime"));
                        integrateJson.remove("collectTime");
                        List<VehicleIntegrate> vehicleIntegrates = JSONArray.parseArray("[" + integrateJson + "]", VehicleIntegrate.class);
                        if (!CollectionUtils.isEmpty(vehicleIntegrates) && vehicleIntegrates.size() != 0) {
                            record.setIsOnline(calcVehicleStateExt(vehicleIntegrates.get(0), collectTime, LocalDateTime.now()));
                            continue;
                        }
                        record.setIsOnline(Boolean.valueOf(false));
                    }
                    continue;
                }
                record.setIsOnline(Boolean.valueOf(false));
                continue;
            }
            record.setIsOnline(Boolean.valueOf(false));
        }
        return PageInfo.of(pageList);
    }

    public JsonResult add(TspEquipmentAddVO vo) {
        TspEquipment tspEquipment = tspEquipmentRepository.getBySn(vo.getSn());
        if (Strings.isNotEmpty(tspEquipment.getSn())) ErrorEnum.TSP_SN_NOT_UNIQ_ERR.throwErr();
        tspEquipment = tspEquipmentRepository.getByICCID(vo.getIccId());
        if (Strings.isNotEmpty(tspEquipment.getIccId())) ErrorEnum.TSP_ICCID_NOT_UNIQ_ERR.throwErr();
        tspEquipment = this.tspEquipmentRepository.getByIMSI(vo.getImsi());
        if (Strings.isNotEmpty(tspEquipment.getImsi())) ErrorEnum.TSP_IMSI_NOT_UNIQ_ERR.throwErr();
        tspEquipment = this.tspEquipmentRepository.getBySIM(vo.getSim());
        if (Strings.isNotEmpty(tspEquipment.getSim())) ErrorEnum.TSP_SIM_NOT_UNIQ_ERR.throwErr();
        tspEquipment = this.tspEquipmentRepository.getByIMEI(vo.getImei());
        if (Strings.isNotEmpty(tspEquipment.getImei())) ErrorEnum.TSP_IMEI_NOT_UNIQ_ERR.throwErr();
        tspEquipment = new TspEquipment();
        BeanUtils.copyProperties(vo, tspEquipment);
        tspEquipment.setCreateBy(SecurityUtils.getUsername());
        tspEquipment.setUpdateBy(SecurityUtils.getUsername());
        tspEquipment.setIsScrap(Boolean.valueOf(false));
        this.tspEquipmentRepository.save(tspEquipment);
        return JsonResult.getResult(true);
    }

    public JsonResult edit(TspEquipmentAddVO vo) {
        if (null == vo.getTspEquipmentId()) throw new RuntimeException("设备ID不能为空");
        TspEquipment equipment = tspEquipmentRepository.getById(vo.getTspEquipmentId());
        if (Objects.isNull(equipment)) ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
        TspEquipment sn = this.tspEquipmentRepository.getBySn(vo.getSn());
        if (Objects.nonNull(sn) && !sn.getId().equals(equipment.getId())) ErrorEnum.TSP_SN_NOT_UNIQ_ERR.throwErr();
        TspEquipment iccid = this.tspEquipmentRepository.getByICCID(vo.getIccId());
        if (Objects.nonNull(iccid) && !iccid.getId().equals(equipment.getId()))
            ErrorEnum.TSP_ICCID_NOT_UNIQ_ERR.throwErr();
        TspEquipment imsi = this.tspEquipmentRepository.getByIMSI(vo.getImsi());
        if (Objects.nonNull(imsi) && !imsi.getId().equals(equipment.getId()))
            ErrorEnum.TSP_IMSI_NOT_UNIQ_ERR.throwErr();
        TspEquipment sim = this.tspEquipmentRepository.getBySIM(vo.getSim());
        if (Objects.nonNull(sim) && !sim.getId().equals(equipment.getId())) ErrorEnum.TSP_SIM_NOT_UNIQ_ERR.throwErr();
        TspEquipment imei = this.tspEquipmentRepository.getByIMEI(vo.getImei());
        if (Objects.nonNull(imei) && !imei.getId().equals(equipment.getId()))
            ErrorEnum.TSP_IMEI_NOT_UNIQ_ERR.throwErr();
        BeanUtils.copyProperties(vo, equipment);
        equipment.setUpdateBy(SecurityUtils.getUsername());
        this.tspEquipmentRepository.updateById(equipment);
        return JsonResult.getResult(true);
    }

    public List<TspEquipmentLikeSelectDTO> likeSelect(Long tspEquipmentId, String modelName) {
        ArrayList<TspEquipmentLikeSelectDTO> dtos = new ArrayList<>();
        tspEquipmentModelRepository.findByTspEquipmentIdLikeName(tspEquipmentId, modelName).forEach(item -> {
            TspEquipmentLikeSelectDTO dto = new TspEquipmentLikeSelectDTO();
            BeanUtils.copyProperties(item, dto);
            dtos.add(dto);
        });
        return dtos;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public JsonResult deletes(Long[] tspEquipmentIds) {
        if (tspEquipmentIds.length == 0) ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
        List<TspEquipment> equipments = tspEquipmentRepository.listByIds(Arrays.asList(tspEquipmentIds));
        if (CollectionUtils.isEmpty(equipments)) ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
        List<TspVehicle> vehicles = tspVehicleRepository.findInTspEquipmentIds(tspEquipmentIds);
        if (!CollectionUtils.isEmpty(vehicles)) ErrorEnum.TSP_EQUIPMENT_EQUIPMENT_DELETE_ERR.throwErr();
        this.tspEquipmentRepository.removeByIds(Arrays.asList(tspEquipmentIds));
        return JsonResult.getResult(true);
    }

    public List<TspEquipmentExcelDTO> exportList(TspEquipmentPageListVO vo) {
        vo.setPageNum(Integer.valueOf(1));
        vo.setPageSize(Integer.valueOf(2147483647));
        List<TspEquipmentExcelDTO> dtos = new ArrayList<>();
        PageInfo<TspEquipmentPageListDTO> voList = getPageList(vo);
        voList.getList().forEach(item -> {
            TspEquipmentExcelDTO dto = new TspEquipmentExcelDTO();
            BeanUtils.copyProperties(item, dto);
            switch (item.getOperator().intValue()) {
                case 1:
                    dto.setOperator("移动");
                    break;
                case 2:
                    dto.setOperator("联通");
                    break;
                case 3:
                    dto.setOperator("电信");
                    break;
                default:
                    dto.setOperator("未知");
                    break;
            }
            TspEquipmentModel equipmentModel = this.tspEquipmentModelRepository.getByIdContainsDelete(item.getTspEquipmentModelId());
            if (equipmentModel == null)
                ErrorEnum.TSP_EQUIPMENT_NULL_ERR.throwErr();
            dto.setModelName(equipmentModel.getModelName());
            dtos.add(dto);
        });
        return dtos;
    }

    public String importEquipment(MultipartFile multipartFile, Boolean isUpdateSupport) throws IOException {
        try {
            log.info("导入设备中------{},{}", multipartFile, isUpdateSupport);
            ExcelUtil<TspEquipmentExcelDTO> util = new ExcelUtil(TspEquipmentExcelDTO.class);
            List<TspEquipmentExcelDTO> dtos = util.importExcel(multipartFile.getInputStream());
            if (StringUtils.isNull(dtos) || dtos.size() == 0)
                throw new ServiceException("导入数据不能为空");
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            for (TspEquipmentExcelDTO dto : dtos) {
                try {
                    Map<String, Object> checkMap = toCheckEquipment(dto, failureMsg, failureNum);
                    failureNum = ((Integer) checkMap.get("failureNum")).intValue();
                    failureMsg = (StringBuilder) checkMap.get("failureMsg");
                    if (failureNum == 0) {
                        TspEquipmentType equipmentType = this.tspEquipmentTypeRepository.getByNameAndExtraType(dto.getName(), dto.getExtraType());
                        if (Objects.isNull(equipmentType)) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、设备分类|设备扩展信息类型").append(dto.getName()).append("/").append(dto.getExtraType()).append("不存在");
                            continue;
                        }
                        TspEquipmentModel equipmentModel = this.tspEquipmentModelRepository.getByModelNameAndType(dto.getModelName(), equipmentType.getId());
                        if (Objects.isNull(equipmentModel)) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、设备型号").append(dto.getName()).append("不存在");
                        }
                        TspEquipment equipment = this.tspEquipmentRepository.getByDTO(dto);
                        if (Objects.isNull(equipment)) {
                            equipment = new TspEquipment();
                            BeanUtils.copyProperties(dto, equipment);
                            if ("电信".equals(dto.getOperator()))
                                equipment.setOperator(Integer.valueOf(3));
                            if ("移动".equals(dto.getOperator()))
                                equipment.setOperator(Integer.valueOf(1));
                            if ("联通".equals(dto.getOperator()))
                                equipment.setOperator(Integer.valueOf(2));
                            equipment.setCreateBy(SecurityUtils.getUsername());
                            equipment.setUpdateBy(SecurityUtils.getUsername());
                            equipment.setTspEquipmentModelId(equipmentModel.getId());
                            equipment.setIsScrap(Boolean.valueOf(false));
                            equipment.setIsTerminal(1);
                            this.tspEquipmentRepository.save(equipment);
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、设备").append(dto.getSn()).append("导入成功");
                            continue;
                        }
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、设备信息已被使用");
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、设备" + dto.getName() + "导入失败";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉,导入失败！共" + failureNum + "条数据格式不正确,错误如下:");
                throw new ServiceException(failureMsg.toString());
            }
            successMsg.insert(0, "恭喜您,数据已全部导入成功！共" + successNum + "条,数据如下:");
            return successMsg.toString();
        } catch (Throwable $ex) {
            throw $ex;
        }
    }

    public JsonResult scrap(TspEquipmentScrapVO vo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (!SecurityUtils.matchesPassword(vo.getPassword(), loginUser.getPassword()))
            ErrorEnum.TSP_VEHICLE_SCRAP_ERR.throwErr();
        for (Long tspEquipmentId : vo.getTspEquipmentIds()) {
            TspEquipment tspEquipment = tspEquipmentRepository.getById(tspEquipmentId);
            List<TspVehicle> tspVehicle = tspVehicleRepository.findByTspEquipmentId(tspEquipmentId);
            if (!CollectionUtils.isEmpty(tspVehicle) && tspVehicle.size() != 0)
                ErrorEnum.TSP_EQUIPMENT_SCRAP_ERR.throwErr();
            tspEquipment.setScrapTime(LocalDateTime.now());
            tspEquipment.setIsScrap(Boolean.valueOf(true));
            tspEquipment.setUpdateBy(SecurityUtils.getUsername());
            this.tspEquipmentRepository.updateById(tspEquipment);
        }
        return JsonResult.getResult(true);
    }


    private <T> Object readListFromCache(String key, String hashKey) {
        try {
            Object o = redisConfig.redisTemplate(this.redisConnectionFactory).opsForHash().get(key, hashKey);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static LocalDateTime convertToLocalDateTime(JSONArray collectTimeJSONArray) {
        log.info("转换本地时间--------{}", collectTimeJSONArray);
        try {
            int h = (collectTimeJSONArray.size() > 3) ? ((Integer) collectTimeJSONArray.get(3)).intValue() : 0;
            int m = (collectTimeJSONArray.size() > 4) ? ((Integer) collectTimeJSONArray.get(4)).intValue() : 0;
            int s = (collectTimeJSONArray.size() > 5) ? ((Integer) collectTimeJSONArray.get(5)).intValue() : 0;
            LocalDateTime collectTime = LocalDateTime.of(((Integer) collectTimeJSONArray.get(0)).intValue(), Month.of(((Integer) collectTimeJSONArray.get(1)).intValue()), ((Integer) collectTimeJSONArray.get(2)).intValue(), h, m, s, 0);
            return collectTime;
        } catch (Exception e) {
            e.printStackTrace();
            return LocalDateTime.MIN;
        }
    }

    private Boolean calcVehicleStateExt(VehicleIntegrate dto, LocalDateTime collectTime, LocalDateTime now) {
        if (dto != null && collectTime != null) {
            Duration duration = Duration.between(collectTime, now);
            if (duration.toMillis() > 60000L) return Boolean.valueOf(false);
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    private Map<String, Object> toCheckEquipment(TspEquipmentExcelDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        String snRegexp = "^.{1,24}$";
        if (dto.getSn() == null || !dto.getSn().matches(snRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备SN").append(dto.getSn()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String versionRegexp = "^([0-9A-Za-z]){1,10}(-([0-9A-Za-z]){1,10}){2}$";
        if (dto.getVersion() == null || !dto.getVersion().matches(versionRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备版本号").append(dto.getVersion()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String iccidRegexp = "^([A-Za-z0-9]){19}$";
        if (dto.getIccId() == null || !dto.getIccId().matches(iccidRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备ICCID").append(dto.getIccId()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String imsiRegexp = "^([0-9]){1,15}$";
        if (dto.getImsi() == null || !dto.getImsi().matches(imsiRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备IMSI").append(dto.getImsi()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String simRegexp = "^([0-9]){11}$";
        if (dto.getSim() == null || !dto.getSim().matches(simRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备SIM").append(dto.getSim()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String imeiRegexp = "^([0-9]){15}$";
        if (dto.getImei() == null || !dto.getImei().matches(imeiRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、设备IMEI").append(dto.getImei()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getSerialNumber() == null || !dto.getSerialNumber().matches(snRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、批次流水号").append(dto.getSerialNumber()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getSupplierCode() == null || "".equals(dto.getSupplierCode())) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、供应商代码").append(dto.getSupplierCode()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getOperator() == null || (!dto.getOperator().equals("移动") && !dto.getOperator().equals("联通") && !dto.getOperator().equals("电信"))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车联网运营商卡").append(dto.getOperator()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", Integer.valueOf(failureNum));
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }


}
