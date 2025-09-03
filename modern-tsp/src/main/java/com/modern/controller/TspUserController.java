package com.modern.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.JsonResult;
import com.modern.common.utils.poi.ExcelUtil;
import com.modern.model.dto.TspUserDTO;
import com.modern.model.dto.TspUserPageListDTO;
import com.modern.model.vo.TspUserAddVO;
import com.modern.model.vo.TspUserExcelVO;
import com.modern.model.vo.TspUserPageListVO;
import com.modern.service.TspUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @Author：tzh
 * @Package：com.modern.controller
 * @Project：tsp
 * @name：TspUserController
 * @Date：2025/7/2 13:57
 * @Filename：TspUserController
 */
@RestController
@RequestMapping({"/tsp/user"})
@Api(tags = {"TSP - 一般用户账户"})
public class TspUserController {

    @Autowired
    private TspUserService tspUserService;

    @ApiOperation("分页列表")
    @GetMapping({"/list"})
    public Result<PageInfo<TspUserPageListDTO>> list(TspUserPageListVO vo) {
        return Result.ok(tspUserService.getPageList(vo));
    }

    @PreAuthorize("@ss.hasAnyPermi('tsp:user:add')")
    @Log(title = "一般用户-添加", businessType = BusinessType.INSERT)
    @ApiOperation("添加")
    @PostMapping({"/add"})
    public JsonResult add(@RequestBody @Valid TspUserAddVO vo) {
        return JsonResult.getResult(tspUserService.add(vo));
    }

    @PreAuthorize("@ss.hasAnyPermi('tsp:user:edit')")
    @Log(title = "一般用户-修改", businessType = BusinessType.UPDATE)
    @ApiOperation("修改")
    @PutMapping({"/edit"})
    public JsonResult edit(@RequestBody @Valid TspUserAddVO vo) {
        return JsonResult.getResult(tspUserService.edit(vo));
    }

    @ApiOperation("一般用户-用户详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tspUserId", value = "用户ID", dataType = "Long", required = false),
    })
    @GetMapping({"/get/{tspUserId}"})
    public Result<TspUserDTO> get(@PathVariable("tspUserId") Long tspUserId) {
        return Result.ok(tspUserService.get(tspUserId));
    }

    @ApiOperation("一般用户-当前车辆详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tspUserId", value = "用户ID", dataType = "Long", required = false),
    })
    @GetMapping({"/find/{tspUserId}"})
    public Result<List<Map<String, Object>>> findCarInfo(@PathVariable("tspUserId") Long tspUserId) {
        return Result.ok(tspUserService.findCarInfo(tspUserId));
    }

    @ApiOperation("一般用户-历史绑定记录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tspUserId", value = "用户ID", dataType = "Long", required = false),
    })
    @GetMapping({"/findHistory/{tspUserId}"})
    public Result<List<Map<String, Object>>> findHistory(@PathVariable("tspUserId") Long tspUserId) {
        return Result.ok(tspUserService.findHistory(tspUserId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:user:deletes')")
    @Log(title = "一般用户-删除", businessType = BusinessType.DELETE)
    @ApiOperation("删除")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "tspUserIds", value = "用户ID集合", dataType = "Long", required = false),
    })
    @DeleteMapping({"deletes/{tspUserIds}"})
    public Result deletes(@PathVariable("tspUserIds") Long[] tspUserIds) {
        return Result.ok(tspUserService.deletes(tspUserIds));
    }

    @ApiOperation("一般用户-下载模版")
    @GetMapping({"/importTemplate"})
    public void importTemplate() {
        ExcelUtil<TspUserExcelVO> util = new ExcelUtil(TspUserExcelVO.class);
        util.importTemplateExcel("一般用户信息数据");
    }

    @ApiOperation("一般用户-导入")
    @PreAuthorize("@ss.hasPermi('tsp:user:importUser')")
    @Log(title = "- ", businessType = BusinessType.IMPORT)
    @PostMapping({"/importUser"})
    public Result importUser(MultipartFile file, Boolean isUpdateSupport) throws IOException {
        return Result.ok(tspUserService.importUser(file, isUpdateSupport));
    }

    @ApiOperation("一般用户-导出")
    @PreAuthorize("@ss.hasPermi('tsp:user:export')")
    @Log(title = "- ", businessType = BusinessType.EXPORT)
    @GetMapping({"/exportUser"})
    public void export(TspUserPageListVO vo) {
        List<TspUserPageListDTO> list = tspUserService.exportList(vo);
        ExcelUtil<TspUserPageListDTO> util = new ExcelUtil(TspUserPageListDTO.class);
        util.exportExcel(list, "一般用户信息数据");
    }

}
