package com.modern.model.dto;

import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspEquipmentTypeImportDTO
 * @Date：2024/9/11 16:00
 * @Filename：TspEquipmentTypeImportDTO
 */
@Data
@ApiModel("设备信息- 数据传输对象-导入导出")
public class TspEquipmentTypeImportDTO {

    @Excel(name = "", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 1)
    private String name;

    @Excel(name = "", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 3)
    private String terminal;

    @Excel(name = "", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 3)
    private String extraType;
}
