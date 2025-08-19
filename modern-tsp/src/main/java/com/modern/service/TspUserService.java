package com.modern.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.annotation.DataScope;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.domain.TspTag;
import com.modern.domain.TspUser;
import com.modern.mapper.TspUserMapper;
import com.modern.model.dto.TspUserDTO;
import com.modern.model.dto.TspUserPageListDTO;
import com.modern.model.vo.TspUserAddVO;
import com.modern.model.vo.TspUserPageListVO;
import com.modern.repository.TspTagRepository;
import com.modern.repository.TspUserRepository;
import com.modern.repository.TspVehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    private static final Logger log = LoggerFactory.getLogger(TspUserService.class);

    @Autowired
    private TspUserRepository tspUserRepository;

    @Autowired
    private TspVehicleRepository tspVehicleRepository;

    @Autowired
    private TspTagRepository tspTagRepository;

    @Resource
    private TspUserMapper tspUserMapper;

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

    public JsonResult add(TspUserAddVO vo) {
        TspUser tspUser = tspUserRepository.getByMobile(vo.getMobile());
        if (Objects.nonNull(tspUser))
            ErrorEnum.TSP_USER_USER_NOT_NULL_ERR.throwErr();
        tspUser = new TspUser();
        BeanUtils.copyProperties(vo, tspUser);
        if (Objects.nonNull(vo.getLabel()) && vo.getLabel().size() != 0) {
            for (Long tspTagId : vo.getLabel()) {
                TspTag tspTag = tspTagRepository.getById(tspTagId);
                tspTag.setTagCount(Integer.valueOf(tspTag.getTagCount().intValue() + 1));
                tspTagRepository.updateById(tspTag);
            }
            tspUser.setLabel(vo.getLabel().toString());
        }
        tspUser.setUpdateBy(SecurityUtils.getUsername());
        tspUser.setCreateBy(SecurityUtils.getUsername());
        tspUser.setIsDelete(Integer.valueOf(0));
        if (StringUtils.isNotEmpty(tspUser.getUserCardBackImg()) && StringUtils.isNotEmpty(tspUser.getUserCardFrontImg()))
            tspUser.setRealNameAudit(Integer.valueOf(1));
        return JsonResult.getResult(tspUserRepository.save(tspUser));
    }

    public JsonResult edit(TspUserAddVO vo) {
        if (Objects.isNull(vo.getTspUserId()))
            throw new ServiceException("用户ID不能为空");
        TspUser mobile = tspUserRepository.getByMobile(vo.getMobile());
        TspUser tspUser = tspUserRepository.getById(vo.getTspUserId());
        if (Objects.isNull(tspUser))
            ErrorEnum.TSP_USER_USER_NULL_ERR.throwErr();
        if (Objects.nonNull(mobile) && !mobile.getId().equals(tspUser.getId()))
            ErrorEnum.TSP_USER_USER_NOT_NULL_ERR.throwErr();
        String labelStr = tspUser.getLabel();
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
                tspTagRepository.updateById(oldTag);
            }
        }
        List<Long> newLabelList = vo.getLabel();
        if (Objects.nonNull(newLabelList) && newLabelList.size() != 0)
            for (Long newLabel : newLabelList) {
                TspTag newTag = tspTagRepository.getById(newLabel);
                newTag.setTagCount(Integer.valueOf(newTag.getTagCount().intValue() + 1));
                newTag.setUpdateBy(SecurityUtils.getUsername());
                tspTagRepository.updateById(newTag);
            }
        BeanUtils.copyProperties(vo, tspUser);
        tspUser.setLabel((Objects.isNull(vo.getLabel())) ? null : vo.getLabel().toString());
        if (StringUtils.isNotEmpty(tspUser.getUserCardBackImg()) && StringUtils.isNotEmpty(tspUser.getUserCardFrontImg()))
            tspUser.setRealNameAudit(Integer.valueOf(1));
        return JsonResult.getResult(tspUserRepository.updateById(tspUser));
    }

    public TspUserDTO get(Long tspUserId) {
        TspUser tspUser = tspUserRepository.getById(tspUserId);
        TspUserDTO dto = new TspUserDTO();
        BeanUtils.copyProperties(tspUser, dto);
        String label = tspUser.getLabel();
        if (StringUtils.isNotNull(label) && !"".equals(label) && !"[]".equals(label)) {
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
        if (Objects.isNull(tspUser))
            ErrorEnum.TSP_USER_USER_NULL_ERR.throwErr();
        return dto;
    }

    public List<Map<String, Object>> findCarInfo(Long tspUserId) {
        List<Map<String, Object>> mapList = tspUserMapper.findCarInfo(tspUserId);
        return getMaps(mapList);
    }

    private List<Map<String, Object>> getMaps(List<Map<String, Object>> mapList) {
        if (Objects.nonNull(mapList) && mapList.size() == 1) {
            if (StringUtils.isNull((mapList.get(0)).get("vin")))
                return null;
            for (Map<String, Object> map : mapList) {
                List<Map<String, Object>> cardInfoList = (List<Map<String, Object>>) JSONArray.parse((String) map.get("cardInfo"));
                if (Objects.nonNull(cardInfoList) && cardInfoList.size() != 0)
                    for (Map<String, Object> cardInfoMap : cardInfoList) {
                        if (StringUtils.isNotNull(map.get("ICCID")) && map.get("ICCID").equals(cardInfoMap.get("ICCID"))) {
                            if (cardInfoMap.get("IsAuth").equals("true")) ;
                            map.replace("status", "未实名认证");
                        }
                    }
            }
        }
        return mapList;
    }

    public List<Map<String, Object>> findHistory(Long tspUserId) {
        List<Map<String, Object>> mapList = tspUserMapper.findHistory(tspUserId);
        return getMaps(mapList);
    }

    @Transactional(rollbackFor = {Exception.class})
    public JsonResult deletes(Long[] tspUserIds) {
        log.info("根据{}批量删除", (Object[]) tspUserIds);
        if (tspUserIds.length > 0)
            for (Long tspUserId : tspUserIds) {
                TspUser tspUser = tspUserRepository.getById(tspUserId);
                if (Objects.isNull(tspUser))
                    ErrorEnum.TSP_USER_USERS_NULL_ERR.throwErr();
                tspUserRepository.removeById(tspUserId);
                String labelStr = tspUser.getLabel();
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
                        tspTagRepository.updateById(oldTag);
                    }
                }
            }
        return JsonResult.getResult(true);
    }


}
