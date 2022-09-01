/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : framework

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 01/09/2022 16:54:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `department_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门地址',
  `pid` bigint NOT NULL COMMENT '所属部门编号',
  `parent_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属部门名称',
  `order_num` int NULL DEFAULT NULL COMMENT '排序',
  `is_delete` tinyint NULL DEFAULT 0 COMMENT '是否删除(0-未删除 1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES (1, '广州码农信息技术有限公司', '020-8888888', '广州市天河区', 0, '顶级部门', 0, 0);
INSERT INTO `sys_department` VALUES (2, '软件技术部', '020-88881001', '广州市天河区', 1, '广州码农信息技术有限公司', 1, 0);
INSERT INTO `sys_department` VALUES (3, '人事管理部', '020-88881002', '广州市天河区', 1, '广州码农信息技术有限公司', 1, 0);
INSERT INTO `sys_department` VALUES (4, '市场管理部', '020-88881003', '广州市天河区', 1, '广州码农信息技术有限公司', 1, 0);
INSERT INTO `sys_department` VALUES (5, '软件研发部', '020-88881234', '广州市天河区', 1, '广州码农信息技术有限公司', 2, 0);
INSERT INTO `sys_department` VALUES (11, 'java技术部', '111111', '广州', 2, '软件技术部', 2, 1);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父权限ID',
  `parent_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父权限名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权标识符',
  `path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权路径',
  `type` tinyint NULL DEFAULT NULL COMMENT '权限类型(0-目录 1-菜单 2-按钮)',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `order_num` int NULL DEFAULT NULL COMMENT '排序',
  `is_delete` tinyint NULL DEFAULT 0 COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '系统管理', 0, '顶级菜单', 'sys:manager', '/system', 'system', '/system/system', 0, 'el-icon-menu', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, 0, 0);
