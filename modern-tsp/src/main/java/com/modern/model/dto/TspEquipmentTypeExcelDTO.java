package com.modern.model.dto;

import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备信息导出实体
 *
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspEquipmentTypeExcelDTO
 * @Date：2024/9/11 15:19
 * @Filename：TspEquipmentTypeExcelDTO
 */

@ApiModel("设备信息-数据传输对象 -导入导出 ")
@Data
public class TspEquipmentTypeExcelDTO {
    @Excel(name = "设备类型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 1)
    @ApiModelProperty("设备类型名称")
    private String name;

    @Excel(name = "是否为终端", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "1:是 0:否", sort = 2)
    @ApiModelProperty("是否为终端 1:是 0:否")
    private Integer isTerminal;

    @Excel(name = "设备扩展信息类型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 3)
    @ApiModelProperty("设备扩展信息类型")
    private String extraType;

    @Excel(name = "设备型号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 4)
    @ApiModelProperty("设备型号")
    private String modelName;

    @Excel(name = "供应商", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 5)
    @ApiModelProperty("供应商")
    private String suppliers;

    @Excel(name = "生产批次", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 6)
    @ApiModelProperty("生产批次")
    private String batchNumber;

    @Excel(name = "关联设备", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 7)
    @ApiModelProperty("关联设备")
    private Integer count;
}
