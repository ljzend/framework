package com.ljz.adminapi.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>操作日志类</p>
 *
 * @Author : ljz
 * @Date: 2022/9/2  17:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@TableName("sys_operation_log")
public class OperationLog implements Serializable {
    private static final long serialVersionUID= 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作用户")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "操作名称")
    @TableField("operation_name")
    private String operationName;

    @TableField(exist = false)
    private boolean save;

    @ApiModelProperty(value = "请求路径")
    @TableField("request_uri")
    private String requestUri;

    @ApiModelProperty(value = "请求方式")
    @TableField("method_type")
    private String methodType;

    @ApiModelProperty(value = "请求参数")
    @TableField("request_param")
    private String requestParam;

    @ApiModelProperty(value = "方法名")
    @TableField("method_name")
    private String methodName;

    @ApiModelProperty(value = "响应状态码")
    @TableField("code")
    private Integer code;

    @ApiModelProperty(value = "返回参数")
    @TableField("response_param")
    private String responseParam;

    @ApiModelProperty(value = "异常信息")
    @TableField("exception_msg")
    private String exceptionMsg;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern ="yyyy-MM-dd:HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
}
