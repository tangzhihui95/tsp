package com.modern.exinterface.Controller;

import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.exinterface.Service.VehicleDataService;
import com.modern.exinterface.Service.VehicleIntegrateService;
import com.modern.exinterface.dto.VehicleIntegrateParsedDTO;
import com.modern.exinterface.vo.VehicleSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author：tzh
 * @Package：Controller
 * @Project：tsp
 * @name：VehicleDataController
 * @Date：2025/06/12 15:08
 * @Filename：VehicleDataController
 */
@RestController
@Api(tags = {"TSP - 车辆数据"})
@RequestMapping({"/tsp/vehicle/data"})
public class VehicleDataController {
    @Autowired
    private VehicleDataService vehicleDataService;

    @Autowired
    private VehicleIntegrateService vehicleIntegrateService;

    @ApiOperation("最新实时数据-通过VIN查询")
    @GetMapping({"/latestData/{vin}"})
    public Result<Map<String, Object>> latestData(@PathVariable("vin") String vin) {
        return Result.ok(vehicleDataService.getLatestData(vin));
    }
    @ApiOperation("整车数据分页查询")
    @GetMapping({"/list"})
    public Result<PageInfo<VehicleIntegrateParsedDTO>> list(@RequestBody @Valid VehicleSearchVO vo) {
        return Result.ok(vehicleIntegrateService.getPageList(vo));
    }



}
