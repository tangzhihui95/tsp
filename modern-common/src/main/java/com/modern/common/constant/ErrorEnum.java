package com.modern.common.constant;

import com.modern.common.exception.ServiceException;

/**
 * @Author：tzh
 * @Package：com.modern.common.constant
 * @Project：tsp
 * @name：ErrorEnum
 * @Date：2024/8/30 15:45
 * @Filename：ErrorEnum
 */
public enum ErrorEnum {
    SYS_USER_USER_NULL_ERR(Integer.valueOf(1), Integer.valueOf(101), "用户不存在"),
    SYS_USER_PSW_IDENTICI_ERR(Integer.valueOf(1), Integer.valueOf(102), "新密码不能与旧密码相同"),
    SYS_USER_PSW_ATYPISM_ERR(Integer.valueOf(1), Integer.valueOf(103), "新密码与确认密码不一致"),
    SYS_USER_ROLE_NOT_PERMISSINON_ERR(Integer.valueOf(1), Integer.valueOf(104), "你无权操作"),
    SYS_USER_BIND_VEHICLE_NULL_ERR(Integer.valueOf(1), Integer.valueOf(105), "绑定车辆出现错误,请检查"),
    TSP_VEHICLE_VEHICLE_NULL_ERR(Integer.valueOf(2), Integer.valueOf(106), "车辆不存在,请检查"),
    TSP_EQUIPMENT_TYPE_NOT_NULL_ERR(Integer.valueOf(3), Integer.valueOf(107), "设备分类已存在"),
    TSP_EQUIPMENT_TYPE_NULL_ERR(Integer.valueOf(3), Integer.valueOf(108), "未找到设备分类"),
    TSP_EQUIPMENT_TYPE_DELETE_ERR(Integer.valueOf(3), Integer.valueOf(109), "设备分类下存在设备型号,无法删除"),
    TSP_EQUIPMENT_MODEL_NOT_NULL_ERR(Integer.valueOf(12), Integer.valueOf(110), "设备型号已存在"),
    TSP_EQUIPMENT_MODEL_NULL_ERR(Integer.valueOf(12), Integer.valueOf(111), "未找到该设备型号"),
    TSP_EQUIPMENT_MODEL_DELETE_ERR(Integer.valueOf(13), Integer.valueOf(112), "设备型号下存在设备无法删除"),
    TSP_EQUIPMENT_NULL_ERR(Integer.valueOf(4), Integer.valueOf(113), "未找到设备请检查"),
    TSP_EQUIPMENT_EQUIPMENT_DELETE_ERR(Integer.valueOf(4), Integer.valueOf(114), "设备下存在绑定车辆,无法删除"),
    TSP_VEHICLE_TYPE_VEHICLE_TYPE_NOT_NULL_ERR(Integer.valueOf(5), Integer.valueOf(115), "车辆分类已存在"),
    TSP_VEHICLE_TYPE_VEHICLE_TYPE_NULL_ERR(Integer.valueOf(5), Integer.valueOf(116), "未找到车辆分类,请检查"),
    TSP_VEHICLE_TYPE_VEHICLE_MODEL_NOT_DELETE_ERR(Integer.valueOf(5), Integer.valueOf(117), "车辆分类下存在车辆,无法删除"),
    TSP_VEHICLE_MODEL_VEHICLE_MODEL_NOT_NULL_ERR(Integer.valueOf(6), Integer.valueOf(118), "车辆车型已存在"),
    TSP_VEHICLE_MODEL_VEHICLE_MODEL_NULL_ERR(Integer.valueOf(6), Integer.valueOf(119), "未找到该车型"),
    TSP_VEHICLE_MODEL_VEHICLE_MODEL_DELETE_ERR(Integer.valueOf(6), Integer.valueOf(120), "车辆车型下存在型号车辆,无法删除"),
    TSP_VEHICLE_VEHICLE_CONFIGURE_NAME_NOT_NULL_ERR(Integer.valueOf(7), Integer.valueOf(121), "车辆配置名称已存在"),
    TSP_VEHICLE_SOLD_NULL_ERR(Integer.valueOf(7), Integer.valueOf(123), "未到车辆销售信息,无法绑定"),
    TSP_EQUIPMENT_SCRAP_ERR(Integer.valueOf(7), Integer.valueOf(124), "未绑定车辆不可进行报废操作,请先进行解绑"),
    TSP_VEHICLE_SCRAP_ERR(Integer.valueOf(7), Integer.valueOf(125), "密码输入错误,请重新输入"),
    TSP_VEHICLE_NULL_AUDIT(Integer.valueOf(7), Integer.valueOf(126), "未找到该车辆认证信息"),
    TSP_VEHICLE_NULL_SIGN_UP(Integer.valueOf(7), Integer.valueOf(137), "车辆尚未被注册"),
    TSP_VEHICLE_VIN_EXIST(Integer.valueOf(7), Integer.valueOf(138), "该VIN已被其他车辆使用"),
    TSP_VEHICLE_NOT_FOUND(Integer.valueOf(7), Integer.valueOf(158), "未找到该车辆实时信息,请检查该车辆是否启动"),
    TSP_VEHICLE_NOT_NULL_PLATE_CODE(Integer.valueOf(7), Integer.valueOf(168), "该车牌号不存在"),
    TSP_VEHICLE_PLATE_CODE_EXIST(Integer.valueOf(7), Integer.valueOf(169), "该车牌号已存在"),
    TSP_USER_USER_NOT_NULL_ERR(Integer.valueOf(8), Integer.valueOf(127), "用户手机号已存在,不可重复添加"),
    TSP_USER_USER_NULL_ERR(Integer.valueOf(8), Integer.valueOf(128), "未找到用户"),
    TSP_USER_USERS_NULL_ERR(Integer.valueOf(8), Integer.valueOf(129), "所选列表中存在未知用户,请检查。"),
    TSP_VEHICLE_STD_MODEL_TSP_VEHICLE_STD_MODEL_ADD_ERR(Integer.valueOf(9), Integer.valueOf(130), "车型车型号已存在,不可重复添加"),
    TSP_VEHICLE_STD_MODEL_NULL_ERR(Integer.valueOf(9), Integer.valueOf(131), "未找到该车型型号"),
    TSP_VEHICLE_ALERT_NULL_ERROR(Integer.valueOf(10), Integer.valueOf(132), "未找到该记录"),
    TSP_ALERT_EVENT_NULL_ERROR(Integer.valueOf(10), Integer.valueOf(133), "未找到该条数据"),
    TSP_ALERT_EVENT_NAME_ERROR(Integer.valueOf(10), Integer.valueOf(134), "名称已存在"),
    TSP_MESSAGE_TITLE_REPEAT_ERROR(Integer.valueOf(11), Integer.valueOf(135), "标题重复"),
    TSP_MESSAGE_NULL_ERROR(Integer.valueOf(11), Integer.valueOf(136), "通知不存在"),
    TSP_INFORMATION_NULL_ERROR(Integer.valueOf(11), Integer.valueOf(141), "信息不存在"),
    TSP_DEALER_NAME_REPEAT_ERROR(Integer.valueOf(14), Integer.valueOf(142), "经销商名称重复"),
    TSP_DEALER_CODE_REPEAT_ERROR(Integer.valueOf(14), Integer.valueOf(143), "经销商编码重复"),
    TSP_DEALER_NULL_ERROR(Integer.valueOf(14), Integer.valueOf(144), "经销商不存在"),
    TSP_PARAM_NULL_ERROR(Integer.valueOf(15), Integer.valueOf(145), "vin和车辆主键都是空值"),
    IDENTIFICATION_510_ERROR(Integer.valueOf(15), Integer.valueOf(146), "车辆vin和车厂编码不匹配"),
    IDENTIFICATION_520_ERROR(Integer.valueOf(15), Integer.valueOf(147), "实名认证参数丢失,请联系管理员"),
    IDENTIFICATION_NAME_ERROR(Integer.valueOf(15), Integer.valueOf(148), "名称与实际认证不匹配,请检查输入名称是否正确"),
    IDENTIFICATION_CARD_ERROR(Integer.valueOf(15), Integer.valueOf(149), "证件号码与实际认证不匹配,请检查输入的身份证号码是否正确"),
    IDENTIFICATION_ICCID_ERROR(Integer.valueOf(15), Integer.valueOf(150), "该iccid在车厂中不存在"),
    IDENTIFICATION_FAIL_ERROR(Integer.valueOf(15), Integer.valueOf(151), "实名认证失败"),
    TSP_SN_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(152), "该SN在设备中已存在"),
    TSP_ICCID_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(153), "该ICCID在设备中已存在"),
    TSP_IMEI_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(154), "该IMEI在设备中已存在"),
    TSP_IMSI_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(155), "该IMSI在设备中已存在"),
    TSP_SIM_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(156), "该SIM在设备中已存在"),
    TSP_VEHICLE_EQUIPMENT_EXIST(Integer.valueOf(7), Integer.valueOf(157), "该设备与其他车辆绑定"),
    IDENTIFICATION_NOT_EXIST_ERROR(Integer.valueOf(15), Integer.valueOf(159), "该车辆暂无实名认证信息,请确认是否已完成认证"),
    IDENTIFICATION_ERR(Integer.valueOf(16), Integer.valueOf(160), "该设备与其他车辆绑定"),
    IDENTIFICATION_CHECK_ERR(Integer.valueOf(16), Integer.valueOf(161), "缺少必须字段,请仔细检查"),
    IDENTIFICATION_USERNAME_ERR(Integer.valueOf(16), Integer.valueOf(162), "上传认证不通过,账号或密码错误"),
    IDENTIFICATION_DIANXIN_ERR(Integer.valueOf(16), Integer.valueOf(163), "电信认证平台系统异常"),
    IDENTIFICATION_SEND_ERR(Integer.valueOf(16), Integer.valueOf(164), "车卡信息推送失败"),
    IDENTIFICATION_NOT_FOUND_ERR(Integer.valueOf(16), Integer.valueOf(165), "服务不存在"),
    IDENTIFICATION_MOTOR_ENGINE_ERR(Integer.valueOf(16), Integer.valueOf(166), "电动机序列号和发动机号码必须有一个不为空"),
    IDENTIFICATION_OHTER_ERR(Integer.valueOf(16), Integer.valueOf(167), "车辆状态|新车标识存在空值"),
    IDENTIFICATION_MARKET_ERR(Integer.valueOf(16), Integer.valueOf(168), "销售信息不为空"),
    IDENTIFICATION_EQUIPMENT_ERROR(Integer.valueOf(15), Integer.valueOf(169), "该车辆未绑定设备信息"),
    IDENTIFICATION_EQUIPMENT_ONE_ERROR(Integer.valueOf(15), Integer.valueOf(170), "该车辆只有一次设备绑定记录,不满足更换条件"),
    CONFIG_DELETE_ERR(Integer.valueOf(17), Integer.valueOf(171), "内置参数无法删除,请先将参数设置为非系统内置"),
    TSP_VEHICLE_DELETE_STATE_ERR(Integer.valueOf(7), Integer.valueOf(300), "删除失败!只能删除已解除绑定状态车辆"),
    TSP_VEHICLE_DELETES_STATE_ERR(Integer.valueOf(7), Integer.valueOf(301), "删除失败!所选车辆中只能删除已解除绑定状态车辆"),
    TSP_VEHICLE_EQUIPMENT_BIND_DELETE_ERR(Integer.valueOf(7), Integer.valueOf(302), "删除失败!该车辆已绑定了设备！"),
    TSP_VEHICLE_EQUIPMENT_BIND_DELETES_ERR(Integer.valueOf(7), Integer.valueOf(303), "删除失败!所选车辆中已绑定了设备！");

    private Integer type;

    private Integer code;

    private String msg;

    ErrorEnum(Integer type, Integer code, String msg) {
        this.type = type;
        this.code = code;
        this.msg = msg;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void throwErr(Exception e) {
        throw new ServiceException(String.valueOf(e));
    }

    public void throwErr(String msg, Integer code, Integer type) {
        this.msg = msg;
        this.code = code;
        this.type = type;
        throw new ServiceException(this);
    }

    public void throwErr() {
        throw new ServiceException(this);
    }

}
