package com.modern.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.modern.common.core.domain.BaseModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

/**
 * @Author：tzh
 * @Package：com.modern.domain
 * @Project：tsp
 * @name：TspUser
 * @Date：2024/10/22 17:18
 * @Filename：TspUser
 */
@Table(name = "tsp_user", comment = "摩登- TSP - TSP用户")
@Alias("TspUser")
@TableName("tsp_user")
@Data
public class TspUser extends BaseModel {
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "sys用户主键ID", type = MySqlTypeConstant.BIGINT)
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(comment = "sys用户部门主键ID", type = MySqlTypeConstant.BIGINT)
    private Long deptId;

    @Column(comment = "手机号(账号)", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String mobile;

    @Column(comment = "真实姓名", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String realName;

    @Column(comment = "用户头像", type = MySqlTypeConstant.VARCHAR, length = 500)
    private String avatar;

    @Column(comment = "身份证号", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String idCard;

    @Column(comment = "用户性别 1-男 2-女 0-未知", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer sex;

    @Column(comment = "用户是否绑定车辆 0-没有 1-有 (默认为0)", type = MySqlTypeConstant.TINYINT, isNull = false, defaultValue = "0")
    private Integer hasBind;

    @Column(comment = "账户类型", type = MySqlTypeConstant.TINYINT, isNull = false, defaultValue = "0")
    private Integer type;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(comment = "出生日期", type = MySqlTypeConstant.DATE)
    private LocalDate birthday;

    @Column(comment = "极光推送ID", type = MySqlTypeConstant.BIGINT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long registrationId;

    @Column(comment = "注册地址(省)", type = MySqlTypeConstant.VARCHAR, length = 55)
    private String province;

    @Column(comment = "注册地址(市)", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String city;

    @Column(comment = "注册地址(区)", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String area;

    @Column(comment = "实名认证 0-未认证 1-已认证 （默认为0）", type = MySqlTypeConstant.TINYINT, defaultValue = "0")
    private Integer realNameAudit;

    @Column(comment = "注册渠道 0-平台账号", type = MySqlTypeConstant.TINYINT, defaultValue = "0")
    private Integer registeredChannels;

    @Column(comment = "详细地址", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String address;

    @Column(comment = "身份证正面", type = MySqlTypeConstant.VARCHAR)
    private String userCardFrontImg;

    @Column(comment = "身份证反面", type = MySqlTypeConstant.VARCHAR)
    private String userCardBackImg;

    @Column(comment = "用户标签", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String label;

    @Column(comment = "是否启用 1-是 0-否", type = MySqlTypeConstant.TINYINT, isNull = false, defaultValue = "1")
    private Boolean isEnable;

    @Column(comment = "用户来源 0-未知 1-商店 2-公众号 3-第三方", type = MySqlTypeConstant.INT, isNull = false, defaultValue = "0")
    private Integer source;
}
