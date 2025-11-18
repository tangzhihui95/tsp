package com.modern.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.common.utils.JsonResult;
import com.modern.model.dto.TspDealerInfoDTO;
import com.modern.model.dto.TspDealerPageListDTO;
import com.modern.model.vo.TspDealerAddVO;
import com.modern.model.vo.TspDealerPageListVO;
import com.modern.service.TspDealerService;
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
 * @name：TspDealerController
 * @Date：2025/11/5 9:48
 * @Filename：TspDealerController
 */
@RestController
@Api(tags = {"TSP-经销商管理"})
@RequestMapping({"/tsp/dealer"})
public class TspDealerController {

    @Autowired
    private TspDealerService tspDealerService;

    @ApiOperation("列表")
    @GetMapping({"/list"})
    public Result<PageInfo<TspDealerPageListDTO>> list(TspDealerPageListVO vo) {
        return Result.ok(tspDealerService.list(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:get')")
    @ApiOperation("详情")
    @GetMapping({"/get/{id}"})
    public Result<TspDealerInfoDTO> get(@PathVariable("id") Long tspDealerId) {
        return Result.ok(tspDealerService.get(tspDealerId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:add')")
    @ApiOperation("添加")
    @Log(title = "经销商-添加", businessType = BusinessType.INSERT)
    @PostMapping({"/add"})
    public JsonResult add(@RequestBody @Valid TspDealerAddVO vo) {
        return JsonResult.getResult(tspDealerService.add(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:edit')")
    @ApiOperation("修改")
    @Log(title = "经销商-修改", businessType = BusinessType.UPDATE)
    @PutMapping({"/edit"})
    public JsonResult edit(@RequestBody @Valid TspDealerAddVO vo) {
        return JsonResult.getResult(tspDealerService.edit(vo));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:delete')")
    @ApiOperation("删除")
    @Log(title = "经销商-删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/delete/{id}"})
    public JsonResult delete(@PathVariable("id") Long tspDealerId) {
        return JsonResult.getResult(tspDealerService.delete(tspDealerId));
    }

    @PreAuthorize("@ss.hasPermi('tsp:dealer:deletes')")
    @ApiOperation("批量删除")
    @Log(title = "经销商-批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping({"/deletes/{tspDealerIds}"})
    public JsonResult deletes(@PathVariable Long[] tspDealerIds) {
        return JsonResult.getResult(tspDealerService.batchDelete(tspDealerIds));
    }


}
