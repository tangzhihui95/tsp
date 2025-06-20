package com.modern.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.BaseModel;
import com.modern.common.core.domain.Result;
import com.modern.common.core.domain.entity.SysRole;
import com.modern.common.core.domain.model.LoginUser;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.DateUtils;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.domain.*;
import com.modern.entity.VehicleIntegrate;
import com.modern.enums.TspVehicleStateEnum;
import com.modern.framework.config.RedisConfig;
import com.modern.mapper.*;
import com.modern.model.dto.*;
import com.modern.model.vo.*;
import com.modern.repository.*;
import com.modern.system.service.ISysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspVehicleService
 * @Date：2024/10/16 14:49
 * @Filename：TspVehicleService
 */
@Service
public class TspVehicleService extends TspBaseService {
    private static final Logger log = LoggerFactory.getLogger(TspBaseService.class);
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private TspCarRoleRelationService tspCarRoleRelationService;
    @Resource
    private TspEquipmentMapper tspEquipmentMapper;
    @Autowired
    private TspVehicleRepository tspVehicleRepository;
    @Resource
    private TspVehicleMapper tspVehicleMapper;
    @Autowired
    private TspVehicleLicenseRepository tspVehicleLicenseRepository;
    @Autowired
    private TspVehicleStdModeRepository tspVehicleStdModeRepository;
    @Autowired
    private TspTagRepository tspTagRepository;
    @Autowired
    private TspVehicleEquipmentRepository tspVehicleEquipmentRepository;
    @Autowired
    private TspVehicleLicenseRecordService tspVehicleLicenseRecordService;
    @Autowired
    private TspVehicleMarketRepository tspVehicleMarketRepository;
    @Autowired
    private TspVehicleOtherRepository tspVehicleOtherRepository;
    @Autowired
    private TspUserRepository tspUserRepository;
    @Autowired
    private TspVehicleAuditRepository tspVehicleAuditRepository;
    @Autowired
    private TspUseVehicleRecordService tspUseVehicleRecordService;
    @Autowired
    private TspVehicleAuditService tspVehicleAuditService;
    @Autowired
    private TspUserVehicleRepository tspUserVehicleRepository;
    @Autowired
    private TspVehicleModelRepository tspVehicleModelRepository;
    @Autowired
    private TspEquipmentRepository tspEquipmentRepository;
    @Resource
    private TspVehicleStdModeMapper tspVehicleStdModeMapper;
    @Resource
    private TspDealerMapper tspDealerMapper;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    RedisConfig redisConfig;
    @Resource
    private TspVehicleEquipmentMapper tspVehicleEquipmentMapper;


