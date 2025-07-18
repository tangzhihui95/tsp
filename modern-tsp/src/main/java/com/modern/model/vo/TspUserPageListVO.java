package com.modern.model.vo;

import com.modern.common.core.domain.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspUserPageListVO
 * @Date：2025/7/2 14:08
 * @Filename：TspUserPageListVO
 */
@ApiModel("用户-请求对象 -分页列表 ")
@Data
public class TspUserPageListVO extends BaseVO {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("需要导出的id集合")
    private List<Long> ids;
}
