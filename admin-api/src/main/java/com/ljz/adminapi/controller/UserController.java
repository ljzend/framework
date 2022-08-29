package com.ljz.adminapi.controller;

import com.ljz.adminapi.config.constant.SystemConstant;
import com.ljz.adminapi.dto.R;
import com.ljz.adminapi.dto.RouterDTO;
import com.ljz.adminapi.dto.TokenDTO;
import com.ljz.adminapi.dto.UserInfoDTO;
import com.ljz.adminapi.pojo.Permission;
import com.ljz.adminapi.pojo.User;
import com.ljz.adminapi.service.UserService;
import com.ljz.adminapi.utils.JwtUtils;
import com.ljz.adminapi.utils.MenuTree;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ljz
 * @since 2022-08-27 14-25-33
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "控制类(UserController)")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("")
    @ApiOperation("获取用户信息")
    public R user() {
        return R.ok(userService.list());
    }

    @PostMapping("/refreshToken")
    @ApiOperation("刷新token")
    public R refreshToken(HttpServletRequest request) {
        //从header中获取前端提交的token
        String token = request.getHeader("token");
        //如果header中没有token，则从参数中获取
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        //从Spring Security上下文获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtils.isEmpty(authentication)) {
            return R.error().message("用户信息查询失败");
        }
        //获取身份信息
        UserDetails details = (UserDetails) authentication.getPrincipal();
        //重新生成token
        String reToken = "";
        //验证原来的token是否合法
        if (jwtUtils.validateToken(token, details)) {
            //生成新的token
            reToken = jwtUtils.refreshToken(token);
        }
        //获取本次token的到期时间，交给前端做判断
        long expireTime = Jwts.parser()
                .setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(reToken.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        //清除原来的token信息
        String oldTokenKey = SystemConstant.Token.LOGIN_TOKEN_PREFIX + token;
        redisTemplate.delete(oldTokenKey);
        //存储新的token
        String newTokenKey = SystemConstant.Token.LOGIN_TOKEN_PREFIX + reToken;
        redisTemplate.opsForValue().set(newTokenKey, reToken, jwtUtils.getExpiration() / 1000, TimeUnit.SECONDS);
        //创建TokenVo对象
        TokenDTO tokenVo = new TokenDTO(expireTime, reToken);
        //返回数据
        return R.ok(tokenVo).message("token生成成功");
    }

    @GetMapping("/getInfo")
    @ApiOperation("获取登录用户信息")
    public R getInfo() {
        // 从 SecurityContextHolder 上下文中获取用些信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtils.isEmpty(authentication)) {
            return R.error().message("用户信息查询失败");
        }
        // 提取用户信息
        User user = (User) authentication.getPrincipal();
        List<Permission> permissionList = user.getPermissionList();
        //获取角色权限编码字段
        Object[] roles = permissionList.stream()
                .filter(Objects::nonNull)
                .map(Permission::getCode).toArray();
        //创建用户信息对象
        UserInfoDTO userInfoDTO = new UserInfoDTO().setId(user.getId())
                .setName(user.getNickName())
                .setAvatar(user.getAvatar())
                .setIntroduction(null)
                .setRoles(roles);
        return R.ok(userInfoDTO).message("用户信息查询成功");
    }

    @GetMapping("/getMenuList")
    @ApiOperation("获取登录用户所拥有的菜单信息")
    public R getMenuList() {
        //从Spring Security上下文获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtils.isEmpty(authentication)) {
            return R.error().message("用户信息查询失败");
        }
        //获取用户信息
        User user = (User) authentication.getPrincipal();
        //获取相应的权限
        List<Permission> permissionList = user.getPermissionList();
        //筛选目录和菜单
        List<Permission> collect = permissionList.stream()
                .filter(item -> item != null && item.getType() != 2)
                .collect(Collectors.toList());
        //生成路由数据
        List<RouterDTO> routerVoList = MenuTree.makeRouter(collect, 0L);
        //返回数据
        return R.ok(routerVoList).message("菜单数据获取成功");
    }
}
