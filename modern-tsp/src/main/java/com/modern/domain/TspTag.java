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
 * @name：TspTag
 * @Date：2024/10/17 17:07
 * @Filename：TspTag
 */

@Table(name = "tsp_tag", comment = "摩登-TSP-标签管理")
@Alias("TspTag")
@TableName(value = "tsp_tag", autoResultMap = true)
@Data
public class TspTag extends BaseModel {

    @Column(comment = "标签名称", type = MySqlTypeConstant.VARCHAR, length = 36, isNull = false)
    private String tagName;

    @Column(comment = "标签类型", type = MySqlTypeConstant.INT, length = 11, isNull = false)
    private Integer tagType;

    @Column(comment = "关联数量", type = MySqlTypeConstant.INT, length = 11, isNull = false, defaultValue = "0")
    private Integer tagCount;
}
