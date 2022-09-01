package com.ljz.adminapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljz.adminapi.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 删除用户角色关系
     * @param userId 用户id
     * @return int
     */
    @Delete("delete from sys_user_role where user_id=#{userId}")
    int deleteUserRole(Long userId);

    /**
     * 保存用户角色关系
     * @param userId 用户id
     * @param roleIds 角色id
     * @return int
     */
    int saveUserRole(Long userId, List<Long> roleIds);
}
