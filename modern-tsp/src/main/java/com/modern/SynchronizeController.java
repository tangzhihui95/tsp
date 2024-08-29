package com.modern;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TangZhiHui
 * @Date 2024/8/9 9:36
 * @Version: 1.0
 * @description:
 **/
@RestController
@RequestMapping({"/synchronize"})
@Api(tags = {"TSP - OTA"})
public class SynchronizeController {

    /*@Autowired
    public SynchronizeController(SynVehicleMapper synVehicleMapper, SynStdModelMapper synStdModelMapper, SynModelMapper synModelMapper) {
        this.synVehicleMapper = synVehicleMapper;
        this.synStdModelMapper = synStdModelMapper;
        this.synModelMapper = synModelMapper;
    }*/
/*    @ApiOperation("")
    @PostMapping({"/vehicle"})
    public List<SynVehicle> vehicleSynchronize() {
        return this.synVehicleMapper.query();
    }*/

}
