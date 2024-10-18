package com.modern.controller;

import com.modern.common.annotation.Log;
import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.common.enums.BusinessType;
import com.modern.model.dto.TspVehicleInfoDTO;
import com.modern.model.dto.TspVehiclePageListDTO;
import com.modern.model.vo.TspVehicleAddVO;
import com.modern.model.vo.TspVehiclePageListVO;
import com.modern.service.TspVehicleService;
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
}
