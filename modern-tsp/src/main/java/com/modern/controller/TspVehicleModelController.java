package com.modern.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.domain.TspVehicleModel;
import com.modern.model.dto.*;
import com.modern.model.vo.TspVehicleModelAddVO;
import com.modern.model.vo.TspVehicleModelPageListVO;
import com.modern.model.vo.TspVehiclePageListVO;
import com.modern.model.vo.TspVehicleStdModelAddVO;
import com.modern.service.TspVehicleModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.controller
 * @Project：tsp
 * @name：TspVehicleModelController
 * @Date：2024/10/14 15:00
 * @Filename：TspVehicleModelController
 */
@RestController
@Api(tags = {"TSP - 车辆车型"})
@RequestMapping({"/tsp/vehicle/model"})
public class TspVehicleModelController {

    @Autowired
    private TspVehicleModelService tspVehicleModelService;


    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:list')")
    @ApiOperation("车辆车型-列表")
    @GetMapping({"/list"})
    public Result<PageInfo<TspVehicleModelPageListDTO>> list(TspVehicleModelPageListVO vo) {
        return Result.ok(tspVehicleModelService.getPageList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:add')")
    @ApiOperation("车辆车型-添加")
    @Log(title = "车辆车型- 添加", businessType = BusinessType.INSERT)
    @PostMapping({"/add"})
    public JsonResult add(@RequestBody @Valid TspVehicleModelAddVO vo) {
        return JsonResult.getResult(tspVehicleModelService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:edit')")
    @ApiOperation("车辆车型-编辑")
    @Log(title = "车辆车型- 编辑", businessType = BusinessType.UPDATE)
    @PutMapping({"/edit"})
    public JsonResult edit(@RequestBody @Valid TspVehicleModelAddVO vo) {
        return JsonResult.getResult(tspVehicleModelService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:delete')")
    @ApiOperation("车辆车型-删除")
    @Log(title = "车辆车型-删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/delete/{tspVehicleModelId}"})
    public JsonResult delete(@PathVariable Long tspVehicleModelId) {
        return JsonResult.getResult(tspVehicleModelService.delete(tspVehicleModelId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:deletes')")
    @ApiOperation("车辆车型-批量删除")
    @Log(title = "车辆车型-删除-批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/deletes/{tspVehicleModelIds}"})
    public JsonResult deletes(@PathVariable("tspVehicleModelIds") Long[] tspVehicleModelIds) {
        return JsonResult.getResult(tspVehicleModelService.deletes(tspVehicleModelIds));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:export')")
    @Log(title = "车辆车型-导出", businessType = BusinessType.EXPORT)
    @PostMapping({"/export"})
    public AjaxResult export(TspVehicleModelPageListVO vo) {
        List<TspVehicleStdModelExListDTO> list = this.tspVehicleModelService.exportList(vo);
        ExcelUtil<TspVehicleStdModelExListDTO> util = new ExcelUtil(TspVehicleStdModelExListDTO.class);
        return util.exportExcel(list, "车辆车型信息");
    }

    @ApiOperation("车辆车型一级车型-下载模版")
    @PostMapping({"/importTemplateModel"})
    public void importTemplateModel(HttpServletResponse response) {
        ExcelUtil<TspVehicleModel> util = new ExcelUtil(TspVehicleModel.class);
        util.importTemplateExcel(response, "车辆车型一级车型信息数据");
    }

    @ApiOperation("车辆车型一级车型-导入")
    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:importVehicleModel')")
    @Log(title = "车辆车型一级车型-导入", businessType = BusinessType.IMPORT)
    @PostMapping({"/importVehicleModel"})
    public Result importVehicleModel(@RequestParam("file") MultipartFile multipartFile, Boolean isUpdateSupport) {
        try {
            return Result.ok(tspVehicleModelService.importVehicleModel(multipartFile, isUpdateSupport));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:addStdModel')")
    @ApiOperation("车辆车型二级车型-添加")
    @Log(title = "车辆车型二级车型- 添加", businessType = BusinessType.INSERT)
    @PostMapping({"/addStdModel"})
    public JsonResult addStdModel(@RequestBody @Valid TspVehicleStdModelAddVO vo) {
        return JsonResult.getResult(tspVehicleModelService.addStdModel(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:editStdModel')")
    @ApiOperation("车辆车型二级车型-编辑")
    @Log(title = "车辆车型二级车型-编辑", businessType = BusinessType.UPDATE)
    @PutMapping({"/editStdModel"})
    public JsonResult editStdModel(@RequestBody @Valid TspVehicleStdModelAddVO vo) {
        return JsonResult.getResult(tspVehicleModelService.editStdModel(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:deleteStdModel')")
    @ApiOperation("车辆车型二级车型-删除")
    @Log(title = "车辆车型二级车型-删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/deleteStdModel/{tspVehicleStdModelId}"})
    public JsonResult deleteStdModel(@PathVariable("tspVehicleStdModelId") Long tspVehicleStdModelId) {
        return JsonResult.getResult(tspVehicleModelService.deleteStdModel(tspVehicleStdModelId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:getByTspStdModelId')")
    @ApiOperation("车辆车型二级车型-详情")
    @GetMapping({"/getByTspStdModelId/{tspVehicleStdModelId}"})
    public JsonResult getByTspStdModelId(@PathVariable("tspVehicleStdModelId") Long tspVehicleStdModelId) {
        return JsonResult.getResult(tspVehicleModelService.getByTspStdModelId(tspVehicleStdModelId));
    }

    @ApiOperation("车辆车型二级车型-下拉列表")
    @GetMapping({"/select"})
    public Result<List<TspVehicleStdModelSelectListDTO>> select(@RequestParam(value = "tspVehicleStdModelId", required = false) Long tspVehicleStdModelId) {
        return Result.ok(tspVehicleModelService.select());
    }


    @ApiOperation("车辆车型二级车型-统计车型标签")
    @GetMapping({"/getLabelMap"})
    public Result<TspVehicleStdModelLabelMapDTO> getLabelMap() {
        return Result.ok(tspVehicleModelService.getLabelMap());
    }

    @ApiOperation("车辆车型二级车型-下载模版")
    @PostMapping({"/importTemplateStdModel"})
    public void importTemplateStdModel(HttpServletResponse response) {
        ExcelUtil<TspVehicleStdModelExportListDTO> util = new ExcelUtil(TspVehicleStdModelExportListDTO.class);
        util.importTemplateExcel(response, "车辆车型二级车型信息数据");
    }

    @ApiOperation("车辆车型二级车型导入")
    @PreAuthorize("@ss.hasPermi('tsp:vehicleModel:importVehicleStdModel')")
    @Log(title = "车辆车型二级车型-导入", businessType = BusinessType.IMPORT)
    @PostMapping({"/importVehicleStdModel"})
    public Result importVehicleStdModel(@RequestParam("file") MultipartFile multipartFile, Boolean isUpdateSupport) throws IOException {
        return Result.ok(tspVehicleModelService.importVehicleModelStd(multipartFile, isUpdateSupport));
    }

    @ApiOperation("二级车型列表")
    @Log(title = "二级车型-下拉列表")
    @PostMapping({"/selectChildrenList"})
    public Result<List<TspVehicleModelSelectDTO>> selectChildrenList(@RequestBody @Valid TspVehiclePageListVO vo) {
        return Result.ok(tspVehicleModelService.selectChildrenList(vo));
    }


}
