/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.182
 Source Server Type    : MySQL
 Source Server Version : 50521
 Source Host           : 192.168.0.182:3306
 Source Schema         : generator

 Target Server Type    : MySQL
 Target Server Version : 50521
 File Encoding         : 65001

 Date: 20/09/2018 11:57:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `param_key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统配置信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '人人开源集团', 0, 0);
INSERT INTO `sys_dept` VALUES (2, 1, '长沙分公司', 1, 0);
INSERT INTO `sys_dept` VALUES (3, 1, '上海分公司', 2, 0);
INSERT INTO `sys_dept` VALUES (4, 3, '技术部', 0, 0);
INSERT INTO `sys_dept` VALUES (5, 3, '销售部', 1, 0);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典码',
  `value` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT 0 COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT 0 COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type`(`type`, `code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '性别', 'sex', '0', '女', 0, NULL, -1);
INSERT INTO `sys_dict` VALUES (2, '性别', 'sex', '1', '男', 1, NULL, 0);
INSERT INTO `sys_dict` VALUES (3, '性别', 'sex', '2', '未知', 3, NULL, 0);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `original_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原始名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件路径',
  `content_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件类型',
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件大小',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `crt_user_id` bigint(11) DEFAULT NULL COMMENT '创建者',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (1, '1.png', '/static/img201809201010279161.png', 'image/png', '121', '测试123', 1, '2018-09-20 10:10:42');
INSERT INTO `sys_file` VALUES (2, '1.png', '/static/img/201809201117447831.png', 'image/png', '121 bytes', '1', 1, '2018-09-20 11:19:04');
INSERT INTO `sys_file` VALUES (5, '1.png', '/statics/img/201809201140493291.png', 'image/png', '121 bytes', NULL, 1, '2018-09-20 11:40:52');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'admin', '删除菜单', 'com.swt.modules.sys.controller.SysMenuController.delete()', '51', 4, '127.0.0.1', '2018-09-05 11:13:27');
INSERT INTO `sys_log` VALUES (2, 'admin', '删除菜单', 'com.swt.modules.sys.controller.SysMenuController.delete()', '52', 20, '127.0.0.1', '2018-09-05 11:13:36');
INSERT INTO `sys_log` VALUES (3, 'admin', '删除菜单', 'com.swt.modules.sys.controller.SysMenuController.delete()', '53', 18, '127.0.0.1', '2018-09-05 11:13:48');
INSERT INTO `sys_log` VALUES (4, 'admin', '删除菜单', 'com.swt.modules.sys.controller.SysMenuController.delete()', '54', 12, '127.0.0.1', '2018-09-05 11:13:57');
INSERT INTO `sys_log` VALUES (5, 'admin', '修改数据源', 'com.swt.modules.data.controller.TopResourceConnectionController.update()', '1', 115, '127.0.0.1', '2018-09-05 18:36:13');
INSERT INTO `sys_log` VALUES (6, 'admin', '修改数据源', 'com.swt.modules.data.controller.TopResourceConnectionController.update()', '1', 10, '127.0.0.1', '2018-09-05 18:36:29');
INSERT INTO `sys_log` VALUES (7, 'admin', '新增数据源', 'com.swt.modules.data.controller.TopResourceConnectionController.save()', '{\"connectionId\":3,\"name\":\"Bleeth\",\"type\":\"MYSQL\",\"database\":\"swt_top\",\"serverIp\":\"192.168.1.111\",\"port\":\"3306\",\"username\":\"root\",\"password\":\"123456\",\"source\":\"小伟123\"}', 12, '127.0.0.1', '2018-09-05 18:37:08');
INSERT INTO `sys_log` VALUES (8, 'admin', '删除数据源', 'com.swt.modules.data.controller.TopResourceConnectionController.delete()', '[3]', 30, '0:0:0:0:0:0:0:1', '2018-09-05 18:41:30');
INSERT INTO `sys_log` VALUES (9, 'admin', '修改数据源', 'com.swt.modules.data.controller.ResourceConnectionController.update()', '1', 99, '0:0:0:0:0:0:0:1', '2018-09-10 16:10:43');
INSERT INTO `sys_log` VALUES (10, 'admin', '新增数据源', 'com.swt.modules.data.controller.ResourceConnectionController.save()', '{\"connectionId\":2,\"name\":\"Bleeth\",\"type\":\"MYSQL\",\"database\":\"swt_top\",\"serverIp\":\"192.168.1.111\",\"port\":\"3306\",\"username\":\"root\",\"password\":\"root\",\"source\":\"小伟1111\",\"crtUserId\":1,\"crtTime\":\"Sep 10, 2018 5:17:04 PM\",\"active\":false}', 91, '0:0:0:0:0:0:0:1', '2018-09-10 17:17:04');
INSERT INTO `sys_log` VALUES (11, 'admin', '修改数据源', 'com.swt.modules.data.controller.ResourceConnectionController.update()', '2', 69, '0:0:0:0:0:0:0:1', '2018-09-10 17:58:20');
INSERT INTO `sys_log` VALUES (12, 'admin', '新增化学品分类', 'com.swt.modules.data.controller.ChemicalsClassController.save()', '{\"chemicalClassId\":83,\"chemicalClassName\":\"试试\",\"crtUserId\":1,\"crtTime\":\"Sep 19, 2018 4:05:13 PM\"}', 122, '0:0:0:0:0:0:0:1', '2018-09-19 16:05:13');
INSERT INTO `sys_log` VALUES (13, 'admin', '新增静态资源', 'com.swt.modules.sys.controller.SysFileController.save()', '{\"id\":1,\"originalName\":\"1.png\",\"path\":\"/static/img201809201010279161.png\",\"contentType\":\"image/png\",\"size\":121,\"description\":\"测试\",\"crtUserId\":1,\"crtTime\":\"Sep 20, 2018 10:10:42 AM\"}', 96, '0:0:0:0:0:0:0:1', '2018-09-20 10:10:42');
INSERT INTO `sys_log` VALUES (14, 'admin', '修改静态资源', 'com.swt.modules.sys.controller.SysFileController.update()', '1', 7, '0:0:0:0:0:0:0:1', '2018-09-20 10:11:51');
INSERT INTO `sys_log` VALUES (15, 'admin', '新增静态资源', 'com.swt.modules.sys.controller.SysFileController.save()', '{\"id\":2,\"originalName\":\"1.png\",\"path\":\"/static/img/201809201117447831.png\",\"contentType\":\"image/png\",\"size\":\"121 bytes\",\"description\":\"1\",\"crtUserId\":1,\"crtTime\":\"Sep 20, 2018 11:19:04 AM\"}', 21, '0:0:0:0:0:0:0:1', '2018-09-20 11:19:04');
INSERT INTO `sys_log` VALUES (16, 'admin', '新增静态资源', 'com.swt.modules.sys.controller.SysFileController.save()', '{\"id\":3,\"originalName\":\"1.png\",\"path\":\"/statics/img/201809201128084821.png\",\"contentType\":\"image/png\",\"size\":\"121 bytes\",\"description\":\"234\",\"crtUserId\":1,\"crtTime\":\"Sep 20, 2018 11:28:11 AM\"}', 195, '0:0:0:0:0:0:0:1', '2018-09-20 11:28:12');
INSERT INTO `sys_log` VALUES (17, 'admin', '删除静态资源', 'com.swt.modules.sys.controller.SysFileController.delete()', '[3]', 17, '0:0:0:0:0:0:0:1', '2018-09-20 11:36:10');
INSERT INTO `sys_log` VALUES (18, 'admin', '新增静态资源', 'com.swt.modules.sys.controller.SysFileController.save()', '{\"id\":4,\"originalName\":\"1.png\",\"path\":\"/statics/img/201809201140177151.png\",\"contentType\":\"image/png\",\"size\":\"121 bytes\",\"description\":\"123\",\"crtUserId\":1,\"crtTime\":\"Sep 20, 2018 11:40:22 AM\"}', 185, '0:0:0:0:0:0:0:1', '2018-09-20 11:40:22');
INSERT INTO `sys_log` VALUES (19, 'admin', '删除静态资源', 'com.swt.modules.sys.controller.SysFileController.delete()', '[4]', 26, '0:0:0:0:0:0:0:1', '2018-09-20 11:40:30');
INSERT INTO `sys_log` VALUES (20, 'admin', '新增静态资源', 'com.swt.modules.sys.controller.SysFileController.save()', '{\"id\":5,\"originalName\":\"1.png\",\"path\":\"/statics/img/201809201140493291.png\",\"contentType\":\"image/png\",\"size\":\"121 bytes\",\"crtUserId\":1,\"crtTime\":\"Sep 20, 2018 11:40:52 AM\"}', 29, '0:0:0:0:0:0:0:1', '2018-09-20 11:40:52');
INSERT INTO `sys_log` VALUES (21, 'admin', '修改静态资源', 'com.swt.modules.sys.controller.SysFileController.update()', '2', 60, '0:0:0:0:0:0:0:1', '2018-09-20 11:56:38');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 212 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'fa fa-cog', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员管理', 'modules/sys/user.html', NULL, 1, 'fa fa-user', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'modules/sys/role.html', NULL, 1, 'fa fa-user-secret', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'modules/sys/menu.html', NULL, 1, 'fa fa-th-list', 3);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:perms', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:perms', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'fa fa-sun-o', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', 'modules/sys/log.html', 'sys:log:list', 1, 'fa fa-file-text-o', 7);
INSERT INTO `sys_menu` VALUES (31, 1, '部门管理', 'modules/sys/dept.html', NULL, 1, 'fa fa-file-code-o', 1);
INSERT INTO `sys_menu` VALUES (32, 31, '查看', NULL, 'sys:dept:list,sys:dept:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (33, 31, '新增', NULL, 'sys:dept:save,sys:dept:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (34, 31, '修改', NULL, 'sys:dept:update,sys:dept:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (35, 31, '删除', NULL, 'sys:dept:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (36, 1, '字典管理', 'modules/sys/dict.html', NULL, 1, 'fa fa-bookmark-o', 6);
INSERT INTO `sys_menu` VALUES (37, 36, '查看', NULL, 'sys:dict:list,sys:dict:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (38, 36, '新增', NULL, 'sys:dict:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (39, 36, '修改', NULL, 'sys:dict:update', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (40, 36, '删除', NULL, 'sys:dict:delete', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (207, 1, '静态资源管理', 'modules/sys/sysfile.html', NULL, 1, 'fa fa-file-code-o', 6);
INSERT INTO `sys_menu` VALUES (208, 207, '查看', NULL, 'sys:sysfile:list,sys:sysfile:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (209, 207, '新增', NULL, 'sys:sysfile:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (210, 207, '修改', NULL, 'sys:sysfile:update', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (211, 207, '删除', NULL, 'sys:sysfile:delete', 2, NULL, 6);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件上传' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与部门对应关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
