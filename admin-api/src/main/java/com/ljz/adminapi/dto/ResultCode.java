package com.ljz.adminapi.dto;

/**
 * <p>响应状态码</p>
 *
 * @Author : ljz
 * @Date: 2022/8/27  14:55
 */

public enum ResultCode {
    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    NO_LOGIN(401, "未登录"),
    NO_AUTH(403, "无权限");
    private Integer code;
    private String message;

    ResultCode(Integer code) {
        this.code = code;
    }

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