    public PageInfo<TspVehiclePageListDTO> getPageList(TspVehiclePageListVO vo) {
        log.info("车辆信息列表查询入参--------{}", vo);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<SysRole> sysRoles = roleService.selectRolesByUserId(loginUser.getUserId());
        List<SysRole> collect = sysRoles.stream().filter(p -> ("admin".equals(p.getRoleKey()) && p.isFlag())).collect(Collectors.toList());
        if (collect.size() == 0) {
            Set<Long> roleIds = sysRoles.stream().filter(SysRole::isFlag).map(SysRole::getRoleId).collect(Collectors.toSet());
            if (CollectionUtil.isNotEmpty(roleIds)) {
                List<Long> carIds = tspCarRoleRelationService.queryCarIdByRoleIds(roleIds);
                vo.setCarIds(carIds);
            }
        }
        List<Long> bindEquipmentIds = tspEquipmentMapper.getBindEquipments();
        vo.setTspEquipmentIds(bindEquipmentIds);
        QueryWrapper<TspVehicle> ew = tspVehicleRepository.getPageListEw(vo);
        Page<TspVehiclePageListDTO> p = Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue());
        p.setOptimizeCountSql(false);
        IPage<TspVehiclePageListDTO> page = tspVehicleMapper.getPageList(p, ew);
        for (TspVehiclePageListDTO record : page.getRecords()) {
            TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(record.getId());
            if (Objects.isNull(license))
                license = tspVehicleLicenseRepository.getByPlateCode(record.getPlateCode());
            if (Objects.nonNull(license))
                record.setPlateCode((license.getPlateCode() == null) ? null : license.getPlateCode());
            TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(record.getTspVehicleStdModelId());
            if (Objects.nonNull(stdModel))
                record.setStdModeName((stdModel.getStdModeName() == null) ? null : stdModel.getStdModeName());
            if (Objects.isNull(record.getTspEquipmentId())) {
                record.setBindStatus("未绑定");
                continue;
            }
            record.setBindStatus("已绑定");
        }
        log.info("车辆信息列表查询出参--------{}", page);
        return PageInfo.of(page, page.getSize());
    }

    @Transactional(rollbackFor = {ServiceException.class})
    public TspVehicleInfoDTO add(TspVehicleAddVO vo) {
        log.info("车辆信息-新增入参--------TspVehicleAddVO{}", vo);
        TspVehicle tspVehicle = tspVehicleRepository.getByVin(vo.getVin());
        if (Objects.nonNull(tspVehicle))
            ErrorEnum.TSP_VEHICLE_VIN_EXIST.throwErr();
        if (null != vo.getTspEquipmentId()) {
            Long tspVehicleId = tspVehicleMapper.getByEquipmentId(vo.getTspEquipmentId());
            if (null != tspVehicleId)
                ErrorEnum.TSP_VEHICLE_EQUIPMENT_EXIST.throwErr();
        }
        tspVehicleRepository.isProgressCheck(vo);
        tspVehicleRepository.isUserCheck(vo);
        tspVehicle = new TspVehicle();
        tspVehicle.setUpdateBy(SecurityUtils.getUsername());
        tspVehicle.setCreateBy(SecurityUtils.getUsername());
        tspVehicle.setState(TspVehicleStateEnum.CREATED.getValue());
        tspVehicle.setCreateTime(DateUtils.getCurrentTime());
        BeanUtils.copyProperties(vo, tspVehicle);
        if (null != vo.getLabel() && vo.getLabel().size() != 0) {
            for (Long tspTagId : vo.getLabel()) {
                TspTag tspTag = tspTagRepository.getById(tspTagId);
                tspTag.setTagCount(Integer.valueOf(tspTag.getTagCount().intValue() + 1));
                tspTagRepository.updateById(tspTag);
            }
            tspVehicle.setLabel(vo.getLabel().toString());
        }
        tspVehicleRepository.save(tspVehicle);
        if (null != tspVehicle.getTspEquipmentId() && null != tspVehicle.getId()) {
            TspVehicleEquipment vehicleEquipment = new TspVehicleEquipment();
            vehicleEquipment.setTspVehicleId(tspVehicle.getId());
            vehicleEquipment.setTspEquipmentId(tspVehicle.getTspEquipmentId());
            vehicleEquipment.setCreateTime(DateUtils.getCurrentTime());
            tspVehicleEquipmentRepository.save(vehicleEquipment);
        }
        TspVehicleLicense tspVehicleLicense = tspVehicleLicenseRepository.getByPlateCode(vo.getPlateCode());
        if (Objects.nonNull(tspVehicleLicense)) {
            ErrorEnum.TSP_VEHICLE_PLATE_CODE_EXIST.throwErr();
        } else {
            TspVehicleLicenseRecordAddVO recordAddVO = new TspVehicleLicenseRecordAddVO();
            BeanUtils.copyProperties(vo, recordAddVO);
            recordAddVO.setTspVehicleId(tspVehicle.getId());
            log.info("生成上牌记录--------recordAddVO{}", recordAddVO);
            tspVehicleLicenseRecordService.add(recordAddVO);
        }
        TspVehicleInfoDTO dto = new TspVehicleInfoDTO();
        BeanUtils.copyProperties(tspVehicle, dto);
        return dto;
    }

    @Transactional(rollbackFor = {Exception.class})
    public Result edit(TspVehicleAddVO vo) {
        TspVehicleMarket market;
        TspVehicleOther other;
        TspVehicleLicense license;
        String placeCode;
        List<TspVehicleLicense> existLicenseList;
        TspVehicleLicenseRecordAddVO recordAddVO;
        TspUser byMobile, one;
        TspVehicleAudit vehicleAudit;
        TspUseVehicleRecordAddVO vehicleRecordAddVO;
        log.info("车辆编辑入参--------TspVehicleAddVO {}", vo);
        if (null == vo.getTspVehicleId())
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        Long tspVehicleId = tspVehicleMapper.getByEquipmentId(vo.getTspEquipmentId());
        if (null != tspVehicleId && !vo.getTspVehicleId().equals(tspVehicleId))
            ErrorEnum.TSP_VEHICLE_EQUIPMENT_EXIST.throwErr();
        TspVehicle vehicle = tspVehicleRepository.getById(vo.getTspVehicleId());
        if (Objects.isNull(vehicle))
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        List<TspVehicleEquipment> tspEquipmentIds = tspVehicleEquipmentRepository.getByVehicleId(vehicle.getId());
        if (null != vo.getTspEquipmentId() && CollectionUtil.isNotEmpty(tspEquipmentIds) && tspEquipmentIds.size() != 0)
            if (vehicle.getSendStatus().intValue() == 1)
                if (!vo.getTspEquipmentId().equals((tspEquipmentIds.get(0)).getTspEquipmentId()))
                    if (vehicle.getTspEquipmentId() != null) {
                        TspVehicleEquipment update = tspEquipmentIds.get(0);
                        update.setUnBindTime(DateUtils.getCurrentTime());
                        update.setUpdateTime(DateUtils.getCurrentTime());
                        tspVehicleEquipmentRepository.updateById(update);
                    }
        if (vo.getTspEquipmentId() != null && (tspEquipmentIds == null || tspEquipmentIds.size() == 0 || (tspEquipmentIds.get(0)).getUnBindTime() != null)) {
            TspVehicleEquipment tspVehicleEquipment = new TspVehicleEquipment();
            tspVehicleEquipment.setTspVehicleId(vehicle.getId());
            tspVehicleEquipment.setTspEquipmentId(vo.getTspEquipmentId());
            tspVehicleEquipmentRepository.save(tspVehicleEquipment);
        }
        String labelStr = vehicle.getLabel();
        if (StringUtils.isNotNull(labelStr) && !"".equals(labelStr) && !"[]".equals(labelStr)) {
            List<String> strings = Arrays.asList(labelStr.split(","));
            for (String string : strings) {
                if (string.contains("["))
                    string = string.replace("[", "");
                if (string.contains("]"))
                    string = string.replace("]", "");
                if (string.contains(" "))
                    string = string.replace(" ", "");
                Long tagId = Long.valueOf(string);
                TspTag oldTag = tspTagRepository.getById(tagId);
                oldTag.setTagCount(Integer.valueOf(oldTag.getTagCount().intValue() - 1));
                oldTag.setUpdateBy(SecurityUtils.getUsername());
                oldTag.setUpdateTime(DateUtils.getCurrentTime());
                tspTagRepository.updateById(oldTag);
            }
        }
        List<Long> newLabelList = vo.getLabel();
        if (CollectionUtil.isNotEmpty(newLabelList) && newLabelList.size() != 0)
            for (Long newLabel : newLabelList) {
                TspTag newTag = tspTagRepository.getById(newLabel);
                newTag.setTagCount(Integer.valueOf(newTag.getTagCount().intValue() + 1));
                newTag.setUpdateBy(SecurityUtils.getUsername());
                newTag.setUpdateTime(DateUtils.getCurrentTime());
                tspTagRepository.updateById(newTag);
            }
        if (vehicle.getProgress().intValue() >= 5) {
            vo.setProgress(Integer.valueOf(5));
            vo.setIsComplete(Boolean.valueOf(true));
        }
        tspVehicleRepository.isProgressCheck(vo);
        tspVehicleRepository.isUserCheck(vo);
        BeanUtils.copyProperties(vo, vehicle);
        vehicle.setLabel((vo.getLabel() == null) ? null : vo.getLabel().toString());
        switch (vo.getProgress().intValue()) {
            case 2:
                market = tspVehicleMarketRepository.getByTspVehicleId(vo.getTspVehicleId());
                other = tspVehicleOtherRepository.getByTspVehicleId(vo.getTspVehicleId());
                if (Objects.nonNull(market)) {
                    if (vehicle.getState().intValue() < TspVehicleStateEnum.SOLD.getValue().intValue())
                        vehicle.setState(TspVehicleStateEnum.SOLD.getValue());
                    BeanUtils.copyProperties(vo, market);
                    market.setUpdateBy(SecurityUtils.getUsername());
                    market.setUpdateTime(DateUtils.getCurrentTime());
                    tspVehicleMarketRepository.updateById(market);
                } else {
                    market = new TspVehicleMarket();
                    BeanUtils.copyProperties(vo, market);
                    market.setCreateBy(SecurityUtils.getUsername());
                    market.setUpdateBy(SecurityUtils.getUsername());
                    market.setCreateTime(DateUtils.getCurrentTime());
                    tspVehicleMarketRepository.save(market);
                }
                if (Objects.nonNull(other)) {
                    BeanUtils.copyProperties(vo, other);
                    other.setUpdateBy(SecurityUtils.getUsername());
                    other.setUpdateTime(DateUtils.getCurrentTime());
                    tspVehicleOtherRepository.updateById(other);
                    break;
                }
                other = new TspVehicleOther();
                BeanUtils.copyProperties(vo, other);
                other.setCreateBy(SecurityUtils.getUsername());
                other.setUpdateBy(SecurityUtils.getUsername());
                other.setCreateTime(DateUtils.getCurrentTime());
                tspVehicleOtherRepository.save(other);
                break;
            case 3:
                license = tspVehicleLicenseRepository.getByTspVehicleId(vo.getTspVehicleId());
                placeCode = StringUtils.isBlank(vo.getPlateCodeName()) ? "" : vo.getPlateCodeName();
                placeCode = placeCode + (StringUtils.isBlank(vo.getPlateCode()) ? "" : vo.getPlateCode());
                placeCode = StringUtils.isBlank(placeCode) ? null : placeCode;
                existLicenseList = ((tspVehicleLicenseRepository.lambdaQuery().ne(TspVehicleLicense::getTspVehicleId,
                        vo.getTspVehicleId())).eq(TspVehicleLicense::getPlateCode, placeCode)).list();
                if (placeCode != null && CollectionUtil.isNotEmpty(existLicenseList))
                    ErrorEnum.TSP_VEHICLE_PLATE_CODE_EXIST.throwErr();
                if (Objects.nonNull(license)) {
                    BeanUtils.copyProperties(vo, license);
                    license.setUpdateBy(SecurityUtils.getUsername());
                    license.setPlateCode(placeCode);
                    license.setCreateTime(DateUtils.getCurrentTime());
                    license.setPlateImgUrls(vo.getPlateImgUrls());
                    tspVehicleLicenseRepository.saveOrUpdate(license);
                    TspVehicleLicenseRecordAddVO tspVehicleLicenseRecordAddVO = new TspVehicleLicenseRecordAddVO();
                    BeanUtils.copyProperties(vo, tspVehicleLicenseRecordAddVO);
                    tspVehicleLicenseRecordService.add(tspVehicleLicenseRecordAddVO);
                    break;
                }
                license = new TspVehicleLicense();
                BeanUtils.copyProperties(vo, license);
                license.setUpdateBy(SecurityUtils.getUsername());
                license.setCreateBy(SecurityUtils.getUsername());
                license.setPlateCode(placeCode);
                license.setCreateTime(DateUtils.getCurrentTime());
                tspVehicleLicenseRepository.save(license);
                recordAddVO = new TspVehicleLicenseRecordAddVO();
                BeanUtils.copyProperties(vo, recordAddVO);
                tspVehicleLicenseRecordService.add(recordAddVO);
                break;
            case 4:
                byMobile = new TspUser();
                BeanUtils.copyProperties(vo, byMobile);
                one = (TspUser) ((LambdaQueryChainWrapper) ((LambdaQueryChainWrapper) tspUserRepository.lambdaQuery().orderByDesc(BaseModel::getCreateTime,
                        new com.baomidou.mybatisplus.core.toolkit.support.SFunction[0])).last("limit 1")).one();
                if (Objects.isNull(one) || !Objects.equals(one.getRealName(), byMobile.getRealName()) || !Objects.equals(one.getMobile(), byMobile.getMobile()) || !Objects.equals(one.getIdCard(), byMobile.getIdCard())) {
                    tspUserRepository.save(byMobile);
                } else {
                    byMobile.setId(one.getId());
                }
                vehicleAudit = tspVehicleAuditRepository.getById(vo.getTspVehicleAuditId());
                if (Objects.isNull(vehicleAudit)) {
                    vehicleAudit = new TspVehicleAudit();
                    BeanUtils.copyProperties(vo, vehicleAudit);
                    vehicleAudit.setTspUserId(byMobile.getId());
                    vehicleAudit.setApplyTime(DateUtils.getCurrentTime());
                    vehicleAudit.setCreateBy(SecurityUtils.getUsername());
                    vehicleAudit.setCreateTime(DateUtils.getCurrentTime());
                    vehicleAudit.setUpdateBy(SecurityUtils.getUsername());
                    tspVehicleAuditRepository.save(vehicleAudit);
                } else {
                    BeanUtils.copyProperties(vo, vehicleAudit);
                    vehicleAudit.setUpdateBy(SecurityUtils.getUsername());
                    vehicleAudit.setUpdateTime(DateUtils.getCurrentTime());
                    tspVehicleAuditRepository.updateById(vehicleAudit);
                }
                vehicleRecordAddVO = new TspUseVehicleRecordAddVO();
                BeanUtils.copyProperties(vo, vehicleRecordAddVO);
                vehicleRecordAddVO.setTspUserId(byMobile.getId());
                if (Objects.isNull(one) || !Objects.equals(one.getId(), byMobile.getId()))
                    tspUseVehicleRecordService.add(vehicleRecordAddVO);
                if (vehicle.getState().intValue() < TspVehicleStateEnum.BOUND.getValue().intValue())
                    vehicle.setState(TspVehicleStateEnum.BOUND.getValue());
                vehicle.setTspUserId(byMobile.getId());
                vehicle.setIsComplete(Boolean.valueOf(true));
                vehicle.setUpdateBy(SecurityUtils.getUsername());
                vehicle.setCurrentBindTime(DateUtils.getCurrentTime());
                vehicle.setUpdateTime(DateUtils.getCurrentTime());
                tspVehicleRepository.updateById(vehicle);
                break;
        }
        return Result.ok(tspVehicleRepository.updateById(vehicle));
    }

    @Transactional(rollbackFor = {Exception.class})
    public JsonResult delete(Long tspVehicleId) {
        log.info("根据车辆ID进行删除---------tspVehicleId {}", tspVehicleId);
        TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
        if (Objects.isNull(tspVehicle))
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        Long equipmentId = tspVehicleMapper.getByVehicleId(tspVehicleId);
        if (StringUtils.isNotNull(equipmentId))
            ErrorEnum.TSP_VEHICLE_EQUIPMENT_BIND_DELETE_ERR.throwErr();
        if (!Objects.equals(TspVehicleStateEnum.ALL.getValue(), tspVehicle.getState()) && (
                Objects.equals(TspVehicleStateEnum.SOLD.getValue(), tspVehicle.getState()) ||
                        Objects.equals(TspVehicleStateEnum.BOUND.getValue(), tspVehicle.getState()) ||
                        Objects.equals(TspVehicleStateEnum.SCRAPPED.getValue(), tspVehicle.getState()) ||
                        Objects.equals(TspVehicleStateEnum.ALREADY.getValue(), tspVehicle.getState())))
            ErrorEnum.TSP_VEHICLE_DELETE_STATE_ERR.throwErr();
        TspVehicleAudit vehicleAudit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
        if (Objects.nonNull(vehicleAudit))
            tspVehicleAuditRepository.removeById((Serializable) vehicleAudit);
        tspVehicleRepository.removeById(tspVehicleId);
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicle.getId());
        if (Objects.nonNull(license))
            tspVehicleLicenseRepository.removeById(license.getId());
        String labelStr = tspVehicle.getLabel();
        if (StringUtils.isNotNull(labelStr) && !"".equals(labelStr) && !"[]".equals(labelStr)) {
            List<String> strings = Arrays.asList(labelStr.split(","));
            for (String string : strings) {
                if (string.contains("["))
                    string = string.replace("[", "");
                if (string.contains("]"))
                    string = string.replace("]", "");
                if (string.contains(" "))
                    string = string.replace(" ", "");
                Long tagId = Long.valueOf(string);
                TspTag oldTag = tspTagRepository.getById(tagId);
                oldTag.setTagCount(Integer.valueOf(oldTag.getTagCount().intValue() - 1));
                oldTag.setUpdateBy(SecurityUtils.getUsername());
                oldTag.setUpdateTime(DateUtils.getCurrentTime());
                tspTagRepository.updateById(oldTag);
            }
        }
        return JsonResult.getResult(true);
    }

    @Transactional(rollbackFor = {Exception.class})
    public JsonResult deletes(Long[] tspVehicleIds) {
        log.info("根据ids进行批量删除------------ tspVehicleIds {}", (Object[]) tspVehicleIds);
        for (Long tspVehicleId : tspVehicleIds) {
            TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
            if (Objects.isNull(tspVehicle))
                ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
            Long equipmentId = tspVehicleMapper.getByVehicleId(tspVehicleId);
            if (StringUtils.isNotNull(equipmentId))
                ErrorEnum.TSP_VEHICLE_EQUIPMENT_BIND_DELETES_ERR.throwErr();
            if (!Objects.equals(TspVehicleStateEnum.ALL.getValue(), tspVehicle.getState()) && (
                    Objects.equals(TspVehicleStateEnum.SOLD.getValue(), tspVehicle.getState()) ||
                            Objects.equals(TspVehicleStateEnum.BOUND.getValue(), tspVehicle.getState()) ||
                            Objects.equals(TspVehicleStateEnum.SCRAPPED.getValue(), tspVehicle.getState()) ||
                            Objects.equals(TspVehicleStateEnum.ALREADY.getValue(), tspVehicle.getState())))
                ErrorEnum.TSP_VEHICLE_DELETE_STATE_ERR.throwErr();
            TspVehicleAudit vehicleAudit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
            if (Objects.nonNull(vehicleAudit))
                tspVehicleAuditRepository.removeById((Serializable) vehicleAudit);
            if (null != tspVehicle.getTspUserId()) {
                Long userId = tspVehicle.getTspUserId();
                TspUser tspUser = tspUserRepository.getById(userId);
                List<TspVehicle> vehicles = tspVehicleRepository.findByTspUserId(userId);
                if (vehicles.size() <= 0) {
                    tspUser.setHasBind(Integer.valueOf(0));
                    tspUser.setUpdateBy(SecurityUtils.getUsername());
                    tspUser.setUpdateTime(DateUtils.getCurrentTime());
                    tspUserRepository.save(tspUser);
                }
            }
            TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicle.getId());
            if (Objects.nonNull(license))
                tspVehicleLicenseRepository.removeById(license.getId());
            tspVehicleRepository.removeById(tspVehicleId);
            String labelStr = tspVehicle.getLabel();
            if (StringUtils.isNotNull(labelStr) && !"".equals(labelStr) && !"[]".equals(labelStr)) {
                List<String> strings = Arrays.asList(labelStr.split(","));
                for (String string : strings) {
                    if (string.contains("["))
                        string = string.replace("[", "");
                    if (string.contains("]"))
                        string = string.replace("]", "");
                    if (string.contains(" "))
                        string = string.replace(" ", "");
                    Long tagId = Long.valueOf(string);
                    TspTag oldTag = tspTagRepository.getById(tagId);
                    oldTag.setTagCount(Integer.valueOf(oldTag.getTagCount().intValue() - 1));
                    oldTag.setUpdateBy(SecurityUtils.getUsername());
                    oldTag.setUpdateTime(DateUtils.getCurrentTime());
                    tspTagRepository.updateById(oldTag);
                }
            }
        }
        return JsonResult.getResult(true);
    }

    public JsonResult bind(Long tspVehicleId, Long tspUserId) {
        log.info("根据车辆id和用户id进行车辆绑定-------{},{}", tspVehicleId, tspUserId);
        TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
        TspUser tspUser = tspUserRepository.getById(tspUserId);
        if (Objects.isNull(tspUser))
            ErrorEnum.TSP_USER_USER_NULL_ERR.throwErr();
        if (Objects.isNull(tspVehicle))
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        if (!tspVehicle.getState().equals(TspVehicleStateEnum.SOLD.getValue()))
            ErrorEnum.TSP_VEHICLE_SOLD_NULL_ERR.throwErr();
        log.info("开始进行绑定中--------");
        tspVehicle.setState(TspVehicleStateEnum.BOUND.getValue());
        tspVehicle.setTspUserId(tspUserId);
        tspVehicle.setCurrentBindTime(DateUtils.getCurrentTime());
        log.info("开始生成绑定记录中-----");
        TspUseVehicleRecordAddVO recordAddVO = new TspUseVehicleRecordAddVO();
        BeanUtils.copyProperties(tspUser, recordAddVO);
        tspUseVehicleRecordService.add(recordAddVO);
        TspUserVehicle tspUserVehicle = new TspUserVehicle();
        tspUserVehicle.setTspVehicleId(tspVehicleId);
        tspUserVehicle.setTspUserId(tspUserId);
        tspUserVehicle.setCreateBy(SecurityUtils.getUsername());
        tspUserVehicle.setUpdateBy(SecurityUtils.getUsername());
        tspUserVehicle.setCreateTime(DateUtils.getCurrentTime());
        return JsonResult.getResult(tspUserVehicleRepository.save(tspUserVehicle));
    }

    public JsonResult scrap(TspVehicleScrapVO vo) {
        log.info("车辆开始报废入参------------TspVehicleScrapVO={}", vo);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (!SecurityUtils.matchesPassword(vo.getPassword(), loginUser.getPassword()))
            ErrorEnum.TSP_VEHICLE_SCRAP_ERR.throwErr();
        for (Long tspVehicleId : vo.getTspVehicleIds()) {
            TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
            if (null != tspVehicle.getTspEquipmentId())
                ErrorEnum.TSP_EQUIPMENT_SCRAP_ERR.throwErr();
            tspVehicle.setScrapTime(LocalDateTime.now());
            tspVehicle.setState(TspVehicleStateEnum.SCRAPPED.getValue());
            tspVehicle.setUpdateBy(SecurityUtils.getUsername());
            tspVehicle.setUpdateTime(DateUtils.getCurrentTime());
            tspVehicleRepository.updateById(tspVehicle);
        }
        return JsonResult.getResult(true);
    }

    public TspVehicleInfoDTO get(Long tspVehicleId) {
        log.info("通过车辆id获取车辆信息--------------tspVehicleId={}", tspVehicleId);
        TspVehicle vehicle = tspVehicleRepository.getById(tspVehicleId);
        TspUser tspUser = tspUserRepository.getById(vehicle.getTspUserId());
        TspVehicleAudit audit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
        TspVehicleMarket market = tspVehicleMarketRepository.getByTspVehicleId(tspVehicleId);
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicleId);
        TspVehicleOther tspVehicleOther = tspVehicleOtherRepository.getByTspVehicleId(tspVehicleId);
        TspVehicleStdModel stdModel = tspVehicleStdModeRepository.getById(vehicle.getTspVehicleStdModelId());
        TspVehicleModel model = null;
        if (Objects.nonNull(stdModel))
            model = tspVehicleModelRepository.getById(stdModel.getTspVehicleModelId());
        TspVehicleInfoDTO dto = new TspVehicleInfoDTO();
        if (Objects.nonNull(audit)) {
            BeanUtils.copyProperties(audit, dto);
            dto.setTspVehicleAuditId(audit.getId());
        }
        if (Objects.nonNull(tspUser))
            BeanUtils.copyProperties(tspUser, dto);
        if (Objects.nonNull(market))
            BeanUtils.copyProperties(market, dto);
        if (Objects.nonNull(license)) {
            BeanUtils.copyProperties(license, dto);
            if (StringUtils.isNotBlank(dto.getPlateCode())) {
                String substring = dto.getPlateCode().substring(0, 1);
                dto.setPlateCodeName(substring);
                int len = dto.getPlateCode().length();
                dto.setPlateCode(dto.getPlateCode().substring(1, len));
            }
        }
        if (Objects.nonNull(tspVehicleOther))
            BeanUtils.copyProperties(tspVehicleOther, dto);
        if (Objects.nonNull(model))
            BeanUtils.copyProperties(model, dto);
        if (Objects.nonNull(stdModel))
            BeanUtils.copyProperties(stdModel, dto);
        BeanUtils.copyProperties(vehicle, dto);
        String label = vehicle.getLabel();
        if (null != label && !"".equals(label) && !"[]".equals(label)) {
            List<String> strings = Arrays.asList(label.split(","));
            List<Long> labelLong = new ArrayList<>();
            for (String string : strings) {
                if (string.contains("["))
                    string = string.replace("[", "");
                if (string.contains("]"))
                    string = string.replace("]", "");
                if (string.contains(" "))
                    string = string.replace(" ", "");
                labelLong.add(Long.valueOf(string));
            }
            dto.setLabel(labelLong);
        }
        dto.setTspVehicleId(tspVehicleId);
        return dto;
    }

    public int dealEquipment(Long tspEquipmentId) {
        log.info("根据设备ID进行设备解绑--------------TspEquipmentId={}", tspEquipmentId);
        Long tspVehicleId = tspVehicleMapper.getByEquipmentId(tspEquipmentId);
        if (null == tspVehicleId)
            ErrorEnum.TSP_VEHICLE_VEHICLE_NULL_ERR.throwErr();
        List<TspVehicleEquipment> tspVehicleEquipments = tspVehicleEquipmentRepository.getByEquipmentId(tspVehicleId, tspEquipmentId);
        if (null != tspVehicleEquipments && tspVehicleEquipments.size() != 0) {
            TspVehicleEquipment tspVehicleEquipment = tspVehicleEquipments.get(0);
            tspVehicleEquipment.setUnBindTime(DateUtils.getCurrentTime());
            tspVehicleEquipment.setUpdateTime(DateUtils.getCurrentTime());
            tspVehicleEquipmentRepository.updateById(tspVehicleEquipment);
        }
        Integer state = TspVehicleStateEnum.UNBOUND.getValue();
        tspVehicleMapper.updateSetState(state, tspVehicleId);
        return tspVehicleMapper.updateSetNull(tspVehicleId);
    }

    public List<TspVehicleExportListDTO> exportList(TspVehiclePageListVO vo) {
        log.info("导入车辆信息开始----------------TspVehiclePageListVO={}", vo);
        vo.setPageNum(Integer.valueOf(1));
        vo.setPageSize(Integer.valueOf(2147483647));
        List<TspVehicleExportListDTO> dtos = new ArrayList<>();
        getPageList(vo).getList().forEach(pageListDTO -> {
            TspVehicleExportListDTO dto = new TspVehicleExportListDTO();
            BeanUtils.copyProperties(pageListDTO, dto);
            switch (pageListDTO.getCertificationState().intValue()) {
                case 1:
                    dto.setCertificationState("未认证");
                    break;
                case 2:
                    dto.setCertificationState("认证中");
                    break;
                case 3:
                    dto.setCertificationState("认证失败");
                    break;
                case 4:
                    dto.setCertificationState("已认证");
                    break;
                default:
                    dto.setCertificationState("全部");
                    break;
            }
            dto.setCertificationState(String.valueOf(pageListDTO.getCertificationState()));
            switch (pageListDTO.getState().intValue()) {
                case 1:
                    dto.setCertificationState("已创建");
                    break;
                case 2:
                    dto.setCertificationState("已销售");
                    break;
                case 3:
                    dto.setCertificationState("已绑定");
                    break;
                case 4:
                    dto.setCertificationState("已解绑");
                    break;
                case 5:
                    dto.setCertificationState("已报废");
                    break;
                case 6:
                    dto.setCertificationState("已注册");
                    break;
                default:
                    dto.setCertificationState("全部");
                    break;
            }
            dto.setState(String.valueOf(pageListDTO.getState()));
            switch (pageListDTO.getState().intValue()) {
                case 1:
                    dto.setCertificationState("已推送");
                    break;
                default:
                    dto.setCertificationState("未推送");
                    break;
            }
            dto.setSendStatus(String.valueOf(pageListDTO.getSendStatus()));
            dto.setCreateTime(pageListDTO.getCreateTime());
            dtos.add(dto);
        });
        return dtos;
    }

    @Transactional(rollbackFor = {Exception.class})
    public String importVehicle(MultipartFile multipartFile, Boolean isUpdateSupport) {
        try {
            log.info("正在开始导入出厂信息--------------MultipartFile= {}", multipartFile);
            ExcelUtil<TspVehicleExFactoryTemplateDTO> util = new ExcelUtil(TspVehicleExFactoryTemplateDTO.class);
            List<TspVehicleExFactoryTemplateDTO> dtos = util.importExcel(multipartFile.getInputStream());
            if (StringUtils.isNull(dtos) || dtos.size() == 0)
                throw new ServiceException("导入数据不能为空！");
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            for (TspVehicleExFactoryTemplateDTO dto : dtos) {
                try {
                    log.info("数据校验开始--------------");
                    Map<String, Object> checkMap = toCheckExport(dto, failureMsg, failureNum);
                    failureNum = ((Integer) checkMap.get("failureNum")).intValue();
                    failureMsg = (StringBuilder) checkMap.get("failureMsg");
                    if (failureNum == 0) {
                        TspEquipment tspEquipment = tspEquipmentRepository.getByName(dto.getSn());
                        if (Objects.isNull(tspEquipment) && null != dto.getSn() && !dto.getSn().equals("")) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("出厂信息设备").append(dto.getSn()).append("不存在");
                            continue;
                        }
                        if (Objects.nonNull(tspEquipment)) {
                            List<TspVehicle> byTspEquipmentId = tspVehicleRepository.findByTspEquipmentId(tspEquipment.getId());
                            if (CollectionUtil.isNotEmpty(byTspEquipmentId) && byTspEquipmentId.size() != 0) {
                                failureNum++;
                                failureMsg.append("<br/>").append(failureNum).append("、该设备").append(dto.getSn()).append("已被车辆绑定");
                            }
                            if (tspEquipment.getIsScrap().booleanValue() == true) {
                                failureNum++;
                                failureMsg.append("<br/>").append(failureNum).append("、该设备").append(dto.getSn()).append("为报废状态，不可绑定");
                            }
                        }
                        TspVehicle tspVehicle = tspVehicleRepository.getByVin(dto.getVin());
                        QueryWrapper queryWrapper = tspVehicleStdModeRepository.getByStdModeName(dto.getStdModelName(), dto.getVehicleModelName());
                        TspVehicleStdModel stdModel = tspVehicleStdModeMapper.getByStdModeName(queryWrapper);
                        if (Objects.isNull(stdModel)) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、无法匹配到已有二级车型");
                            continue;
                        }
                        if (Objects.isNull(tspVehicle)) {
                            tspVehicle = new TspVehicle();
                            BeanUtils.copyProperties(dto, tspVehicle);
                            if (null != dto.getLabel() && !"".equals(dto.getLabel())) {
                                TspTag tag = tspTagRepository.getByDealerName(dto.getLabel());
                                List<Long> label = new ArrayList<>();
                                if (Objects.nonNull(tag)) {
                                    tag.setTagCount(Integer.valueOf(tag.getTagCount().intValue() + 1));
                                    tspTagRepository.updateById(tag);
                                    label.add(tag.getId());
                                    tspVehicle.setLabel(label.toString());
                                } else {
                                    tspVehicle.setLabel(null);
                                }
                            }
                            tspVehicle.setCreateBy(SecurityUtils.getUsername());
                            tspVehicle.setUpdateBy(SecurityUtils.getUsername());
                            LocalDate exDate = LocalDate.parse(dto.getExFactoryDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            LocalDate operateDate = LocalDate.parse(dto.getOperateDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            tspVehicle.setExFactoryDate(exDate);
                            tspVehicle.setOperateDate(operateDate);
                            tspVehicle.setProgress(Integer.valueOf(1));
                            if (Objects.nonNull(tspEquipment))
                                tspVehicle.setTspEquipmentId(tspEquipment.getId());
                            tspVehicle.setTspVehicleStdModelId(stdModel.getId());
                            tspVehicleRepository.save(tspVehicle);
                            if (Objects.nonNull(tspEquipment)) {
                                TspVehicleEquipment tspVehicleEquipment = new TspVehicleEquipment();
                                tspVehicleEquipment.setTspEquipmentId(tspEquipment.getId());
                                tspVehicleEquipment.setTspVehicleId(tspVehicle.getId());
                                tspVehicleEquipment.setCreateTime(DateUtils.getCurrentTime());
                                tspVehicleEquipmentRepository.save(tspVehicleEquipment);
                            }
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、出厂信息").append(dto.getVin()).append("新增成功");
                            continue;
                        }
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、车辆信息").append(dto.getVin()).append("已存在");
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、出厂信息" + dto.getVin() + "导入失败";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉导入失败!共" + failureNum + "条数据格式不正确,错误如下:");
                throw new ServiceException(failureMsg.toString());
            }
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共" + successNum + "条，数据如下");
            return successMsg.toString();
        } catch (Throwable $ex) {
            try {
                throw $ex;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Map<String, Object> toCheckExport(TspVehicleExFactoryTemplateDTO dto, StringBuilder failureMsg, int failureNum) {
        log.info("正好校验导入信息--------------------------TspVehicleExFactoryTemplateDTO={}", dto);
        Map<String, Object> checkMap = new HashMap<>();
        String versionRegexp = "^([a-zA-Z0-9]){17}$";
        if (null == dto.getVin() || !dto.getVin().matches(versionRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、出厂信息VIN").append(dto.getVin()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getProviderName() || dto.getProviderName().equals("") || !dto.getProviderName().equals("摩登汽车有限公司")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆厂商").append(dto.getProviderName()).append("名称异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getVehicleModelName() || dto.getVehicleModelName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆一级型号").append(dto.getVehicleModelName()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getStdModelName() || dto.getStdModelName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆二级型号").append(dto.getStdModelName()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getConfigureName() || dto.getConfigureName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆配置名称").append(dto.getConfigureName()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getColor() || dto.getColor().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、外观颜色").append(dto.getColor()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getBatchNo() || dto.getBatchNo().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆批次号").append(dto.getBatchNo()).append("不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String dateRegexp = "^([0-9]{4})(-([0-1][0-9]))(-[0-3][0-9])$";
        if (null == dto.getExFactoryDate() || !dto.getExFactoryDate().matches(dateRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、出厂日期").append(dto.getExFactoryDate()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getOperateDate() || !dto.getOperateDate().matches(dateRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、下线日期").append(dto.getOperateDate()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getEssModel() || dto.getEssModel().equals("") || (!dto.getEssModel().equals("73") && !dto.getEssModel().equals("80") && !dto.getEssModel().equals("53"))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、电池包规格").append(dto.getEssModel()).append("值异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }

        String essRegexp = "^([a-zA-Z0-9]){24}$";
        if (null == dto.getEssNum() || !dto.getEssNum().matches(essRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("电池包编号").append(dto.getEssNum()).append("、格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getMotorBrand() || dto.getMotorBrand().equals("") || (!dto.getMotorBrand().equals("青山") && !dto.getMotorBrand().equals("上汽齿"))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、电动机品牌").append(dto.getMotorBrand()).append("值异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }

        String motorRegexp = "^.{1,27}$";
        if (null == dto.getMotorNum() || !dto.getMotorNum().matches(motorRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、电动机序列号").append(dto.getMotorNum()).append("格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", Integer.valueOf(failureNum));
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    @Transactional(rollbackFor = {Exception.class})
    public String importSales(MultipartFile multipartFile, Boolean isUpdateSupport) {
        try {
            log.info("导入车辆销售信息中----------------------MultipartFile={}", multipartFile);
            ExcelUtil<TspVehicleSaleTemplateDTO> util = new ExcelUtil(TspVehicleSaleTemplateDTO.class);
            List<TspVehicleSaleTemplateDTO> dtos = util.importExcel(multipartFile.getInputStream());
            if (StringUtils.isNull(dtos) || dtos.size() == 0)
                throw new ServiceException("导入销售数据不能为空");
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            for (TspVehicleSaleTemplateDTO dto : dtos) {
                try {
                    Map<String, Object> checkMap = toCheckSales(dto, failureMsg, failureNum);
                    failureNum = ((Integer) checkMap.get("failureNum")).intValue();
                    failureMsg = (StringBuilder) checkMap.get("failureMsg");
                    if (failureNum == 0) {
                        if (null == dto.getPurchaser() || null == dto.getChannelType()) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、必填字段数据缺失").append(dto.getVin()).append(" 导入失败");
                            return failureMsg.toString();
                        }
                        TspVehicle tspVehicle = tspVehicleRepository.getByVin(dto.getVin());
                        if (Objects.isNull(tspVehicle)) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、车辆").append(dto.getVin()).append(" 不存在");
                            continue;
                        }
                        if (tspVehicle.getProgress().intValue() == 1) {
                            tspVehicle.setUpdateBy(SecurityUtils.getUsername());
                            tspVehicle.setState(TspVehicleStateEnum.SOLD.getValue());
                            tspVehicle.setPurpose(dto.getPurpose());
                            tspVehicle.setProgress(Integer.valueOf(2));
                            tspVehicle.setUpdateTime(DateUtils.getCurrentTime());
                            tspVehicleRepository.updateById(tspVehicle);
                            TspVehicleMarket tspVehicleMarket = new TspVehicleMarket();
                            TspVehicleOther tspVehicleOther = new TspVehicleOther();
                            BeanUtils.copyProperties(dto, tspVehicleMarket);
                            BeanUtils.copyProperties(dto, tspVehicleOther);
                            if (dto.getPurchaserState().equals("单位用车"))
                                tspVehicleMarket.setPurchaserState(Integer.valueOf(2));
                            if (dto.getPurchaserState().equals("私人用车"))
                                tspVehicleMarket.setPurchaserState(Integer.valueOf(1));
                            tspVehicleMarket.setTspVehicleId(tspVehicle.getId());
                            tspVehicleMarket.setCreateBy(SecurityUtils.getUsername());
                            tspVehicleMarket.setUpdateBy(SecurityUtils.getUsername());
                            tspVehicleMarket.setCreateTime(DateUtils.getCurrentTime());
                            tspVehicleMarket.setUpdateTime(DateUtils.getCurrentTime());
                            tspVehicleMarket.setInvoicingDate(LocalDate.parse(dto.getInvoicingDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            TspVehicleMarket oneMarket = (TspVehicleMarket) ((LambdaQueryChainWrapper) tspVehicleMarketRepository.lambdaQuery().eq(TspVehicleMarket::getTspVehicleId, tspVehicle.getId())).one();
                            if (Objects.nonNull(oneMarket))
                                tspVehicleMarket.setId(oneMarket.getId());
                            tspVehicleMarketRepository.saveOrUpdate(tspVehicleMarket);
                            tspVehicleOther.setTspVehicleId(tspVehicle.getId());
                            tspVehicleOther.setCreateBy(SecurityUtils.getUsername());
                            tspVehicleOther.setUpdateBy(SecurityUtils.getUsername());
                            tspVehicleOther.setCreateTime(DateUtils.getCurrentTime());
                            tspVehicleOther.setUpdateTime(DateUtils.getCurrentTime());
                            TspVehicleOther otherOne = (TspVehicleOther) ((LambdaQueryChainWrapper) tspVehicleOtherRepository.lambdaQuery().eq(TspVehicleOther::getTspVehicleId, tspVehicle.getId())).one();
                            if (Objects.nonNull(otherOne))
                                tspVehicleOther.setId(otherOne.getId());
                            tspVehicleOtherRepository.saveOrUpdate(tspVehicleOther);
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、销售信息").append(dto.getVin()).append(" 新增成功");
                            continue;
                        }
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、销售信息").append(dto.getVin()).append(" 已存在");
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、销售信息" + dto.getVin() + " 导入失败";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共" + failureNum + " 条数据格式不正确，错误如下：");
                return failureMsg.toString();
            }
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共" + successNum + " 条，数据如下：");
            return successMsg.toString();
        } catch (Throwable $ex) {
            try {
                throw $ex;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Map<String, Object> toCheckSales(TspVehicleSaleTemplateDTO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        if (dto.getPurchaserState() != null && !"".equals(dto.getPurchaserState()) && !dto.getPurchaserState().equals("私人用车") && !dto.getPurchaserState().equals("单位用车")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、购买领域").append(dto.getPurchaserState()).append(" 值异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getPurpose() || "".equals(dto.getPurpose()) || (!dto.getPurpose().equals("私人乘用车")
                && !dto.getPurpose().equals("公务乘用车") && !dto.getPurpose().equals("私人运营车")
                && !dto.getPurpose().equals("公务运营车"))) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆用途").append(dto.getPurpose()).append(" 值异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String versionRegexp = "^([a-zA-Z0-9]){17}$";
        if (null == dto.getVin() || !dto.getVin().matches(versionRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、出厂信息VIN").append(dto.getVin()).append(" 格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getEmployeeName() || dto.getEmployeeName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、办理员工名称").append(dto.getEmployeeName()).append(" 不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getPurchaser() || dto.getPurchaser().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、购买方").append(dto.getPurchaser()).append(" 不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String idRegexp = "^(\\d{17})(\\d|X|x)$";
        if (null == dto.getVehicleIdCard() || !dto.getVehicleIdCard().matches(idRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、身份证号").append(dto.getVehicleIdCard()).append(" 格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String invoiceRegexp = "^\\d{8}$";
        if (null == dto.getInvoiceNo() || !dto.getInvoiceNo().matches(invoiceRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、发票号码").append(dto.getInvoiceNo()).append(" 格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String dateRegexp = "^([0-9]{4})(-([0-1][0-9]))(-[0-3][0-9])$";
        if (null == dto.getInvoicingDate() || !dto.getInvoicingDate().matches(dateRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、开票日期").append(dto.getInvoicingDate()).append(" 格式异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getSalesUnitName() || dto.getSalesUnitName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、销货单位名称").append(dto.getSalesUnitName()).append(" 不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getSalesUnitAddress() || dto.getSalesUnitAddress().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、销货单位地址").append(dto.getSalesUnitAddress()).append(" 不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getSalesChannel() || dto.getSalesChannel().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、售货渠道名称").append(dto.getSalesChannel()).append(" 不能为空");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getVehicleStatus() || dto.getVehicleStatus().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车辆状态").append(dto.getVehicleStatus()).append(" 值异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getChannelType() || dto.getChannelType().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、销售渠道类型").append(dto.getChannelType()).append(" 值异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (null == dto.getNewVehicleFlag() || dto.getNewVehicleFlag().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、是否是新车").append(dto.getNewVehicleFlag()).append(" 值异常");
            checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        checkMap.put("failureNum", Integer.valueOf(failureNum));
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

    public TspVehicleAuditInfoDTO getAuditInfo(Long tspVehicleId) {
        log.info("根据车辆id获取认证信息--------------------tspVehicleId={}", tspVehicleId);
        TspVehicleAudit audit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
        if (Objects.isNull(audit))
            ErrorEnum.TSP_VEHICLE_NULL_AUDIT.throwErr();
        TspVehicle tspVehicle = tspVehicleRepository.getById(tspVehicleId);
        TspUser tspUser = tspUserRepository.getById(tspVehicle.getTspUserId());
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicleId);
        TspVehicleAuditInfoDTO dto = new TspVehicleAuditInfoDTO();
        BeanUtils.copyProperties(audit, dto);
        if (Objects.nonNull(license))
            dto.setPlateCode(license.getPlateCode());
        dto.setTspUser(tspUser);
        dto.setTspVehicle(tspVehicle);
        return dto;
    }

    public List<TspVehicleRelationMobileOptionDTO> relationMobileOption() {
        return tspVehicleRepository.relationMobileOption();
    }

    public List<TspVehicleExFactoryTemplateDTO> exportExFactory(TspVehiclePageListVO vo) {
        List<TspVehicleExFactoryTemplateDTO> list = null;
        try {
            vo.setPageNum(Integer.valueOf(1));
            vo.setPageSize(Integer.valueOf(2147483647));
            QueryWrapper<TspVehicle> ew = tspVehicleRepository.getPageListEw(vo);
            list = tspVehicleMapper.getExFactoryList(Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
            for (TspVehicleExFactoryTemplateDTO dto : list) {
                String label = dto.getLabel();
                List<String> labelList = new ArrayList<>();
                if (null != label && !"".equals(label) && !"[]".equals(label)) {
                    List<String> strings = Arrays.asList(label.split(","));
                    for (String string : strings) {
                        if (string.contains("["))
                            string = string.replace("[", "");
                        if (string.contains("]"))
                            string = string.replace("]", "");
                        if (string.contains(" "))
                            string = string.replace(" ", "");
                        TspTag tag = tspTagRepository.getById(Long.valueOf(string));
                        labelList.add(tag.getTagName());
                    }
                }
                if (labelList.size() > 0)
                    dto.setLabel(String.valueOf(labelList));
                if ("[]".equals(dto.getLabel()))
                    dto.setLabel("");
            }
        } catch (Exception e) {
            String msg = "导出车辆出厂信息失败";
            log.error(msg, e);
        }
        return list;
    }

    public List<TspVehicleSaleTemplateDTO> exportSales(TspVehiclePageListVO vo) {
        log.info("导出车辆销售信息---------------TspVehiclePageListVO={}", vo);
        List<TspVehicleSaleTemplateDTO> list = null;
        try {
            vo.setPageNum(Integer.valueOf(1));
            vo.setPageSize(Integer.valueOf(2147483647));
            QueryWrapper<TspVehicle> ew = tspVehicleRepository.getPageListEw(vo);
            list = tspVehicleMapper.getSalesList(Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
            for (TspVehicleSaleTemplateDTO dto : list) {
                if ("1".equals(dto.getPurchaserState())) {
                    dto.setPurchaserState("私人用车");
                    continue;
                }
                if ("2".equals(dto.getPurchaserState())) {
                    dto.setPurchaserState("单位用车");
                    continue;
                }
                if ("0".equals(dto.getPurchaserState()))
                    dto.setPurchaserState("");
            }
        } catch (Exception e) {
            String msg = "导出车辆销售失败";
            log.error(msg, e);
        }
        return list;
    }

    public List<Map<String, Object>> getBind(Long tspVehicleId) {
        return tspVehicleMapper.getBind(tspVehicleId);
    }

    public List<Map<String, String>> saleNameList() {
        return tspDealerMapper.saleNameList();
    }

    public List<TspDealer> saleNameListByLikeAddress(String address) {
        return tspDealerMapper.saleNameListByLikeAddress(address);
    }

    public Map<String, String> saleNameGetAddress(String dealerName) {
        return tspDealerMapper.saleNameGetAddress(dealerName);
    }

    public PageInfo<TspVehiclePageListDTO> listVehicle(TspVehiclePageListVO vo) {
        log.info("获取车辆列表-----------------TspVehiclePageListVO={}", vo);
        QueryWrapper<TspVehicle> ew = tspVehicleRepository.getPageListEw(vo);
        Integer count = tspVehicleMapper.getCount(ew);
        if (count.intValue() <= 10)
            vo.setPageNum(Integer.valueOf(1));
        IPage<TspVehiclePageListDTO> page = tspVehicleMapper.getPageList(Page.of(vo.getPageNum().intValue(), vo.getPageSize().intValue()), ew);
        for (TspVehiclePageListDTO record : page.getRecords()) {
            RedisConfig redisConfig = new RedisConfig();
            RedisTemplate<Object, Object> redisTemplate = redisConfig.redisTemplate(redisConnectionFactory);
            HashOperations<Object, Object, Object> hashOps = redisTemplate.opsForHash();
            List<Object> values = hashOps.values("VehicleRealtimeData:" + record.getVin());
            if (CollectionUtil.isNotEmpty(values) && values.size() != 0) {
                LocalDateTime collectTime = null;
                log.info("获取整车数据中---------------page={}", page);
                Map<String, Object> integrateJson = (Map<String, Object>) readListFromCache("VehicleRealtimeData:" + record.getVin(), "VehicleIntegrate");
                if (CollectionUtil.isNotEmpty(integrateJson)) {
                    collectTime = convertToLocalDateTime((JSONArray) integrateJson.get("collectTime"));
                    integrateJson.remove("collectTime");
                    List<VehicleIntegrate> vehicleIntegrates = JSONArray.parseArray("[" + integrateJson + "]", VehicleIntegrate.class);
                    if (vehicleIntegrates != null && vehicleIntegrates.size() != 0) {
                        record.setIsOnline(calcVehicleStateExt(vehicleIntegrates.get(0), collectTime, LocalDateTime.now()));
                        continue;
                    }
                    record.setIsOnline(Boolean.valueOf(false));
                }
                continue;
            }
            record.setIsOnline(Boolean.valueOf(false));
        }
        return PageInfo.of(page, page.getTotal());
    }

    public PageInfo<TspEquipmentPageListDTO> equipmentHistory(Long tspVehicleId) {
        log.info("根据当前车辆ID查询历史记录----------------tspVehicleId={}", tspVehicleId);
        QueryWrapper<TspVehicleEquipment> listEw = tspVehicleEquipmentRepository.getPageListEw(tspVehicleId);
        IPage<TspEquipmentPageListDTO> pageList = tspVehicleEquipmentMapper.getPageList(Page.of(1L, 10L), listEw);
        return PageInfo.of(pageList,pageList.getTotal());
    }

    public PageInfo<TspEquipmentPageListDTO> equipmentNow(Long tspEquipmentId) {
        log.info("根据设备ID查询设备绑定记录--------------tspEquipmentId={}", tspEquipmentId);
        QueryWrapper<TspEquipment> listEw = tspEquipmentRepository.getNowEquipment(tspEquipmentId);
        IPage<TspEquipmentPageListDTO> pageList = tspEquipmentMapper.getNowPageList(Page.of(1L, 10L), listEw);
        return PageInfo.of(pageList,pageList.getTotal());
    }

    private <T> Object readListFromCache(String key, String hashKey) {
        try {
            Object o = redisConfig.redisTemplate(redisConnectionFactory).opsForHash().get(key, hashKey);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static LocalDateTime convertToLocalDateTime(JSONArray collectTimeJSONArray) {
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
        log.info("车辆状态,当前时间------------{}---------------{}", new Object[]{dto, collectTime, now});
        if (Objects.nonNull(dto) && Objects.nonNull(collectTime)) {
            Duration duration = Duration.between(collectTime, now);
            if (duration.toMillis() > 60000L)
                return Boolean.valueOf(false);
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

}
