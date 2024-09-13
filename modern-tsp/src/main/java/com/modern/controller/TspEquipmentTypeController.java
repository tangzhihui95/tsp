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
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.model.dto.TspEquipmentTypeExcelDTO;
import com.modern.model.dto.TspEquipmentTypeImportDTO;
import com.modern.model.dto.TspEquipmentTypePageListDTO;
import com.modern.model.dto.TspEquipmentTypeSelectDTO;
import com.modern.model.vo.FrontQuery;
import com.modern.model.vo.TspEquipmentTypeAddVO;
import com.modern.service.TspEquipmentTypeService;
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
 * tsp-设备类型
 *
 * @Author：tzh
 * @Package：com.modern.controller
 * @Project：tsp
 * @name：TspEquipmentTypeController
 * @Date：2024/8/29 10:45
 * @Filename：TspEquipmentTypeController
 */
@RestController
@Api(tags = {"tsp-设备类型"})
@RequestMapping({"/tsp/equipmentType"})
public class TspEquipmentTypeController {

    @Autowired
    private TspEquipmentTypeService equipmentTypeService;

    /**
     * 设备分类-列表查询
     *
     * @param vo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:list')")
    @ApiOperation("分页列表")
    @GetMapping({"/list"})
    public Result<PageInfo<TspEquipmentTypePageListDTO>> list(FrontQuery vo) {
        return Result.ok(equipmentTypeService.getPageList(vo));
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
        return JsonResult.getResult(equipmentTypeService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:edit')")
    @ApiOperation("")
    @Log(title = "- ", businessType = BusinessType.UPDATE)
    @PutMapping({"/edit"})
    public JsonResult edit(@RequestBody @Valid TspEquipmentTypeAddVO vo) {
        return JsonResult.getResult(equipmentTypeService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:delete')")
    @ApiOperation("删除")
    @Log(title = "设备分类-删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/delete/{equipmentTypeId}"})
    public JsonResult delete(@PathVariable("equipmentTypeId") Long equipmentTypeId) {
        return JsonResult.getResult(equipmentTypeService.delete(equipmentTypeId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "设备分类-批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/deletes/{equipmentTypeIds}"})
    public JsonResult deletes(@PathVariable Long[] equipmentTypeIds) {
        return JsonResult.getResult(equipmentTypeService.deletes(equipmentTypeIds));
    }

    @ApiOperation("下拉列表")
    @Log(title = "设备分类-下拉列表", businessType = BusinessType.DELETE)
    @PostMapping({"/selectList"})
    public Result<List<TspEquipmentTypeSelectDTO>> selectList(@RequestBody FrontQuery vo) {
        return Result.ok(this.equipmentTypeService.selectList(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:export')")
    @ApiOperation("导出")
    @Log(title = "设备信息-导出", businessType = BusinessType.EXPORT)
    @GetMapping({"/export"})
    public AjaxResult export(FrontQuery vo) {
        List<TspEquipmentTypeExcelDTO> list = this.equipmentTypeService.exportList(vo);
        ExcelUtil<TspEquipmentTypeExcelDTO> util = new ExcelUtil(TspEquipmentTypeExcelDTO.class);
        return util.exportExcel(list, "");
    }

    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:importEquipmentModel')")
    @ApiOperation("设备型号导入")
    @Log(title = "设备信息-设备型号导入", businessType = BusinessType.IMPORT)
    @PostMapping({"/importEquipmentModel"})
    public Result importEquipmentModel(MultipartFile file, Boolean isUpdateSupport) {
        try {
            return Result.ok(this.equipmentTypeService.importEquipmentModel(file, isUpdateSupport));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @ApiOperation("设备分类导入")
    @PreAuthorize("@ss.hasPermi('tsp:equipmentType:importEquipmentType')")
    @Log(title = "设备分类信息-导入", businessType = BusinessType.IMPORT)
    @PostMapping({"/importEquipmentType"})
    public Result importEquipmentType(MultipartFile file, Boolean isUpdateSupport) {
        return Result.ok(this.equipmentTypeService.importEquipmentType(file, isUpdateSupport));
    }

    @ApiOperation("设备分类下载模版")
    @GetMapping({"/importTypeTemplate"})
    public AjaxResult importTypeTemplate() {
        ExcelUtil<TspEquipmentTypeImportDTO> util = new ExcelUtil(TspEquipmentTypeImportDTO.class);
        return util.importTemplateExcel("设备类型数据");
    }

    @ApiOperation("设备型号下载模版")
    @GetMapping({"/importModelTemplate"})
    public AjaxResult importModelTemplate() {
        ExcelUtil<TspEquipmentTypeExcelDTO> util = new ExcelUtil(TspEquipmentTypeExcelDTO.class);
        return util.importTemplateExcel("设备型号数据");
    }
}