INSERT INTO `sys_permission` VALUES (2, '部门管理', 1, '系统管理', 'sys:department', '/department', 'department', '/system/department/department', 1, 'el-icon-s-tools', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (3, '新增', 2, '部门管理', 'sys:department:add', NULL, NULL, NULL, 2, 'el-icon-plus', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (4, '修改', 2, '部门管理', 'sys:department:edit', NULL, NULL, NULL, 2, 'el-icon-edit', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (5, '删除', 2, '部门管理', 'sys:department:delete', NULL, NULL, NULL, 2, 'el-icon-delete', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (6, '用户管理', 1, '系统管理', 'sys:user', '/userList', 'userList', '/system/user/userList', 1, 'el-icon-s-custom', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (7, '新增', 6, '用户管理', 'sys:user:add', NULL, NULL, NULL, 2, 'el-icon-plus', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (8, '修改', 6, '用户管理', 'sys:user:edit', NULL, NULL, NULL, 2, 'el-icon-edit', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (9, '删除', 6, '用户管理', 'sys:user:delete', NULL, NULL, NULL, 2, 'el-icon-delete', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (10, '角色管理', 1, '系统管理', 'sys:role', '/roleList', 'roleList', '/system/role/roleList', 1, 'el-icon-s-tools', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (11, '新增', 10, '角色管理', 'sys:role:add', NULL, NULL, NULL, 2, 'el-icon-plus', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (12, '修改', 10, '角色管理', 'sys:role:edit', NULL, NULL, NULL, 2, 'el-icon-edit', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (13, '删除', 10, '角色管理', 'sys:role:delete', NULL, NULL, NULL, 2, 'el-icon-delete', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (14, '菜单管理', 1, '系统管理', 'sys:menu', '/menuList', 'menuList', '/system/menu/menuList', 1, 'el-icon-s-tools', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (15, '新增', 14, '权限管理', 'sys:menu:add', NULL, NULL, NULL, 2, 'el-icon-plus', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (16, '修改', 14, '权限管理', 'sys:menu:edit', NULL, NULL, NULL, 2, 'el-icon-edit', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (17, '删除', 14, '权限管理', 'sys:menu:delete', NULL, NULL, NULL, 2, 'el-icon-delete', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (18, '资料管理', 0, '顶级菜单', 'sys:resource', '/resource', 'resource', '/resource/index', 0, 'el-icon-menu', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, 2, 0);
INSERT INTO `sys_permission` VALUES (19, '供应商管理', 18, '资料管理', 'sys:provider', '/providerList', 'providerList', '/system/provider/providerList', 1, 'el-icon-s-tools', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, 2, 0);
INSERT INTO `sys_permission` VALUES (20, '新增', 19, '供应商管理', 'sys:provider:add', NULL, NULL, NULL, 2, 'el-icon-plus', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (21, '修改', 19, '供应商管理', 'sys:provider:edit', NULL, NULL, NULL, 2, 'el-icon-edit', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (22, '删除', 19, '供应商管理', 'sys:provider:delete', NULL, NULL, NULL, 2, 'el-icon-delete', '2022-04-25 14:40:32', '2022-04-25 14:40:32', NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (23, '分配角色', 6, '用户管理', 'sys:user:assign', '', '', '', 2, 'el-icon-setting', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (24, '分配权限', 10, '角色管理', 'sys:role:assign', '', '', '', 2, 'el-icon-setting', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (25, '查询', 2, '部门管理', 'sys:department:select', '', '', '', 2, 'el-icon-search', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (26, '查询', 6, '用户管理', 'sys:user:select', '', '', '', 2, 'el-icon-search', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (27, '查询', 10, '角色管理', 'sys:role:select', '', '', '', 2, 'el-icon-search', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (28, '查询', 14, '菜单管理', 'sys:menu:select', '', '', '', 2, 'el-icon-search', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (29, '订单管理', 18, '资料管理', 'resource:order', '/resource/order', 'OrderList', '/resource/order/orderList', 1, 'el-icon-setting', NULL, NULL, NULL, 2, 0);
INSERT INTO `sys_permission` VALUES (31, '添加', 29, '订单管理', 'aa', '', '', '', 2, 'el-icon-plus', '2022-08-30 19:24:20', '2022-08-30 19:32:13', NULL, 1, 0);
INSERT INTO `sys_permission` VALUES (32, '系统工具', 0, '顶级菜单', 'sys:tool', '/tool', 'tool', '/system/tool', 1, 'el-icon-suitcase-1', '2022-09-01 10:15:55', '2022-09-01 10:17:35', NULL, 4, 0);
INSERT INTO `sys_permission` VALUES (33, '操作日志', 32, '系统工具', 'sys:log', '/log', 'log', '/system/tool/log', 1, 'el-icon-edit', '2022-09-01 10:22:58', '2022-09-01 10:23:15', NULL, 4, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint NULL DEFAULT 0 COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ROLE_SYSTEM', '超级管理员', 1, '2022-04-25 14:44:23', '2022-04-25 14:44:23', '拥有超级权限', 0);
INSERT INTO `sys_role` VALUES (2, 'ROLE_SYSTEM', '系统管理员', 1, '2022-04-25 14:44:23', '2022-04-25 14:44:23', '拥有系统管理功能模块的权限', 0);
INSERT INTO `sys_role` VALUES (3, 'ROLE_RESOURCE', '资料管理员', 4, NULL, NULL, '拥有资料管理模块的功能权限', 0);
INSERT INTO `sys_role` VALUES (4, 'ROLE_TEST', '测试', 4, '2022-08-30 20:43:34', '2022-08-31 10:38:59', '测试', 0);
INSERT INTO `sys_role` VALUES (5, 'ROLE_TEST1', '测试1', NULL, '2022-09-01 14:56:05', '2022-09-01 14:56:05', '测试1', 0);
INSERT INTO `sys_role` VALUES (6, 'ROLE_TEST2', '测试2', NULL, '2022-09-01 14:56:24', '2022-09-01 14:56:24', '测试2', 0);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `role_Id` bigint NOT NULL COMMENT '角色ID',
  `permission_Id` bigint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (29, 2, 1);
INSERT INTO `sys_role_permission` VALUES (30, 2, 2);
INSERT INTO `sys_role_permission` VALUES (31, 2, 3);
INSERT INTO `sys_role_permission` VALUES (32, 2, 4);
INSERT INTO `sys_role_permission` VALUES (33, 2, 5);
INSERT INTO `sys_role_permission` VALUES (34, 2, 25);
INSERT INTO `sys_role_permission` VALUES (35, 2, 6);
INSERT INTO `sys_role_permission` VALUES (36, 2, 7);
INSERT INTO `sys_role_permission` VALUES (37, 2, 8);
INSERT INTO `sys_role_permission` VALUES (38, 2, 9);
INSERT INTO `sys_role_permission` VALUES (39, 2, 23);
INSERT INTO `sys_role_permission` VALUES (40, 2, 26);
INSERT INTO `sys_role_permission` VALUES (41, 2, 10);
INSERT INTO `sys_role_permission` VALUES (42, 2, 11);
INSERT INTO `sys_role_permission` VALUES (43, 2, 12);
INSERT INTO `sys_role_permission` VALUES (44, 2, 13);
INSERT INTO `sys_role_permission` VALUES (45, 2, 24);
INSERT INTO `sys_role_permission` VALUES (46, 2, 27);
INSERT INTO `sys_role_permission` VALUES (47, 2, 14);
INSERT INTO `sys_role_permission` VALUES (48, 2, 15);
INSERT INTO `sys_role_permission` VALUES (49, 2, 16);
INSERT INTO `sys_role_permission` VALUES (50, 2, 17);
INSERT INTO `sys_role_permission` VALUES (51, 2, 28);
INSERT INTO `sys_role_permission` VALUES (52, 3, 18);
INSERT INTO `sys_role_permission` VALUES (53, 3, 19);
INSERT INTO `sys_role_permission` VALUES (54, 3, 20);
INSERT INTO `sys_role_permission` VALUES (55, 3, 21);
INSERT INTO `sys_role_permission` VALUES (56, 3, 22);
INSERT INTO `sys_role_permission` VALUES (57, 3, 29);
INSERT INTO `sys_role_permission` VALUES (71, 4, 14);
INSERT INTO `sys_role_permission` VALUES (72, 4, 15);
INSERT INTO `sys_role_permission` VALUES (73, 4, 16);
INSERT INTO `sys_role_permission` VALUES (74, 4, 17);
INSERT INTO `sys_role_permission` VALUES (75, 4, 28);
INSERT INTO `sys_role_permission` VALUES (76, 4, 1);
INSERT INTO `sys_role_permission` VALUES (77, 1, 1);
INSERT INTO `sys_role_permission` VALUES (78, 1, 2);
INSERT INTO `sys_role_permission` VALUES (79, 1, 3);
INSERT INTO `sys_role_permission` VALUES (80, 1, 4);
INSERT INTO `sys_role_permission` VALUES (81, 1, 5);
INSERT INTO `sys_role_permission` VALUES (82, 1, 25);
INSERT INTO `sys_role_permission` VALUES (83, 1, 6);
INSERT INTO `sys_role_permission` VALUES (84, 1, 7);
INSERT INTO `sys_role_permission` VALUES (85, 1, 8);
INSERT INTO `sys_role_permission` VALUES (86, 1, 9);
INSERT INTO `sys_role_permission` VALUES (87, 1, 23);
INSERT INTO `sys_role_permission` VALUES (88, 1, 26);
INSERT INTO `sys_role_permission` VALUES (89, 1, 10);
INSERT INTO `sys_role_permission` VALUES (90, 1, 11);
INSERT INTO `sys_role_permission` VALUES (91, 1, 12);
INSERT INTO `sys_role_permission` VALUES (92, 1, 13);
INSERT INTO `sys_role_permission` VALUES (93, 1, 24);
INSERT INTO `sys_role_permission` VALUES (94, 1, 27);
INSERT INTO `sys_role_permission` VALUES (95, 1, 14);
INSERT INTO `sys_role_permission` VALUES (96, 1, 15);
INSERT INTO `sys_role_permission` VALUES (97, 1, 16);
INSERT INTO `sys_role_permission` VALUES (98, 1, 17);
INSERT INTO `sys_role_permission` VALUES (99, 1, 28);
INSERT INTO `sys_role_permission` VALUES (100, 1, 19);
INSERT INTO `sys_role_permission` VALUES (101, 1, 20);
INSERT INTO `sys_role_permission` VALUES (102, 1, 21);
INSERT INTO `sys_role_permission` VALUES (103, 1, 22);
INSERT INTO `sys_role_permission` VALUES (104, 1, 32);
INSERT INTO `sys_role_permission` VALUES (105, 1, 33);
INSERT INTO `sys_role_permission` VALUES (106, 1, 18);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名称(用户名)',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `is_account_non_expired` tinyint NOT NULL COMMENT '帐户是否过期(1-未过期，0-已过期)',
  `is_account_non_locked` tinyint NOT NULL COMMENT '帐户是否被锁定(1-未过期，0-已过期)',
  `is_credentials_non_expired` tinyint NOT NULL COMMENT '密码是否过期(1-未过期，0-已过期)',
  `is_enabled` tinyint NOT NULL COMMENT '帐户是否可用(1-可用，0-禁用)',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `department_id` bigint NULL DEFAULT NULL COMMENT '所属部门ID',
  `department_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  `gender` tinyint NOT NULL COMMENT '性别(0-男，1-女)',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/default-avatar.gif' COMMENT '用户头像',
  `is_admin` tinyint NULL DEFAULT 0 COMMENT '是否是管理员(1-管理员)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint NULL DEFAULT 0 COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$TdEVQtGCkpo8L.jKjFB3/uxV5xkkDfiy0zoCa.ZS2yAXHe7H95OIC', 1, 1, 1, 1, '李明', '超级管理员', 1, '广州码农信息技术有限公司', 0, '13242587415', 'liming@163.com', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 1, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (2, 'liming', '$2a$10$TdEVQtGCkpo8L.jKjFB3/uxV5xkkDfiy0zoCa.ZS2yAXHe7H95OIC', 1, 1, 1, 1, '黎明', '黎明', 2, '软件技术部', 0, '13262365412578', '', 'https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/default-avatar.gif', 0, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (3, 'zhangsan', '$2a$10$TdEVQtGCkpo8L.jKjFB3/uxV5xkkDfiy0zoCa.ZS2yAXHe7H95OIC', 1, 1, 1, 1, '张三', '张三', 2, '软件技术部', 0, '13245678965', 'zhangsan@163.com', 'https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/bfa834a4c9424461a1ea0cbf8d4c9105-5acd2ed959790ec52b2825cbbc11b72d.jpeg', 0, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (4, 'lisi', '$2a$10$TdEVQtGCkpo8L.jKjFB3/uxV5xkkDfiy0zoCa.ZS2yAXHe7H95OIC', 1, 1, 1, 1, '李四', '李四', 2, '软件技术部', 0, '13754214568', '', 'https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/8868a2bfb4364e0697f7c3d28f3d889a-5acd2ed959790ec52b2825cbbc11b72d.jpeg', 0, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (5, 'wangwu', '$2a$10$TdEVQtGCkpo8L.jKjFB3/uxV5xkkDfiy0zoCa.ZS2yAXHe7H95OIC', 1, 1, 1, 1, '王五', '王五', 2, '软件技术部', 0, '13212345678', '', 'https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/fe664c1e45bb4e39a719cd3f6d95232a-male.jpg', 0, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (6, 'zhaoliu', '$2a$10$r45wkEYLHlteEr0KLI8y3.G506ylhQrEJkmGM.i2eHkcCnFvfbhCS', 1, 1, 1, 1, '赵六', '赵六', 2, '软件技术部', 0, '13212345676', '', 'https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/8868a2bfb4364e0697f7c3d28f3d889a-5acd2ed959790ec52b2825cbbc11b72d.jpeg', 0, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (7, 'test', '$2a$10$bkERi9PX.yLfyLX7MT7hpe4rTjE3LdOtNOTRwUCBIAL3RshKu73cG', 1, 1, 1, 1, '测试', 'test', 1, '广州码农信息技术有限公司', 0, '15871554841', 'test', 'https://ljzend.oss-cn-hangzhou.aliyuncs.com/avatar/2022/08/31/82cbe5701ae5455bb8220ada9c54d500.jpg', 0, '2022-08-31 14:37:55', '2022-08-31 16:13:08', 0);
INSERT INTO `sys_user` VALUES (8, 'lis', '$2a$10$Qiz4G5VNLsZsHryTcO4sgujtUOWDYFVoZMsPRcoR4FAQafwrul9de', 1, 1, 1, 1, 'lis', '', 2, '软件技术部', 0, '15874268721', '', '', 0, '2022-09-01 14:07:05', '2022-09-01 14:07:05', 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `role_id` bigint NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
INSERT INTO `sys_user_role` VALUES (3, 4, 2);
INSERT INTO `sys_user_role` VALUES (4, 6, 3);
INSERT INTO `sys_user_role` VALUES (5, 5, 3);
INSERT INTO `sys_user_role` VALUES (6, 7, 4);

SET FOREIGN_KEY_CHECKS = 1;
