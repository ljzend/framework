package com.ljz.adminapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>统一响应结果</p>
 *
 * @Author : ljz
 * @Date: 2022/8/27  15:00
 */
@ApiModel("统一返回结果")
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> {
    @ApiModelProperty("是否成功")
    private Boolean success;
    @ApiModelProperty("响应状态码")
    private Integer code;
    @ApiModelProperty("响应消息")
    private String msg;
    @ApiModelProperty("返回数据")
    private T data;
    @ApiModelProperty("时间戳")
    private long timestamp;

    private R() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功执行，不返回数据
     *
     * @return R
     */
    public static <T> R<T> ok() {
        R<T> R = new R<T>();
        R.setSuccess(true);
        R.setCode(ResultCode.SUCCESS.getCode());
        R.setMsg(ResultCode.SUCCESS.getMessage());
        return R;
    }

    /**
     * 成功执行，并返回数据
     *
     * @param data 数据
     * @param <T> 类型
     * @return R
     */
    public static <T> R<T> ok(T data) {
        R<T> R = new R<T>();
        R.setSuccess(true);
        R.setCode(ResultCode.SUCCESS.getCode());
        R.setMsg(ResultCode.SUCCESS.getMessage());
        R.setData(data);
        return R;
    }

    /**
     * 失败
     *
     * @return R
     */
    public static <T> R<T> error() {
        R<T> R = new R<T>();
        R.setSuccess(false);
        R.setCode(ResultCode.ERROR.getCode());
        R.setMsg(ResultCode.ERROR.getMessage());
        return R;
    }

    /**
     * 设置是否成功
     *
     * @param success 是否成功
     * @return R
     */
    public R<T> success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * 设置状态码
     *
     * @param code 状态码
     * @return R
     */
    public R<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 设置返回消息
     *
     * @param message 返回消息
     * @return R
     */
    public R<T> message(String message) {
        this.setMsg(message);
        return this;
    }

    /**
     * 是否存在
     *
     * @return R
     */
    public static <T> R<T> exist() {
        R<T> R = new R<>();
        R.setSuccess(true);
        R.setCode(ResultCode.SUCCESS.getCode());
        R.setMsg(ResultCode.SUCCESS.getMessage());
        return R;
    }
}