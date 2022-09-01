package com.ljz.adminapi.vo;

import com.ljz.adminapi.pojo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 *
 * @Author : ljz
 * @Date: 2022/8/31  10:41
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryVo extends User {
    private Long pageNo = 1L;//当前页码
    private Long pageSize = 10L;//每页显示数量
}
