/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : platform

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 15/12/2022 00:54:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aaainfo
-- ----------------------------
DROP TABLE IF EXISTS `aaainfo`;
CREATE TABLE `aaainfo`  (
  `id` int NOT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aaainfo
-- ----------------------------
INSERT INTO `aaainfo` VALUES (1, 'Chu Ming Sze', '5qJFf91oms');
INSERT INTO `aaainfo` VALUES (2, 'Choi Wai Man', 'HJvhhMPf8N');
INSERT INTO `aaainfo` VALUES (3, 'Manuel Silva', '60ikPOb2cL');
INSERT INTO `aaainfo` VALUES (4, 'Hirano Daichi', 'pf7307QhaC');
INSERT INTO `aaainfo` VALUES (5, 'Okada Sakura', 'nlegiCwVmM');

-- ----------------------------
-- Table structure for accesslog
-- ----------------------------
DROP TABLE IF EXISTS `accesslog`;
CREATE TABLE `accesslog`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `operate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `operatetime` datetime NULL DEFAULT NULL,
  `spare` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of accesslog
-- ----------------------------
INSERT INTO `accesslog` VALUES ('1', 'Nishimura Nanami', 'nanamnishimura625', 'bMuR3rN1Zq', '2012-01-29 05:17:36', 'bGtUECjVUF');
INSERT INTO `accesslog` VALUES ('10', 'Chow Wai Han', 'cwh69', 'GdloeSkUaA', '2014-06-24 07:17:26', 'BL4IP1m8Oq');
INSERT INTO `accesslog` VALUES ('2', 'Kikuchi Aoi', 'aoikikuchi909', 'fWlhm09kcM', '2000-03-09 05:46:49', 'rCX1JJs8Z8');
INSERT INTO `accesslog` VALUES ('3', 'Cynthia Gardner', 'cynthiag221', '0Y5MbW5N18', '2003-02-16 11:22:48', '71k8jXBVpr');
INSERT INTO `accesslog` VALUES ('4', 'Zeng Yuning', 'yuning125', 'oKaJ7PAfnF', '2022-08-06 02:34:52', 'qvSs2BidKz');
INSERT INTO `accesslog` VALUES ('4da1437a-2456-41f3-96cf-92c269a203b4', 'Du Rui', NULL, '用户登录', '2022-12-15 00:11:13', NULL);
INSERT INTO `accesslog` VALUES ('5', 'Kondo Sara', 'sarakond1217', 'qHq0MAYovI', '2010-04-04 12:40:46', 'nDmlHrvzDq');
INSERT INTO `accesslog` VALUES ('516fb99b-314f-43a1-a20a-dfd50a401858', 'admin', NULL, '用户登录', '2022-12-14 23:56:08', NULL);
INSERT INTO `accesslog` VALUES ('532a78cc-0a4e-4610-a9e2-ca723c02835c', 'admin', NULL, '用户登录', '2022-12-14 23:54:32', NULL);
INSERT INTO `accesslog` VALUES ('6', 'Long Yunxi', 'yunlong', 'aTgkpJJBfW', '2019-01-04 06:38:20', '1QAfRFqLE4');
INSERT INTO `accesslog` VALUES ('7', 'Chiang Siu Wai', 'siuwai208', 'LjbgBeRjRZ', '2016-11-21 15:31:10', 'lMIFsryzQt');
INSERT INTO `accesslog` VALUES ('70a02ddf-35a9-4bf6-a9f8-259b4e823b52', 'dodo', NULL, '用户登录', '2022-12-14 23:07:52', NULL);
INSERT INTO `accesslog` VALUES ('73beaeb5-dce1-4d99-be44-b302f8569ac2', 'dodo', NULL, '用户登录', '2022-12-14 20:51:03', NULL);
INSERT INTO `accesslog` VALUES ('8', 'Yamada Hina', 'yh909', '8ukAzhoHif', '2002-11-05 14:49:56', 'XX1p8k9ZFd');
INSERT INTO `accesslog` VALUES ('9', 'Kwok Hok Yau', 'hykwok', 'BcS08Y3qOG', '2006-10-15 09:26:17', 's35YkQqim2');
INSERT INTO `accesslog` VALUES ('d012a0f2-80ad-4725-8414-b73430a38779', 'dodo', NULL, '用户登录', '2022-12-14 22:30:13', NULL);

-- ----------------------------
-- Table structure for bbbinfo
-- ----------------------------
DROP TABLE IF EXISTS `bbbinfo`;
CREATE TABLE `bbbinfo`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `useless_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `Email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `备注` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bbbinfo
-- ----------------------------
INSERT INTO `bbbinfo` VALUES (1, '张三', '垃圾信息', 'takagi6@yahoo.com', 'takagihikaru@outlook.com', '医生', '汉字', '这是备注');
INSERT INTO `bbbinfo` VALUES (2, '李四', 'sada', 'hito6@outlook.com', 'hikaru17@gmail.com', '律师', '测试', '12345');
INSERT INTO `bbbinfo` VALUES (3, '小明', 'Carol Robinson', 'carol1106@outlook.com', 'carol9@outlook.com', '演员', '脱敏', 'ZASf55lSDY');
INSERT INTO `bbbinfo` VALUES (4, 'Nomura Kasumi', 'Nomura Kasumi', 'kno@outlook.com', 'kasumi920@outlook.com', 'p7lC0krZt0', '正常', '6wDLdtm2Wa');
INSERT INTO `bbbinfo` VALUES (5, 'Pang Lik Sun', 'Pang Lik Sun', 'pangliksun@gmail.com', 'pangliksun2@icloud.com', 'bQrgsynvn2', '1235', '3AZ8abL0i3');
INSERT INTO `bbbinfo` VALUES (6, 'Sano Minato', 'Sano Minato', 'sanominat@yahoo.com', 'sano1@hotmail.com', '0czuBEGnAb', 'fiEWmhrGWf', 'ycxU6zu35D');
INSERT INTO `bbbinfo` VALUES (7, 'Hu Zhennan', 'Hu Zhennan', 'zhennan5@icloud.com', 'zhu@mail.com', '3KfenGI2vq', 'a9EAumS3Oa', 'PbYqnioQ0w');
INSERT INTO `bbbinfo` VALUES (8, 'Tao Ziyi', 'Tao Ziyi', 'ziyi6@icloud.com', 'ziyit@hotmail.com', 'ENPxXdfRy3', '7voBmQWqkr', 'CCacZcyBty');
INSERT INTO `bbbinfo` VALUES (9, 'Evelyn Webb', 'Evelyn Webb', 'evelwebb@mail.com', 'evelyn520@mail.com', 'SiS1uouMh0', 'XwQ53J5i8B', '0ZY6X5UM9y');
INSERT INTO `bbbinfo` VALUES (10, 'Chan Siu Wai', 'Chan Siu Wai', 'chansiuwai@gmail.com', 'chansiu@yahoo.com', 'gljZlX7Rnd', 're9hwrEzSL', 'zJu3GEo3wX');

-- ----------------------------
-- Table structure for cleansing_task
-- ----------------------------
DROP TABLE IF EXISTS `cleansing_task`;
CREATE TABLE `cleansing_task`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `input_table` int NOT NULL COMMENT '格式：schema_table',
  `input_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `output_table` int NOT NULL COMMENT '格式：schema_table',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `complete_time` datetime NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL COMMENT '1 - 正在运行\n2 - 暂停',
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `config_location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应的配置文件存放位置',
  `logLocation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'nohup启动seatunnel作业后，日志的输出位置',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cleansing_task_app_id_uindex`(`app_id` ASC) USING BTREE,
  UNIQUE INDEX `cleansing_task_id_uindex`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cleansing_task
-- ----------------------------
INSERT INTO `cleansing_task` VALUES (1, 'local-1668968877988', 1, 'MySQL', -1, '2022-11-21 02:27:58', NULL, 2, NULL, '/usr/local/seatunnel/config/2022-11-21T02:27:57.988', '');
INSERT INTO `cleansing_task` VALUES (2, 'local-1669029038204', 2, 'MySQL', -1, '2022-11-21 19:10:38', NULL, 2, NULL, '/usr/local/seatunnel/config/2022-11-21T19:10:38.204', '');

-- ----------------------------
-- Table structure for data_source
-- ----------------------------
DROP TABLE IF EXISTS `data_source`;
CREATE TABLE `data_source`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据源描述',
  `create_time` datetime NOT NULL,
  `last_modified_time` datetime NOT NULL,
  `source_type` int NOT NULL COMMENT '数据源类型',
  `source_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `connect_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_source
-- ----------------------------
INSERT INTO `data_source` VALUES ('5e5e3f35-6d69-4796-977f-7b25df3b6587', 'test mysql data source inserted modified', '2022-10-03 11:10:11', '2022-10-03 15:55:33', 1, 'test mysql inserted modified', '{\"port\":\"3306\",\"host\":\"localhost\",\"schema\":\"wutong\",\"table\":\"freight_source\",\"password\":\"zlx1754wanc\",\"user\":\"root\"}');
INSERT INTO `data_source` VALUES ('a1s2d3f4', 'test mysql data source 1', '2022-09-30 11:10:11', '2022-09-30 11:10:12', 1, 'test mysql 1', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('a1s2d3f5', 'test mysql data source 2', '2022-09-30 11:11:11', '2022-09-30 11:11:12', 1, 'test mysql 2', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('a1s2d3f6', 'test mysql data source 3', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 3', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('a1s2d3f7', 'test mysql data source 4', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 4', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('adsfzxv', 'test mysql data source 22', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 22', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('ae', 'test mysql data source 13', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 13', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('asdf', 'test mysql data source 7', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 7', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('dfgh', 'test mysql data source 11', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 11', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('dsf', 'test mysql data source 6', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 6', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('ertyu', 'test mysql data source 20', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 20', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('fghj', 'test mysql data source 12', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 12', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('fsdgt', 'test mysql data source 24', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 24', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('fsgzx', 'test mysql data source 28', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 28', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('gfhj', 'test mysql data source 8', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 8', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('gsfdgt', 'test mysql data source 29', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 29', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('gthsd', 'test mysql data source 27', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 27', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('hg', 'test mysql data source 10', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 10', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('hgfj', 'test mysql data source 9', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 9', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('rytu', 'test mysql data source 17', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 17', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('sad', 'test mysql data source 5', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 5', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('sdf', 'test mysql data source 19', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 19', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('sdfgfx', 'test mysql data source 26', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 26', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('sdfgsh', 'test mysql data source 21', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 21', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('sdfgt', 'test mysql data source 25', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 25', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('sdfgzxc', 'test mysql data source 23', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 23', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('sdgf', 'test mysql data source 14', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 14', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('try', 'test mysql data source 30', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 30', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('werty', 'test mysql data source 15', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 15', '{\"host\":\"localhost\",\"port\":\"3306\"}');
INSERT INTO `data_source` VALUES ('ytery', 'test mysql data source 16', '2022-09-30 11:12:11', '2022-09-30 11:12:12', 1, 'test mysql 16', '{\"host\":\"localhost\",\"port\":\"3306\"}');

-- ----------------------------
-- Table structure for mask
-- ----------------------------
DROP TABLE IF EXISTS `mask`;
CREATE TABLE `mask`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `schema` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `table` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `maskway` int NULL DEFAULT NULL,
  `savetype` int NULL DEFAULT NULL,
  `maskwaytext` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `savetypetext` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createtime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mask
-- ----------------------------
INSERT INTO `mask` VALUES ('03de384a-bb21-4144-aa69-f552a33e2760', '1', '2', '', '', '', '', 'platform', 'bbbinfo', 2, 1, '哈希CRC32', '.xlsx', '2022-12-13 22:55:27');
INSERT INTO `mask` VALUES ('315533c5-91cd-4312-ae80-e31986f2aa79', '掩盖脱敏', '测试', '', '', '', '', 'platform', 'bbbinfo', 4, 1, '遮盖', '.xlsx', '2022-12-13 22:56:57');
INSERT INTO `mask` VALUES ('397d51b8-9779-44bb-96b3-8420d803d190', 'q', '1', '1', '1', '1', '2', 'platform', 'bbbinfo', 3, 0, '哈希MD5', '.txt', '2022-12-14 16:13:56');
INSERT INTO `mask` VALUES ('425f1fe7-3454-4680-ac9d-0e92ca5995f7', '1233', '213', '13', '2', '2', '1', 'platform', 'bbbinfo', 4, 1, NULL, NULL, NULL);
INSERT INTO `mask` VALUES ('4e8dfedf-e2ef-4b0e-8a05-32b62cb199e4', '掩盖脱敏', '测试', '1', '2', '34', '4', 'platform', 'aaainfo', 4, 1, '遮盖', '.xlsx', '2022-12-14 14:17:29');
INSERT INTO `mask` VALUES ('982aa0af-bb35-4768-bc14-8f4cedd040ad', 'q', 'w', 'e', 's', 'd', 'd', 'platform', 'bbbinfo', 0, 1, NULL, NULL, NULL);
INSERT INTO `mask` VALUES ('9bbea286-bf27-491f-b4b5-8577e58a9c50', '掩盖脱敏', '测试', '1', '2', '34', '4', 'platform', 'bbbinfo', 4, 1, '遮盖', '.xlsx', '2022-12-14 14:03:30');
INSERT INTO `mask` VALUES ('a091f18c-31aa-421e-8c68-4b7f38b17fcb', '9999', '2223', '1', '1', '1', '1', 'platform', 'bbbinfo', 2, 1, '哈希CRC32', '.xlsx', '2022-12-14 22:25:21');
INSERT INTO `mask` VALUES ('ad4ebde4-22ae-40de-a7d8-9799b2a659f2', '=111', 'w', 'e', 's', 'd', 'd', 'platform', 'bbbinfo', 0, 1, NULL, NULL, NULL);
INSERT INTO `mask` VALUES ('b7c1e507-80ee-4a54-9e38-8a8cc2c2d697', '测试', '213', '13', '2', '2', '1', 'platform', 'bbbinfo', 4, 1, NULL, NULL, NULL);
INSERT INTO `mask` VALUES ('dfbafdf1-a11b-4f48-b0c8-9a167764f032', '12', '2133', '123', '12', '21', '21', 'platforn', 'bbbinfo', 3, 1, '哈希MD5', '.xlsx', '2022-12-14 16:00:07');

-- ----------------------------
-- Table structure for masking_task
-- ----------------------------
DROP TABLE IF EXISTS `masking_task`;
CREATE TABLE `masking_task`  (
  `task_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `task_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `task_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `task_des` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`task_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of masking_task
-- ----------------------------
INSERT INTO `masking_task` VALUES ('1', 'Chang Anqi', 'KEWbrO9A7R', 'N1x2pxnHyK', '2011-05-24 05:42:23');
INSERT INTO `masking_task` VALUES ('10', 'Lee Hicks', 'TAaVLwxeB0', 'g59VmcY0Z7', '2003-07-24 23:14:14');
INSERT INTO `masking_task` VALUES ('11', 'Qian Xiuying', 'wTJn2j2Aps', 'iCoGjMAC6r', '2015-11-23 21:20:38');
INSERT INTO `masking_task` VALUES ('12', 'Chan Lai Yan', 'DwxYUD06sD', 'JMIoLJMFw6', '2002-08-08 03:07:29');
INSERT INTO `masking_task` VALUES ('13', 'Xiang Zitao', 'vOZa14vV5p', 'j73Z6oKhkH', '2022-02-02 02:47:28');
INSERT INTO `masking_task` VALUES ('14', 'Ono Seiko', 'PVTM1314VS', 'cWea2prDlF', '2014-10-04 10:35:01');
INSERT INTO `masking_task` VALUES ('15', 'Ding Shihan', 'Sdmvp640lj', 'fAa2ZtUOFZ', '2021-06-08 21:10:11');
INSERT INTO `masking_task` VALUES ('16', 'Ti Kwok Wing', 'KZTtsHSAOH', 'I5Z61BCAQZ', '2020-02-22 02:59:06');
INSERT INTO `masking_task` VALUES ('17', 'Ishikawa Mio', 'nrZjkRfRwl', '8Vmb7ijOn9', '2007-03-20 20:04:45');
INSERT INTO `masking_task` VALUES ('18', 'Ethel Taylor', 'nGECQ1PBMg', 'Uxd7hSrL3T', '2002-06-07 02:13:02');
INSERT INTO `masking_task` VALUES ('19', 'Rita Hawkins', 'qtH5fSwHyH', '59HBOLIF76', '2011-09-18 23:15:52');
INSERT INTO `masking_task` VALUES ('2', 'Yung Ling Ling', 'EOH8EzVmev', '73to2T2RkZ', '2010-01-30 15:21:17');
INSERT INTO `masking_task` VALUES ('20', 'Jia Zitao', '0uA38jdbD4', 'w57MNobRne', '2021-09-12 22:42:06');
INSERT INTO `masking_task` VALUES ('3', 'Wong Chi Ming', 'C60if2vMqD', 'TpyGjRkgBL', '2010-06-15 00:26:36');
INSERT INTO `masking_task` VALUES ('4', 'Yan Jialun', 'iCirUGwM7j', '2ETxBKjyNd', '2014-06-21 05:55:50');
INSERT INTO `masking_task` VALUES ('5', 'Yu Xiaoming', 'kv9MY8HTkJ', 'S8H7mE8eWV', '2003-07-20 07:42:23');
INSERT INTO `masking_task` VALUES ('6', 'Andrea Jones', '55cqcSfc2U', 'rfIyHBKYYc', '2015-03-03 08:29:22');
INSERT INTO `masking_task` VALUES ('7', 'Lin Jiehong', 'JaCliU2fdc', 'ly4LeBeo8V', '2011-03-04 06:19:32');
INSERT INTO `masking_task` VALUES ('8', 'Wu Ka Ling', 'v2Q6c62p8x', '6lrK9iZPI1', '2010-04-17 14:12:45');
INSERT INTO `masking_task` VALUES ('9', 'Marvin Fernandez', 'dvYVYf9Hso', 'YWufamiNNg', '2022-05-15 22:53:23');

-- ----------------------------
-- Table structure for mysql_source
-- ----------------------------
DROP TABLE IF EXISTS `mysql_source`;
CREATE TABLE `mysql_source`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `port` int NOT NULL,
  `schema_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `mysql_source_id_uindex`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'MySQL外部数据源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mysql_source
-- ----------------------------
INSERT INTO `mysql_source` VALUES (2, '测试', '测试2', '2022-12-09 15:58:19', '127.0.0.1', 3306, 'logistics', 'root', 'zlx1754wanc');

-- ----------------------------
-- Table structure for mysql_task
-- ----------------------------
DROP TABLE IF EXISTS `mysql_task`;
CREATE TABLE `mysql_task`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `mode` int NOT NULL COMMENT '1 - 流\n2 - 批',
  `host` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `port` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `schema_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `target_table` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` int NULL DEFAULT NULL COMMENT '1 - 正在运行\n2 - 停止',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `mysql_task_id_uindex`(`id` ASC) USING BTREE,
  UNIQUE INDEX `mysql_task_name_uindex`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mysql_task
-- ----------------------------
INSERT INTO `mysql_task` VALUES (1, 'asf', 'sdfasd', '2022-11-20 23:16:19', 2, 'localhost', '3306', 'logistics', 'freight_source', 1, 'root', 'zlx1754wanc');
INSERT INTO `mysql_task` VALUES (2, '货源数据库', '货源数据库', '2022-11-21 10:59:27', 1, 'localhost', '3306', 'logistics', 'freight_source', 2, 'root', 'zlx1754wanc');
INSERT INTO `mysql_task` VALUES (3, '货源', '货源', '2022-11-21 19:07:12', 1, 'localhost', '3306', 'logistics', 'freight_source', 2, 'root', 'zlx1754wanc');

-- ----------------------------
-- Table structure for spider_source
-- ----------------------------
DROP TABLE IF EXISTS `spider_source`;
CREATE TABLE `spider_source`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `spider_source_id_uindex`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '爬虫数据源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spider_source
-- ----------------------------

-- ----------------------------
-- Table structure for spider_task
-- ----------------------------
DROP TABLE IF EXISTS `spider_task`;
CREATE TABLE `spider_task`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `modified_time` datetime NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sink` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` int NOT NULL COMMENT '1 - 正在运行\n2 - 已暂停',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `spider_task_name_uindex`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spider_task
-- ----------------------------
INSERT INTO `spider_task` VALUES (1, 'ss', 'ss', '2022-11-20 22:13:37', 'ss', 'kafka', 2);
INSERT INTO `spider_task` VALUES (3, 'as', 'ss', '2022-11-20 22:15:08', 'ss', 'kafka', 2);
INSERT INTO `spider_task` VALUES (4, 'asd', 'fasf', '2022-11-20 22:17:49', 'adsf', 'kafka', 2);
INSERT INTO `spider_task` VALUES (5, 'fsg', 'asd', '2022-11-20 22:20:24', 'sdfg', 'kafka', 2);
INSERT INTO `spider_task` VALUES (6, 'dsagf', 'adfgsg', '2022-11-20 22:22:13', 'sdfg', 'kafka', 2);
INSERT INTO `spider_task` VALUES (7, 'gsdfg', 'asdfsfg', '2022-11-20 22:33:44', 'dasg', 'kafka', 2);

-- ----------------------------
-- Table structure for testinfo
-- ----------------------------
DROP TABLE IF EXISTS `testinfo`;
CREATE TABLE `testinfo`  (
  `id` int NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testinfo
-- ----------------------------
INSERT INTO `testinfo` VALUES (1, 'Shannon Mendez', 'Shannon Mendez', 'sdgr8L1uRV');
INSERT INTO `testinfo` VALUES (2, 'Yuen Tak Wah', 'Yuen Tak Wah', 'G6i9GkeXKr');
INSERT INTO `testinfo` VALUES (3, 'Chung Ming', 'Chung Ming', 'WpuKdRvffb');
INSERT INTO `testinfo` VALUES (4, 'Tony Hicks', 'Tony Hicks', 'mMEbCxkDxx');
INSERT INTO `testinfo` VALUES (5, 'Kono Aoshi', 'Kono Aoshi', 'AqK82GNEBK');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'Du Rui', 'PcfFc27Ra2');
INSERT INTO `user_info` VALUES ('7e625465-759e-4ec6-8ce0-ad099a5332e7', 'admin', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
