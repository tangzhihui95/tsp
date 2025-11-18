package com.modern.model.vo;

import com.modern.common.core.domain.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspDealerPageListVO
 * @Date：2025/11/5 10:28
 * @Filename：TspDealerPageListVO
 */
@ApiModel("经销商管理-请求对象-分页列表")
@Data
public class TspDealerPageListVO extends BaseVO {

    @ApiModelProperty("经销商名称")
    private String dealerName;

    @ApiModelProperty("经销商地址")
    private String dealerAddress;
}
