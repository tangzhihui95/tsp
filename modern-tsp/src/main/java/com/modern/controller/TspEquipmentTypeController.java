package com.modern.controller;

/**
 * @Author：tzh
 * @Package：com.modern.controller
 * @Project：tsp
 * @name：TspEquipmentTypeController
 * @Date：2024/8/28 14:45
 * @Filename：TspEquipmentTypeController
 */

import com.modern.common.annotation.Log;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.JsonResult;
import com.modern.domain.FrontQuery;
import com.modern.model.dto.TspEquipmentTypePageListDTO;
import com.modern.model.vo.TspEquipmentTypeAddVO;
import com.modern.service.EquipmentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping({"/tsp/equipmentType"})
@Api(tags = {"tsp-设备类型"})
public class TspEquipmentTypeController {

    @Autowired
    public EquipmentTypeService equipmentTypeService;

    /**
     * 设备分类-列表查询
     *
     * @param vo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:list')")
    @ApiOperation("分页列表")
    @PostMapping({"/list"})
    public Result<PageInfo<TspEquipmentTypePageListDTO>> list(@RequestBody FrontQuery vo) {
        return Result.ok(equipmentTypeService.getPageList(vo));
        //return Result.ok(equipmentTypeService.getList(vo));
    }

    /**
     * 设备分类-设备分类添加
     *
     * @param vo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:add')")
    @ApiOperation("设备分类-添加")
    @Log(title = "设备分类-添加", businessType = BusinessType.INSERT)
    @PostMapping({"/add"})
    public JsonResult add(@RequestBody @Valid TspEquipmentTypeAddVO vo) {
        return JsonResult.getResult1(equipmentTypeService.add(vo));
    }
}
