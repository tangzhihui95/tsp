package com.modern.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.model.dto.TspEquipmentExcelDTO;
import com.modern.model.dto.TspEquipmentLikeSelectDTO;
import com.modern.model.dto.TspEquipmentPageListDTO;
import com.modern.model.vo.TspEquipmentAddVO;
import com.modern.model.vo.TspEquipmentPageListVO;
import com.modern.model.vo.TspEquipmentScrapVO;
import com.modern.service.TspEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Tsp-设备信息
 *
 * @Author：tzh
 * @Package：com.modern.controller
 * @Project：tsp
 * @name：TspEquipmentController
 * @Date：2024/9/12 14:13
 * @Filename：TspEquipmentController
 */
@RestController
@Api(tags = {"Tsp-设备信息"})
@RequestMapping({"/tsp/equipment"})
public class TspEquipmentController {

    @Autowired
    private TspEquipmentService tspEquipmentService;

    @ApiOperation("设备信息-列表查询")
    @GetMapping({"/list"})
    @PreAuthorize("@ss.hasPermi('tsp:equipment:list')")
    public Result<PageInfo<TspEquipmentPageListDTO>> list(TspEquipmentPageListVO vo) {
        return Result.ok(tspEquipmentService.getPageList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipment:add')")
    @ApiOperation("添加")
    @Log(title = "设备信息-添加", businessType = BusinessType.INSERT)
    @PostMapping({"/add"})
    public JsonResult add(@RequestBody @Valid TspEquipmentAddVO vo) {
        return JsonResult.getResult(tspEquipmentService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipment:edit')")
    @ApiOperation("编辑")
    @Log(title = "设备信息-编辑", businessType = BusinessType.UPDATE)
    @PutMapping({"/edit"})
    public JsonResult edit(@RequestBody @Valid TspEquipmentAddVO vo) {
        return JsonResult.getResult(tspEquipmentService.edit(vo));
    }


    @ApiOperation("下拉列表")
    @GetMapping({"/likeSelect/{tspEquipmentId}"})
    public Result<List<TspEquipmentLikeSelectDTO>> likeSelect(@PathVariable Long tspEquipmentId, @RequestParam(required = false) String name) {
        return Result.ok(tspEquipmentService.likeSelect(tspEquipmentId, name));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipment:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "设备信息-批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/deletes/{tspEquipmentIds}"})
    public JsonResult deletes(@PathVariable Long[] tspEquipmentIds) {
        return JsonResult.getResult(tspEquipmentService.deletes(tspEquipmentIds));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipment:export')")
    @ApiOperation("导出")
    @Log(title = "设备信息- 导出", businessType = BusinessType.EXPORT)
    @GetMapping({"/export"})
    public AjaxResult export(TspEquipmentPageListVO vo) {
        List<TspEquipmentExcelDTO> list = tspEquipmentService.exportList(vo);
        ExcelUtil<TspEquipmentExcelDTO> util = new ExcelUtil(TspEquipmentExcelDTO.class);
        return util.exportExcel(list, "设备信息");
    }

    @ApiOperation("导入")
    @PreAuthorize("@ss.hasPermi('tsp:equipment:importEquipment')")
    @Log(title = "设备信息- 导入", businessType = BusinessType.IMPORT)
    @PostMapping({"/importEquipment"})
    public Result importEquipment(MultipartFile file, Boolean isUpdateSupport) throws IOException {
        return Result.ok(this.tspEquipmentService.importEquipment(file, isUpdateSupport));
    }

    @ApiOperation("下载模版")
    @GetMapping({"/importTemplate"})
    public AjaxResult importTemplate() {
        ExcelUtil<TspEquipmentExcelDTO> util = new ExcelUtil(TspEquipmentExcelDTO.class);
        return util.importTemplateExcel("设备信息数据");
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipment:scrap')")
    @ApiOperation("报废")
    @Log(title = "设备信息-报废", businessType = BusinessType.UPDATE)
    @PutMapping({"/scrap"})
    public JsonResult scrap(@RequestBody @Valid TspEquipmentScrapVO vo) {
        return JsonResult.getResult(tspEquipmentService.scrap(vo));
    }

}
