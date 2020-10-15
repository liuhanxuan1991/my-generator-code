package com.lhx.study.mygeneratorcode.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一状态码
 */
public enum StatusEnum {

    OK(0, "操作成功"),

    USERNAME_PASSWORD_IS_EMPTY(10001, "用户名或密码不能为空"),
    USERNAME_PASSWORD_ERROR(10002, "用户名或密码错误"),
    VALIDATE_CODE_INVALID(10003, "验证码错误"),
    USERNAME_STATUS_ERROR(10004,"帐号异常，请联系管理员"),
    USERNAME_PASSWORD_INCONFORMITY(10005,"两次输入密码不一致"),
    OLD_PASSWORD_ERROR(10006,"旧密码错误"),
    OLD_NEW_PASSWORD_ACCORDANCE(10007,"新密码不能与旧密码一致"),
    PASSWORD_ERROR(10008, "密码错误"),


    PARAMS_ERROR(400, "参数错误"),
    SEARCH_NOT_EXIST(401, "查询不存在"),
    OP_ERROR(402, "操作失败"),
    SERVER_ERROR(500, "服务器错误"),
    TOKEN_ERROR(501, "没有权限访问");

    private int code;

    private String value;

    StatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取map集合枚举
     *
     * @return
     */
    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (StatusEnum e : StatusEnum.values()) {
            map.put(e.getCode(), e.getValue());
        }
        return map;
    }

    /**
     * 获取list集合枚举
     *
     * @return
     */
    public static List<StatusEnum> toList() {
        List<StatusEnum> list = new ArrayList<StatusEnum>();
        for (StatusEnum e : StatusEnum.values()) {
            list.add(e);
        }
        return list;
    }

    @Override
    public String toString() {
        return "StatusEnum{" +
                "code=" + code +
                ", value='" + value + '\'' +
                '}';
    }
}
