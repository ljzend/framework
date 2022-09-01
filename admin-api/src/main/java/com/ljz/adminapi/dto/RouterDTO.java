package com.ljz.adminapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>封装菜单信息</p>
 *
 * @Author : ljz
 * @Date: 2022/8/29  11:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("封装菜单信息")
public class RouterDTO {
    @ApiModelProperty("路由地址")
    private String path;
    @ApiModelProperty("路由对应的组件")
    private String component;
    @ApiModelProperty("是否显示")
    private boolean alwaysShow;
    @ApiModelProperty("路由名称")
    private String name;
    @ApiModelProperty("路由meta信息")
    private Meta meta;
    @ApiModelProperty("路由在面包屑导航中是否可被点击")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String redirect;

    @Data
    @AllArgsConstructor
    @ApiModel("路由meta信息")
    public class Meta {
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图标")
        private String icon;
        @ApiModelProperty("角色列表")
        private Object[] roles;
    }

    @ApiModelProperty("子路由")
    private List<RouterDTO> children = new ArrayList<>();
}
