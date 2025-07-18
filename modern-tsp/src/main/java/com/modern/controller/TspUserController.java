package com.modern.controller;

import com.modern.common.core.domain.Result;
import com.modern.common.core.page.PageInfo;
import com.modern.model.dto.TspUserPageListDTO;
import com.modern.model.vo.TspUserPageListVO;
import com.modern.service.TspUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
@Api(tags = {"TSP - 用户"})
public class TspUserController {

    @Autowired
    private TspUserService tspUserService;

    @ApiOperation("分页列表")
    @GetMapping({"/list"})
    public Result<PageInfo<TspUserPageListDTO>> list(@RequestBody TspUserPageListVO vo) {
        return Result.ok(tspUserService.getPageList(vo));
    }
}
