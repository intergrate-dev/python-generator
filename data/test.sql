/*
Navicat MySQL Data Transfer

Source Server         : conn-local
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-06-12 16:56:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ex_result
-- ----------------------------
DROP TABLE IF EXISTS `ex_result`;
CREATE TABLE `ex_result` (
  `taskID` varchar(20) NOT NULL COMMENT '任务定义id  ',
  `resourceID` varchar(50) DEFAULT NULL COMMENT '资源目录ID  ',
  `exTotal` char(2) DEFAULT NULL COMMENT '交换数据总量  ',
  `sucCnt` int(1) DEFAULT NULL COMMENT '交换成功的数据量  ',
  `exState` text COMMENT '交换状态   0：交换失败，1：交换成功',
  PRIMARY KEY (`taskID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ex_result
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `file_name` varchar(255) NOT NULL,
  `MD5` varchar(255) DEFAULT NULL,
  `upload_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('12');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` text,
  `identity` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('001', 'james');

-- ----------------------------
-- Table structure for task_exe_record
-- ----------------------------
DROP TABLE IF EXISTS `task_exe_record`;
CREATE TABLE `task_exe_record` (
  `taskDefId` varchar(600) NOT NULL COMMENT '任务定义id  单作业：同任务编号17位时间戳+R+6位顺序号 作业流：作业组编号（14位时间戳+F+6位顺序号）+3位顺序编号',
  `resourceID` varchar(50) DEFAULT NULL COMMENT '资源目录ID  ',
  `executeWay` char(2) DEFAULT NULL COMMENT '执行方式  0：一次性，1：周期性',
  `jobState` int(1) DEFAULT NULL COMMENT '作业运行状态  待执行，1-执行中，2-执行成功，3-执行失败，4-取消',
  `extInfo` text COMMENT '扩展信息   包括连接节点、数据表、合约信息',
  PRIMARY KEY (`taskDefId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_exe_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `goods_name` varchar(40) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('1', 'yu123', 'iphoneX');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(30) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `password` varchar(30) CHARACTER SET utf8mb4 NOT NULL COMMENT '密码',
  `description` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '00000000000000');
INSERT INTO `user` VALUES ('2', 'yyyy', 'yyyy', '222222222');
INSERT INTO `user` VALUES ('3', 'mmm', '123', '44444');
INSERT INTO `user` VALUES ('4', 'www', '888', '44444');
INSERT INTO `user` VALUES ('5', 'hhh', '666', '99999');
INSERT INTO `user` VALUES ('6', 'sss', '111', '33333');
