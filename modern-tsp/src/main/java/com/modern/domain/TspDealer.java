package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspDealer
 * @Date：2024/11/7 13:43
 * @Filename：TspDealer
 */

@Table(name = "tsp_dealer", comment = "摩登- TSP - 经销商")
@Alias("TspDealer")
@TableName(value = "tsp_dealer", autoResultMap = true)
@Data
public class TspDealer extends BaseModel {
    @Column(comment = "经销商名称", type = MySqlTypeConstant.VARCHAR, length = 50, isNull = false)
    private String dealerName;

    @Column(comment = "经销商编码", type = MySqlTypeConstant.VARCHAR, length = 50, isNull = false)
    private String dealerCode;

    @Column(comment = "经销商电话", type = MySqlTypeConstant.VARCHAR, length = 22, isNull = false)
    private String dealerPhone;

    @Column(comment = "经销商所在省", type = MySqlTypeConstant.VARCHAR, length = 63, isNull = false)
    private String dealerProvince;

    @Column(comment = "经销商所在市", type = MySqlTypeConstant.VARCHAR, length = 63, isNull = false)
    private String dealerCity;

    @Column(comment = "经销商所在区", type = MySqlTypeConstant.VARCHAR, length = 63, isNull = false)
    private String dealerDistrict;

    @Column(comment = "经销商所在街道地址", type = MySqlTypeConstant.VARCHAR, length = 510, isNull = false)
    private String dealerStreetAndStreetNumber;

    @Column(comment = "经销商地址", type = MySqlTypeConstant.VARCHAR, length = 500, isNull = false)
    private String dealerAddress;

    @Column(comment = "经销商纬度", type = MySqlTypeConstant.VARCHAR, length = 50, isNull = false)
    private String dealerLat;

    @Column(comment = "经销商经度", type = MySqlTypeConstant.VARCHAR, length = 50, isNull = false)
    private String dealerLng;
}
