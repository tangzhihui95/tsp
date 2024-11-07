package com.modern.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.domain.AjaxResult;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.model.dto.*;
import com.modern.model.vo.TspVehicleAddVO;
import com.modern.model.vo.TspVehiclePageListVO;
import com.modern.model.vo.TspVehicleScrapVO;
import com.modern.service.TspVehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.controller
 * @Project：tsp
 * @name：TspVehicleController
 * @Date：2024/10/16 14:45
 * @Filename：TspVehicleController
 */
@Api(tags = {"TSP-车辆信息"})
@RestController
@RequestMapping({"/tsp/vehicle"})
public class TspVehicleController {

    @Autowired
    private TspVehicleService tspVehicleService;


    @PreAuthorize("@ss.hasPermi('tsp:vehicle:list')")
    @ApiOperation("车辆信息-列表查询")
    @GetMapping({"/list"})
    public Result<PageInfo<TspVehiclePageListDTO>> list(TspVehiclePageListVO vo) {
        return Result.ok(tspVehicleService.getPageList(vo));
    }


    @PreAuthorize("@ss.hasPermi('tsp:vehicle:add')")
    @ApiOperation("车辆信息-新增")
    @Log(title = "车辆信息- 新增", businessType = BusinessType.INSERT)
    @PostMapping({"/add"})
    public Result<TspVehicleInfoDTO> add(@RequestBody @Valid TspVehicleAddVO vo) {
        return Result.ok(tspVehicleService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicle:edit')")
    @ApiOperation("车辆信息-编辑")
    @Log(title = "车辆信息-编辑", businessType = BusinessType.UPDATE)
    @PutMapping({"/edit"})
    public Result edit(@RequestBody @Valid TspVehicleAddVO vo) {
        return Result.ok(tspVehicleService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicle:delete')")
    @ApiOperation("车辆信息-删除")
    @Log(title = "车辆信息- 删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/delete/{tspVehicleId}"})
    public JsonResult delete(@PathVariable Long tspVehicleId) {
        return JsonResult.getResult(tspVehicleService.delete(tspVehicleId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicle:deletes')")
    @ApiOperation("车辆信息-批量删除")
    @Log(title = "车辆信息 - 批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/deletes/{tspVehicleIds}"})
    public JsonResult deletes(@PathVariable Long[] tspVehicleIds) {
        return JsonResult.getResult(tspVehicleService.deletes(tspVehicleIds));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicle:bind')")
    @ApiOperation("根据车辆idhe用户id进行车辆绑定")
    @Log(title = "车辆信息- 绑定", businessType = BusinessType.UPDATE)
    @PatchMapping({"/bind/{tspVehicleId}/{tspUserId}"})
    public JsonResult bind(@PathVariable Long tspVehicleId, @PathVariable Long tspUserId) {
        return JsonResult.getResult(tspVehicleService.bind(tspVehicleId, tspUserId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:vehicle:scrap')")
    @ApiOperation("车辆信息- 报废")
    @Log(title = "车辆信息- 报废", businessType = BusinessType.UPDATE)
    @PutMapping({"/scrap"})
    public JsonResult scrap(@RequestBody @Valid TspVehicleScrapVO vo) {
        return JsonResult.getResult(tspVehicleService.scrap(vo));
    }

    @ApiOperation("车辆信息- 详情")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:get')")
    @GetMapping({"/get/{tspVehicleId}"})
    public Result<TspVehicleInfoDTO> get(@PathVariable Long tspVehicleId) {
        return Result.ok(tspVehicleService.get(tspVehicleId));
    }

    @ApiOperation("车辆信息- 解绑")
    @GetMapping({"/dealEquipment/{tspEquipmentId}"})
    public Result dealEquipment(@PathVariable Long tspEquipmentId) {
        return Result.ok(tspVehicleService.dealEquipment(tspEquipmentId));
    }

    @ApiOperation("车辆信息- 导出")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:export')")
    @Log(title = "车辆信息- 导出", businessType = BusinessType.EXPORT)
    @PostMapping({"/export"})
    public void export(HttpServletResponse response, TspVehiclePageListVO vo) {
        List<TspVehicleExportListDTO> list = tspVehicleService.exportList(vo);
        ExcelUtil<TspVehicleExportListDTO> util = new ExcelUtil(TspVehicleExportListDTO.class);
        util.exportExcel(response,list, "车辆信息");
    }

    @ApiOperation("车辆管理-导入出厂信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:importVehicle')")
    @Log(title = "车辆管理- 导入出厂信息", businessType = BusinessType.IMPORT)
    @PostMapping({"/importVehicle"})
    public JsonResult importVehicle(@RequestParam("file") MultipartFile multipartFile, Boolean isUpdateSupport) {
        return JsonResult.getResult(tspVehicleService.importVehicle(multipartFile, isUpdateSupport));
    }

    @ApiOperation("车辆管理-导入汽车销售信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:importSales')")
    @Log(title = "车辆管理-导入汽车销售信息", businessType = BusinessType.IMPORT)
    @PostMapping({"/importSales"})
    public JsonResult importSales(@RequestParam("file") MultipartFile multipartFile, Boolean isUpdateSupport) {
        return JsonResult.getResult(tspVehicleService.importSales(multipartFile, isUpdateSupport));
    }

    @ApiOperation("车辆管理-下载车辆出厂信息模版")
    @PostMapping({"/importExFactoryTemplate"})
    public AjaxResult importExFactoryTemplate() {
        ExcelUtil<TspVehicleExFactoryTemplateDTO> util = new ExcelUtil(TspVehicleExFactoryTemplateDTO.class);
        return util.importTemplateExcel("出厂信息数据");
    }

    @ApiOperation("车辆管理-下载车辆销售信息模版")
    @GetMapping({"/importSaleTemplate"})
    public AjaxResult importSaleTemplate() {
        ExcelUtil<TspVehicleSaleTemplateDTO> util = new ExcelUtil(TspVehicleSaleTemplateDTO.class);
        return util.importTemplateExcel("车辆销售信息数据");
    }

    @ApiOperation("车辆管理-根据车辆ID查认证信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:getAuditInfo')")
    @GetMapping({"/getAuditInfo/{tspVehicleId}"})
    public Result<TspVehicleAuditInfoDTO> getAuditInfo(@PathVariable Long tspVehicleId) {
        return Result.ok(tspVehicleService.getAuditInfo(tspVehicleId));
    }

    @ApiOperation("车辆管理-关联账号")
    @GetMapping({"/relationMobileOption"})
    public Result<List<TspVehicleRelationMobileOptionDTO>> relationMobileOption() {
        return Result.ok(tspVehicleService.relationMobileOption());
    }

    @ApiOperation("车辆管理-导出车辆出厂信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:exportExFactory')")
    @Log(title = "- ", businessType = BusinessType.EXPORT)
    @PostMapping({"/exportExFactory"})
    public void exportExFactory(HttpServletResponse response,TspVehiclePageListVO vo) {
        List<TspVehicleExFactoryTemplateDTO> list = tspVehicleService.exportExFactory(vo);
        ExcelUtil<TspVehicleExFactoryTemplateDTO> util = new ExcelUtil(TspVehicleExFactoryTemplateDTO.class);
        util.exportExcel(response,list, "车辆出厂信息");
    }

    @ApiOperation("车辆管理-导出车辆销售信息")
    @PreAuthorize("@ss.hasPermi('tsp:vehicle:exportSales')")
    @Log(title = "车辆管理- 导出车辆销售信息", businessType = BusinessType.EXPORT)
    @PostMapping({"/exportSales"})
    public void exportSales(HttpServletResponse response,TspVehiclePageListVO vo) {
        List<TspVehicleSaleTemplateDTO> list = tspVehicleService.exportSales(vo);
        ExcelUtil<TspVehicleSaleTemplateDTO> util = new ExcelUtil(TspVehicleSaleTemplateDTO.class);
        util.exportExcel(response,list, "车辆销售信息");
    }

}
