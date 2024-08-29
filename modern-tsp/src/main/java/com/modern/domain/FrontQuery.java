package com.modern.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：FrontQuery
 * @Date：2024/8/28 16:55
 * @Filename：FrontQuery
 */
@ApiModel("查询 -请求对象-条件查询")
@Data
public class FrontQuery extends BaseVO {

    @ApiModelProperty("是否需要全部")
    private Integer needAll;

    @ApiModelProperty("类型ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentTypeId;

    @ApiModelProperty("型号ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentModelId;
}
