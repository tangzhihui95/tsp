package com.modern.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.modern.common.annotation.DataScope;
import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.page.PageInfo;
import com.modern.common.exception.ServiceException;
import com.modern.common.utils.DateUtils;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.SecurityUtils;
import com.modern.common.utils.StringUtils;
import com.modern.common.utils.bean.BeanUtils;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.domain.TspTag;
import com.modern.domain.TspUser;
import com.modern.mapper.TspUserMapper;
import com.modern.model.dto.TspUserDTO;
import com.modern.model.dto.TspUserPageListDTO;
import com.modern.model.vo.TspUserAddVO;
import com.modern.model.vo.TspUserExcelVO;
import com.modern.model.vo.TspUserPageListVO;
import com.modern.repository.TspTagRepository;
import com.modern.repository.TspUserRepository;
import com.modern.repository.TspVehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        tspUser.setCreateBy(SecurityUtils.getUsername());
        tspUser.setIsDelete(Integer.valueOf(0));
        tspUser.setCreateTime(DateUtils.getCurrentTime());
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
        if (Objects.nonNull(vo.getLabel()) && newLabelList.size() != 0)
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
        tspUser.setUpdateTime(DateUtils.getCurrentTime());
        tspUser.setUpdateBy(SecurityUtils.getUsername());
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

    public String importUser(MultipartFile file, Boolean isUpdateSupport) throws IOException {
        try {
            ExcelUtil<TspUserExcelVO> util = new ExcelUtil(TspUserExcelVO.class);
            List<TspUserExcelVO> dtos = util.importExcel(file.getInputStream());
            if (StringUtils.isNull(dtos) || dtos.size() == 0)
                throw new ServiceException("导入数据不能为空");
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            for (TspUserExcelVO dto : dtos) {
                try {
                    Map<String, Object> checkMap = toCheckUser(dto, failureMsg, failureNum);
                    failureNum = ((Integer) checkMap.get("failureNum")).intValue();
                    failureMsg = (StringBuilder) checkMap.get("failureMsg");
                    if (failureNum == 0) {
                        TspUser tspUser = tspUserRepository.getByMobile(dto.getMobile());
                        Integer addressId = tspUserMapper.getAddress("中国,"+ dto.getProvince() + ", " + dto.getCity() + ", " + dto.getArea());
                        if (addressId == null) {
                            failureNum++;
                            failureMsg.append("<br/>").append(failureNum).append("、地址").append(dto.getProvince())
                                    .append(dto.getCity()).append(dto.getArea()).append("、填写不正确");
                            continue;
                        }
                        if (tspUser == null) {
                            tspUser = new TspUser();
                            BeanUtils.copyProperties(dto, tspUser);
                            if (dto.getLabel() != null && !"".equals(dto.getLabel())) {
                                TspTag tag = tspTagRepository.getByDealerName(dto.getLabel());
                                List<Long> label = new ArrayList<>();
                                if (tag != null) {
                                    tag.setTagCount(Integer.valueOf(tag.getTagCount().intValue() + 1));
                                    tspTagRepository.updateById(tag);
                                    label.add(tag.getId());
                                    tspUser.setLabel(label.toString());
                                } else {
                                    tspUser.setLabel(null);
                                }
                            }
                            tspUser.setCreateBy(SecurityUtils.getUsername());
                            tspUser.setUpdateBy(SecurityUtils.getUsername());
                            if (dto.getBirthday() != null && !"".equals(dto.getBirthday()))
                                tspUser.setBirthday(LocalDate.parse(dto.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            tspUserRepository.save(tspUser);
                            successNum++;
                            successMsg.append("<br/>").append(successNum).append("、用户").append(dto.getRealName()).append(" 导入成功 ");
                            continue;
                        }
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum).append("、用户").append(dto.getRealName()).append("的电话号码").append(dto.getMobile()).append("已被使用");
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、用户" + dto.getRealName() + " 导入失败 ";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共" + failureNum + " 条数据格式不正确，错误如下：");
                throw new ServiceException(failureMsg.toString());
            }
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共" + successNum + " 条，数据如下：");
            return successMsg.toString();
        } catch (Throwable $ex) {
            throw $ex;
        }
    }

    public List<TspUserPageListDTO> exportList(TspUserPageListVO vo) {
        vo.setPageNum(Integer.valueOf(1));
        vo.setPageSize(Integer.valueOf(2147483647));
        //return getPageList(vo).getList();
        ArrayList<TspUserPageListDTO> dtos = new ArrayList<>();
        List<TspUserPageListDTO> list = getPageList(vo).getList();
        for(TspUserPageListDTO tspUserPageListDTO :list){
            Integer count = tspVehicleRepository.countByTspUserId(tspUserPageListDTO.getId());
            TspUserPageListDTO dto = new TspUserPageListDTO();
            BeanUtils.copyProperties(tspUserPageListDTO, dto);
            dto.setVehicleCount(Integer.valueOf(count));
            dtos.add(dto);
        }
        return dtos;
    }

    private Map<String, Object> toCheckUser(TspUserExcelVO dto, StringBuilder failureMsg, int failureNum) {
        Map<String, Object> checkMap = new HashMap<>();
        if (dto.getRealName() == null || dto.getRealName().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、车主姓名").append(dto.getRealName()).append(" 不能为空 ");
                    checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String phoneRegexp = "^\\d{11}$";
        if (dto.getMobile() == null || !dto.getMobile().matches(phoneRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、手机号码").append(dto.getMobile()).append(" 格式异常 ");
                    checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String idRegexp = "^(\\d{17})(\\d|X|x)$";
        if (dto.getIdCard() == null || !dto.getIdCard().matches(idRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、身份证号").append(dto.getIdCard()).append(" 格式异常 ");
                    checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        String dateRegexp = "^([0-9]{4})(-([0-1][0-9]))(-[0-3][0-9])$";
        if (dto.getBirthday() != null && !"".equals(dto.getBirthday()) && !dto.getBirthday().matches(dateRegexp)) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、生日").append(dto.getBirthday()).append(" 格式异常 ");
                    checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getProvince() == null || dto.getProvince().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、注册地址(省)").append(dto.getProvince()).append(" 不能为空 ");
                    checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getCity() == null || dto.getCity().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、注册地址(市)").append(dto.getCity()).append(" 不能为空 ");
                    checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getArea() == null || dto.getArea().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、注册地址(区/县)").append(dto.getArea()).append(" 不能为空 ");
                    checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getLabel() == null || dto.getLabel().equals("")) {
            failureNum++;
            failureMsg.append("<br/>").append(failureNum).append("、用户标签").append(dto.getLabel()).append(" 不能为空 ");
                    checkMap.put("failureNum", Integer.valueOf(failureNum));
            checkMap.put("failureMsg", failureMsg);
            return checkMap;
        }
        if (dto.getLabel() != null && !dto.getLabel().equals("")) {
            TspTag tag = tspTagRepository.getByDealerName(dto.getLabel());
            if (tag == null) {
                failureNum++;
                failureMsg.append("<br/>").append(failureNum).append("、所填写的用户标签无法在标签管理中找到对应标签 ");
                checkMap.put("failureNum", Integer.valueOf(failureNum));
                checkMap.put("failureMsg", failureMsg);
                return checkMap;
            }
        }
        checkMap.put("failureNum", Integer.valueOf(failureNum));
        checkMap.put("failureMsg", failureMsg);
        return checkMap;
    }

}
