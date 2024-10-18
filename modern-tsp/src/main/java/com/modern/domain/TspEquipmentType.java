package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;



/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspEquipmentType
 * @Date：2024/8/28 17:05
 * @Filename：TspEquipmentType
 */
@Data
@Alias("TspEquipmentType")
@Table(name = "tsp_equipment_type", comment = "摩登-TSP-设备类型")
@TableName(value = "tsp_equipment_type", autoResultMap = true)
public class TspEquipmentType extends BaseModel {
    private static final long serialVersionUID = 1L;

    @Column(comment = "设备类型", type = MySqlTypeConstant.VARCHAR, length = 55, isNull = false)
    @Excel(name = "设备类型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 1)
    private String name;
    @Column(comment = "供应商", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String suppliers;
    @Column(comment = "是否为终端1-是 0-否", type = MySqlTypeConstant.TINYINT, isNull = false, defaultValue = "1")
    private Integer isTerminal;
    @Excel(name = "设备扩展信息", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 3)
    @Column(comment = "设备扩展信息", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String extraType;

    public static final String NAME = "name";

    public static final String IS_TERMINAL = "is_terminal";

    public static final String EXTRA_TYPE = "extra_type";

}
