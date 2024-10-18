package com.modern.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.entity.SysRole;
import com.modern.common.core.domain.model.LoginUser;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.DateUtils;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.domain.*;
import com.modern.enums.TspVehicleStateEnum;
import com.modern.mapper.TspEquipmentMapper;
import com.modern.mapper.TspVehicleMapper;
import com.modern.model.dto.TspVehicleInfoDTO;
import com.modern.model.dto.TspVehiclePageListDTO;
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

}
