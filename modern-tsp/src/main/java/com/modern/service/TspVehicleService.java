package com.modern.service;

import cn.hutool.core.collection.CollectionUtil;
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
import com.modern.domain.*;
import com.modern.enums.TspVehicleStateEnum;
import com.modern.mapper.TspEquipmentMapper;
import com.modern.mapper.TspVehicleMapper;
import com.modern.model.dto.TspVehicleInfoDTO;
import com.modern.model.dto.TspVehiclePageListDTO;
import com.modern.model.vo.TspUseVehicleRecordAddVO;
import com.modern.model.vo.TspVehicleAddVO;
import com.modern.model.vo.TspVehicleLicenseRecordAddVO;
import com.modern.model.vo.TspVehiclePageListVO;
import com.modern.repository.*;
import com.modern.system.service.ISysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
        return PageInfo.of(page, page.getTotal());
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
        tspVehicle.setState(TspVehicleStateEnum.CREATED);
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
        Long tspVehicleId = this.tspVehicleMapper.getByEquipmentId(vo.getTspEquipmentId());
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
                    if (vehicle.getState().getValue().intValue() < TspVehicleStateEnum.SOLD.getValue().intValue())
                        vehicle.setState(TspVehicleStateEnum.SOLD);
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
                    this.tspVehicleMarketRepository.save(market);
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
                if (vehicle.getState().getValue().intValue() < TspVehicleStateEnum.BOUND.getValue().intValue())
                    vehicle.setState(TspVehicleStateEnum.BOUND);
                vehicle.setTspUserId(byMobile.getId());
                vehicle.setIsComplete(Boolean.valueOf(true));
                vehicle.setUpdateBy(SecurityUtils.getUsername());
                vehicle.setCurrentBindTime(DateUtils.getCurrentTime());
                vehicle.setUpdateTime(DateUtils.getCurrentTime());
                this.tspVehicleRepository.updateById(vehicle);
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
        if (!Objects.equals(TspVehicleStateEnum.ALL.getValue(), tspVehicle.getState().getValue()) && (
                Objects.equals(TspVehicleStateEnum.SOLD.getValue(), tspVehicle.getState().getValue()) ||
                        Objects.equals(TspVehicleStateEnum.BOUND.getValue(), tspVehicle.getState().getValue()) ||
                        Objects.equals(TspVehicleStateEnum.SCRAPPED.getValue(), tspVehicle.getState().getValue()) ||
                        Objects.equals(TspVehicleStateEnum.ALREADY.getValue(), tspVehicle.getState().getValue())))
            ErrorEnum.TSP_VEHICLE_DELETE_STATE_ERR.throwErr();
        TspVehicleAudit vehicleAudit = tspVehicleAuditService.getByTspVehicleId(tspVehicleId);
        if (Objects.isNull(vehicleAudit))
            tspVehicleAuditRepository.removeById((Serializable) vehicleAudit);
        tspVehicleRepository.removeById(tspVehicleId);
        TspVehicleLicense license = tspVehicleLicenseRepository.getByTspVehicleId(tspVehicle.getId());
        if (Objects.isNull(license))
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
            if (!Objects.equals(TspVehicleStateEnum.ALL.getValue(), tspVehicle.getState().getValue()) && (
                    Objects.equals(TspVehicleStateEnum.CREATED.getValue(), tspVehicle.getState().getValue()) ||
                            Objects.equals(TspVehicleStateEnum.SOLD.getValue(), tspVehicle.getState().getValue()) ||
                            Objects.equals(TspVehicleStateEnum.BOUND.getValue(), tspVehicle.getState().getValue()) ||
                            Objects.equals(TspVehicleStateEnum.SCRAPPED.getValue(), tspVehicle.getState().getValue()) ||
                            Objects.equals(TspVehicleStateEnum.ALREADY.getValue(), tspVehicle.getState().getValue())))
                ErrorEnum.TSP_VEHICLE_DELETES_STATE_ERR.throwErr();
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
        if (!tspVehicle.getState().equals(TspVehicleStateEnum.SOLD))
            ErrorEnum.TSP_VEHICLE_SOLD_NULL_ERR.throwErr();
        log.info("开始进行绑定中--------");
        tspVehicle.setState(TspVehicleStateEnum.BOUND);
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

}
