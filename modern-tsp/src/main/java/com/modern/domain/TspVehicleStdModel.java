package com.modern.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import com.modern.enums.TpsVehicleDataKeyEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;



/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspVehicleStdModel
 * @Date：2024/10/14 15:57
 * @Filename：TspVehicleStdModel
 */

@Table(name = "tsp_vehicle_std_model", comment = "摩登- TSP - 车辆车型")
@Alias("TspVehicleStdModel")
@TableName("tsp_vehicle_std_model")
@Data
public class TspVehicleStdModel extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "车型ID", type = MySqlTypeConstant.BIGINT, isNull = false)
    private Long tspVehicleModelId;

    @Column(comment = "型号名称", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String stdModeName;

    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    @Column(comment = "能源类型", type = MySqlTypeConstant.TINYINT)
    private TpsVehicleDataKeyEnum dataKey;

    @Column(comment = "公告批次", type = MySqlTypeConstant.VARCHAR)
    private String noticeBatch;

    @Column(comment = "公告型号", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String noticeModel;

    @Column(comment = "车辆厂商", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String firm;

}
