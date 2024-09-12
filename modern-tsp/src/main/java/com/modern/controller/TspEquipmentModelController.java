package com.modern.controller;

import com.modern.common.annotation.Log;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.JsonResult;
import com.modern.model.vo.TspEquipmentModelAddVO;
import com.modern.service.TspEquipmentModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author：tzh
 * @Package：com.modern.controller
 * @Project：tsp
 * @name：TspEquipmentModelController
 * @Date：2024/9/12 10:45
 * @Filename：TspEquipmentModelController
 */
@RestController
@RequestMapping({"/tsp/equipmentModel"})
@Api(tags = {"TSP - 设备型号"})
public class TspEquipmentModelController {
    @Autowired
    private TspEquipmentModelService tspEquipmentModelService;


    @PreAuthorize("@ss.hasPermi('tsp:equipmentModel:add')")
    @ApiOperation("添加")
    @Log(title = "设备型号-添加", businessType = BusinessType.INSERT)
    @PostMapping({"/add"})
    public JsonResult add(@RequestBody @Valid TspEquipmentModelAddVO vo) {
        return JsonResult.getResult(tspEquipmentModelService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentModel:edit')")
    @ApiOperation("编辑")
    @Log(title = "设备型号-编辑", businessType = BusinessType.UPDATE)
    @PutMapping({"/edit"})
    public JsonResult edit(@RequestBody @Valid TspEquipmentModelAddVO vo) {
        return JsonResult.getResult1(tspEquipmentModelService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentModel:delete')")
    @ApiOperation("删除")
    @Log(title = "设备型号-删除", businessType = BusinessType.UPDATE)
    @DeleteMapping({"/delete/{tspEquipmentModelId}"})
    public JsonResult delete(@PathVariable Long tspEquipmentModelId) {
        return JsonResult.getResult1(tspEquipmentModelService.delete(tspEquipmentModelId));
    }
}
