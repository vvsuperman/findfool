/*
Navicat MySQL Data Transfer

Source Server         : zpl
Source Server Version : 50523
Source Host           : localhost:3306
Source Database       : oj

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2014-07-18 23:15:34
*/
drop database if exists ojsite;
create database ojsite character set utf8;
use ojsite;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `testuser_problem`
-- ----------------------------
CREATE TABLE ojsite.testuser_problem (tuid INT(10) NOT NULL, problemid INT(10) NOT NULL, PRIMARY KEY (tuid, problemid)) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT = 'testuser和problem的关联表'


-- ----------------------------
-- Table structure for `api`
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api` (
  `apiid` int(11) NOT NULL AUTO_INCREMENT,
  `api` text NOT NULL COMMENT '这个是有该权限的api',
  PRIMARY KEY (`apiid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='这个是api表\r\n';

-- ----------------------------
-- Records of api
-- ----------------------------
INSERT INTO `api` VALUES ('1', '/user/add/hr');
INSERT INTO `api` VALUES ('2', '/user/confirm');
INSERT INTO `api` VALUES ('3', '/contactus');
INSERT INTO `api` VALUES ('4', '/question/query');
INSERT INTO `api` VALUES ('5', '/search/site');
INSERT INTO `api` VALUES ('6', '/search/my');
INSERT INTO `api` VALUES ('7', '/search/user/common');
INSERT INTO `api` VALUES ('8', '/search/admin');
INSERT INTO `api` VALUES ('9', '/test/manage/submite');
INSERT INTO `api` VALUES ('10', '/question/add');
INSERT INTO `api` VALUES ('11', '/test/show');
INSERT INTO `api` VALUES ('12', '/test/manage');
INSERT INTO `api` VALUES ('13', '/test/manage/setting/set');
INSERT INTO `api` VALUES ('14', '/test/manage/invite');
INSERT INTO `api` VALUES ('15', '/user/setting/query');
INSERT INTO `api` VALUES ('16', '/user/setting/set');
INSERT INTO `api` VALUES ('17', '/set/manage');
INSERT INTO `api` VALUES ('18', '/user/add/admin');
INSERT INTO `api` VALUES ('19', '/test/add');
INSERT INTO `api` VALUES ('20', '/test/queryByID');
INSERT INTO `api` VALUES ('21', '/test/queryByName');
INSERT INTO `api` VALUES ('22', '/search/sets');
INSERT INTO `api` VALUES ('23', '/question/delete');

-- ----------------------------
-- Table structure for `contactus`
-- ----------------------------
DROP TABLE IF EXISTS `contactus`;
CREATE TABLE `contactus` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `email` text NOT NULL COMMENT '这个是留言者的email',
  `name` text COMMENT '留言者名字',
  `context` text COMMENT '内容',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='留言表';

-- ----------------------------
-- Records of contactus
-- ----------------------------
INSERT INTO `contactus` VALUES ('1', 'xxx', 'zpl', 'fuck fuck fuck ');

-- ----------------------------
-- Table structure for `invite`
-- ----------------------------
DROP TABLE IF EXISTS `invite`;
CREATE TABLE `invite` (
  `iid` int(11) NOT NULL AUTO_INCREMENT,
  `testid` int(11) DEFAULT NULL COMMENT '测试的id',
  `hrid` int(11) DEFAULT NULL COMMENT '测试的hr',
  `uid` int(11) DEFAULT NULL COMMENT '被邀请的用户id，如果该email第一次邀请，则系统自动注册',
  `inviteTime` datetime DEFAULT NULL COMMENT '邀请发出去的时间',
  `finishtime` datetime DEFAULT NULL COMMENT '用户完成的时间，如果是0表示没有完成',
  `score` text COMMENT '存放的形式为 20/50',
  PRIMARY KEY (`iid`),
  KEY `f_t_i` (`testid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='这个是邀请的表，里面存放着被邀请的人';

-- ----------------------------
-- Records of invite
-- ----------------------------
INSERT INTO `invite` VALUES ('1', '5', '3', '18', '2014-07-02 16:22:20', '2014-07-02 16:22:20', '0/0');
INSERT INTO `invite` VALUES ('2', '5', '3', '19', '2014-07-02 16:31:29', '2014-07-02 16:31:29', '0/0');
INSERT INTO `invite` VALUES ('3', '5', '3', '3', '2014-07-02 16:34:22', '2014-07-02 16:34:22', '0/0');

-- ----------------------------
-- Table structure for `problem`
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem` (
  `uuid` int(11) NOT NULL COMMENT '全局统一id',
  `problem_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题的id',
  `title` text COMMENT '问题的题目',
  `description` text COMMENT '问题的描述',
  `date` date DEFAULT NULL COMMENT '产生的时间',
  `problem_set_id` int(11) DEFAULT NULL COMMENT '试题集id',
  `creator` int(11) DEFAULT NULL COMMENT '这个表示是谁产生的问题',
  `type` int(11) NOT NULL COMMENT '1：选择题 2：编程题 4：自定义问题 3：问答题',
  `limit_time` int(11) DEFAULT NULL COMMENT '时间限制',
  `limit_mem` int(11) DEFAULT NULL COMMENT '内存限制',
  `submit` int(11) DEFAULT NULL COMMENT '总共被人做的次数',
  `sloved` int(11) DEFAULT NULL COMMENT '做对了的人次数',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者id',
  `modifyDate` datetime DEFAULT NULL COMMENT '修改者时间',
  `isdelete` int(11) DEFAULT '0' COMMENT '0:表示活跃，1表示删除',
  `belong` int(11) DEFAULT '0' COMMENT '0表示网站的题，1表示是自己的题',
  PRIMARY KEY (`problem_id`),
  KEY `f_problem_owner` (`creator`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='这个是问题表';

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES ('33', '33', 'choose1', 'choose12', '2014-07-18', null, '22', '1', '5', '512', '0', '0', '0', '2014-07-18 22:58:31', '1', '1');
INSERT INTO `problem` VALUES ('33', '34', 'choose3', 'choose12', '2014-07-18', null, '22', '1', '5', '512', '0', '0', '22', '2014-07-18 22:58:57', '1', '1');

-- ----------------------------
-- Table structure for `problem_test_case`
-- ----------------------------
DROP TABLE IF EXISTS `problem_test_case`;
CREATE TABLE `problem_test_case` (
  `test_case_id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) DEFAULT NULL COMMENT '问题编号',
  `score` int(11) DEFAULT '4' COMMENT '该测试用例的分数',
  `excepted_res` text COMMENT '预期的值',
  `args` text COMMENT '参数',
  `detail` text COMMENT '这个是说明测试用例',
  PRIMARY KEY (`test_case_id`),
  KEY `p_id` (`problem_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='这个是问题相关联问题的测试用例\r\n这个也可以表示选择题的选项和答案\r\n';

-- ----------------------------
-- Records of problem_test_case
-- ----------------------------
INSERT INTO `problem_test_case` VALUES ('49', '33', '4', 'true', '试试', null);
INSERT INTO `problem_test_case` VALUES ('50', '34', '4', 'true', '试试', null);

-- ----------------------------
-- Table structure for `quiz`
-- ----------------------------
DROP TABLE IF EXISTS `quiz`;
CREATE TABLE `quiz` (
  `quizid` int(11) NOT NULL AUTO_INCREMENT,
  `owner` int(11) NOT NULL,
  `name` text NOT NULL COMMENT '测试的名字',
  `date` datetime DEFAULT NULL COMMENT '创建的时间',
  `time` int(11) NOT NULL DEFAULT '70' COMMENT '该测试所需的时间，默认70分钟',
  `extra_info` text COMMENT '1,2,3,4 （1代表学校信息，2代表学历，3代表编码年限，4代表link',
  `uuid` int(11) DEFAULT NULL COMMENT '全局唯一识别符，只要uuid相同的，那么都是同一个测试，以时间最新的为标准',
  `emails` text COMMENT '这个里面是测试报告发送地址的邮件，使用逗号隔开',
  PRIMARY KEY (`quizid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='这个是存放测试的表';

-- ----------------------------
-- Records of quiz
-- ----------------------------

-- ----------------------------
-- Table structure for `quizproblem`
-- ----------------------------
DROP TABLE IF EXISTS `quizproblem`;
CREATE TABLE `quizproblem` (
  `tpid` int(11) NOT NULL AUTO_INCREMENT,
  `quizid` int(11) NOT NULL COMMENT '测试id',
  `problemid` int(11) NOT NULL COMMENT '问题id',
  `date` datetime NOT NULL,
  `lang` text COMMENT '（1,2,3,4）的形式 用来保存该题目只能用哪些语言',
  PRIMARY KEY (`tpid`),
  KEY `f_tp_test` (`quizid`),
  KEY `f_tp_problem` (`problemid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='这个是测试与问题的关联表';

-- ----------------------------
-- Records of quizproblem
-- ----------------------------

-- ----------------------------
-- Table structure for `resultinfo`
-- ----------------------------
DROP TABLE IF EXISTS `resultinfo`;
CREATE TABLE `resultinfo` (
  `resultinfo_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_case_id` int(11) DEFAULT NULL COMMENT '测试用例的数目',
  `solution_id` int(11) DEFAULT NULL COMMENT 'solution_run的id',
  `cost_time` int(11) DEFAULT NULL COMMENT '程序运行所花费的时间',
  `cost_mem` int(11) DEFAULT NULL COMMENT '程序运行所花费的内存',
  `test_case_result` text COMMENT '这个是用户自定义测试用例返回的结果',
  `test_case` text COMMENT '这个是用户自定义的测试用例',
  `score` int(11) DEFAULT NULL COMMENT '得分',
  PRIMARY KEY (`resultinfo_id`),
  KEY `f_result_so_id` (`solution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个是用来记录用户的答题信息';

-- ----------------------------
-- Records of resultinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` int(11) NOT NULL DEFAULT '0' COMMENT '这个是角色id',
  `rolename` text,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特权等级有\r\n0：任何用户\r\n1：参与测试的用户\r\n2：hr\r\n3：网站管理员\r\n4：超级管理员';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0', 'visitor');
INSERT INTO `role` VALUES ('1', 'tester');
INSERT INTO `role` VALUES ('2', 'hr');
INSERT INTO `role` VALUES ('3', 'admin');
INSERT INTO `role` VALUES ('4', 'superadmin');

-- ----------------------------
-- Table structure for `role_api`
-- ----------------------------
DROP TABLE IF EXISTS `role_api`;
CREATE TABLE `role_api` (
  `rpid` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL COMMENT '角色表',
  `apiid` int(11) DEFAULT NULL COMMENT 'api表',
  PRIMARY KEY (`rpid`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='这个是角色-权限表';

-- ----------------------------
-- Records of role_api
-- ----------------------------
INSERT INTO `role_api` VALUES ('1', '2', '1');
INSERT INTO `role_api` VALUES ('2', '2', '2');
INSERT INTO `role_api` VALUES ('3', '2', '3');
INSERT INTO `role_api` VALUES ('4', '2', '4');
INSERT INTO `role_api` VALUES ('5', '2', '5');
INSERT INTO `role_api` VALUES ('6', '2', '6');
INSERT INTO `role_api` VALUES ('7', '3', '7');
INSERT INTO `role_api` VALUES ('8', '4', '8');
INSERT INTO `role_api` VALUES ('9', '2', '9');
INSERT INTO `role_api` VALUES ('10', '2', '10');
INSERT INTO `role_api` VALUES ('11', '2', '11');
INSERT INTO `role_api` VALUES ('12', '2', '12');
INSERT INTO `role_api` VALUES ('13', '2', '13');
INSERT INTO `role_api` VALUES ('14', '2', '14');
INSERT INTO `role_api` VALUES ('15', '2', '15');
INSERT INTO `role_api` VALUES ('16', '2', '16');
INSERT INTO `role_api` VALUES ('17', '3', '17');
INSERT INTO `role_api` VALUES ('18', '4', '18');
INSERT INTO `role_api` VALUES ('19', '3', '1');
INSERT INTO `role_api` VALUES ('20', '3', '2');
INSERT INTO `role_api` VALUES ('21', '3', '3');
INSERT INTO `role_api` VALUES ('22', '3', '4');
INSERT INTO `role_api` VALUES ('23', '3', '5');
INSERT INTO `role_api` VALUES ('24', '3', '6');
INSERT INTO `role_api` VALUES ('27', '3', '9');
INSERT INTO `role_api` VALUES ('28', '3', '10');
INSERT INTO `role_api` VALUES ('29', '3', '11');
INSERT INTO `role_api` VALUES ('30', '3', '12');
INSERT INTO `role_api` VALUES ('31', '3', '13');
INSERT INTO `role_api` VALUES ('32', '3', '14');
INSERT INTO `role_api` VALUES ('33', '3', '15');
INSERT INTO `role_api` VALUES ('34', '3', '16');
INSERT INTO `role_api` VALUES ('35', '4', '5');
INSERT INTO `role_api` VALUES ('36', '4', '4');
INSERT INTO `role_api` VALUES ('37', '4', '3');
INSERT INTO `role_api` VALUES ('38', '4', '2');
INSERT INTO `role_api` VALUES ('39', '4', '1');
INSERT INTO `role_api` VALUES ('40', '4', '6');
INSERT INTO `role_api` VALUES ('41', '4', '7');
INSERT INTO `role_api` VALUES ('42', '4', '5');
INSERT INTO `role_api` VALUES ('43', '4', '9');
INSERT INTO `role_api` VALUES ('44', '4', '10');
INSERT INTO `role_api` VALUES ('45', '4', '11');
INSERT INTO `role_api` VALUES ('46', '4', '12');
INSERT INTO `role_api` VALUES ('47', '4', '14');
INSERT INTO `role_api` VALUES ('48', '4', '13');
INSERT INTO `role_api` VALUES ('49', '4', '15');
INSERT INTO `role_api` VALUES ('50', '4', '16');
INSERT INTO `role_api` VALUES ('51', '4', '17');
INSERT INTO `role_api` VALUES ('52', '1', '4');
INSERT INTO `role_api` VALUES ('53', '1', '3');
INSERT INTO `role_api` VALUES ('54', '0', '3');
INSERT INTO `role_api` VALUES ('55', '2', '19');
INSERT INTO `role_api` VALUES ('56', '3', '19');
INSERT INTO `role_api` VALUES ('57', '2', '20');
INSERT INTO `role_api` VALUES ('58', '3', '20');
INSERT INTO `role_api` VALUES ('59', '2', '21');
INSERT INTO `role_api` VALUES ('60', '3', '21');
INSERT INTO `role_api` VALUES ('61', '2', '22');
INSERT INTO `role_api` VALUES ('62', '3', '22');
INSERT INTO `role_api` VALUES ('63', '2', '23');
INSERT INTO `role_api` VALUES ('64', '3', '23');

-- ----------------------------
-- Table structure for `sets`
-- ----------------------------
DROP TABLE IF EXISTS `sets`;
CREATE TABLE `sets` (
  `problem_set_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `date` datetime DEFAULT NULL,
  `owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`problem_set_id`),
  KEY `f_owner_id` (`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='这个是试题集信息表';

-- ----------------------------
-- Records of sets
-- ----------------------------
INSERT INTO `sets` VALUES ('1', 'C', '2014-07-14 15:52:56', '1');
INSERT INTO `sets` VALUES ('2', 'Java ELITE', '2014-07-14 15:53:18', '1');
INSERT INTO `sets` VALUES ('3', 'Java PRIMARY', '2014-07-13 15:53:40', '1');
INSERT INTO `sets` VALUES ('4', 'Algorithm', '2014-07-14 15:54:03', '1');

-- ----------------------------
-- Table structure for `solution_record`
-- ----------------------------
DROP TABLE IF EXISTS `solution_record`;
CREATE TABLE `solution_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '这个主要是用来记录用户改变程序后的快照',
  `uid` int(11) DEFAULT NULL COMMENT '用户email',
  `problem_id` int(11) DEFAULT NULL COMMENT '问题id',
  `test_id` int(11) DEFAULT NULL COMMENT '测试的id',
  `date` date DEFAULT NULL COMMENT '发生的时间',
  `solution` text COMMENT '快照源码',
  `language` int(11) DEFAULT NULL COMMENT '语言',
  PRIMARY KEY (`record_id`),
  KEY `f_record_prob_if` (`problem_id`),
  KEY `f_record_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个是用来记录用户编程的记录';

-- ----------------------------
-- Records of solution_record
-- ----------------------------

-- ----------------------------
-- Table structure for `solution_run`
-- ----------------------------
DROP TABLE IF EXISTS `solution_run`;
CREATE TABLE `solution_run` (
  `solution_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户的email',
  `quizid` int(11) DEFAULT NULL COMMENT '测试题编号',
  `problem_id` int(11) DEFAULT NULL COMMENT '问题编号，当问题编号为-1的时候，则是用户自己运行测试',
  `solution` text COMMENT '这个是程序',
  `user_test_case` text COMMENT '这个是用户自定义的测试用例',
  `error` text COMMENT '出错信息',
  `language` int(11) DEFAULT NULL COMMENT '编程语言',
  `date` datetime DEFAULT NULL COMMENT '写入数据库的时间',
  `type` int(11) DEFAULT NULL COMMENT '0:表示待测；1表示编译出错；2表示运行出错；3表示运行ok',
  PRIMARY KEY (`solution_id`),
  KEY `p_id` (`problem_id`),
  KEY `f_run_test_id` (`quizid`),
  KEY `f_run_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个是用来记录用户的编程的信息';

-- ----------------------------
-- Records of solution_run
-- ----------------------------

-- ----------------------------
-- Table structure for `tag`
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tagid` int(11) NOT NULL AUTO_INCREMENT,
  `context` text COMMENT '内tag容',
  PRIMARY KEY (`tagid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='标签的表';

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('1', '测试');
INSERT INTO `tag` VALUES ('2', '水货');
INSERT INTO `tag` VALUES ('3', '选择题');
INSERT INTO `tag` VALUES ('4', 'yj');
INSERT INTO `tag` VALUES ('5', 'test');
INSERT INTO `tag` VALUES ('6', 'hhg');
INSERT INTO `tag` VALUES ('7', 'fff');
INSERT INTO `tag` VALUES ('8', 'dd');
INSERT INTO `tag` VALUES ('9', 'ss');
INSERT INTO `tag` VALUES ('10', 'gg');
INSERT INTO `tag` VALUES ('11', 'hh');
INSERT INTO `tag` VALUES ('12', 'dd&hh');
INSERT INTO `tag` VALUES ('13', '');
INSERT INTO `tag` VALUES ('14', 'rrr');
INSERT INTO `tag` VALUES ('15', 'eee');
INSERT INTO `tag` VALUES ('16', 'er');

-- ----------------------------
-- Table structure for `tagproblem`
-- ----------------------------
DROP TABLE IF EXISTS `tagproblem`;
CREATE TABLE `tagproblem` (
  `tpid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tag和problem的关联表',
  `tagid` int(11) NOT NULL,
  `problemid` int(11) NOT NULL,
  PRIMARY KEY (`tpid`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='tag和problem的关联表';

-- ----------------------------
-- Records of tagproblem
-- ----------------------------
INSERT INTO `tagproblem` VALUES ('46', '5', '33');
INSERT INTO `tagproblem` VALUES ('47', '5', '34');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '这个是用户的id\r\n用户角色包括\r\n1：被测试的用户\r\n2：hr\r\n3：网站管理员\r\n4：系统管理员',
  `fname` text COMMENT '这个是first name',
  `lname` text COMMENT '这个是last name',
  `email` text NOT NULL COMMENT 'email',
  `company` text,
  `privilege` int(11) NOT NULL DEFAULT '2' COMMENT '特权级别',
  `pwd` text,
  `link` text,
  `age` text,
  `degree` text,
  `school` text,
  `register_date` datetime DEFAULT NULL COMMENT '注册日期',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录日期',
  `invited_left` int(11) NOT NULL DEFAULT '0' COMMENT '这个是还剩余多少邀请次数',
  `invited_num` int(11) NOT NULL DEFAULT '0' COMMENT '这个是已经邀请的人数',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '1表示活着的，0表示死掉了，默认为1',
  `tel` text NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='这个是用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', 'xx@qq.com', 'zpl', '2', null, null, null, null, null, '2014-06-26 21:28:03', null, '0', '0', '1', '');
INSERT INTO `user` VALUES ('3', 'ss', 'no value', '524510356@qq.com', 'QQ', '3', 'zpl', 'no value', 'no value', 'no value', 'no value', '2014-06-29 18:36:15', '2014-07-12 17:39:08', '97', '1', '1', '188888888');
INSERT INTO `user` VALUES ('4', 'atom', 'no value', '524510356@gmail.com', 'xxoo', '2', 'dhsyd7623', 'no value', 'no value', 'no value', 'no value', '2014-06-30 10:43:32', '2014-06-30 10:43:32', '0', '0', '1', '23232323232');
INSERT INTO `user` VALUES ('18', 'liu1', 'zheng', 'liuzheng712@gmail.com', 'no value', '3', 'eb6efe717485bd28fb98f89b3d924725', 'no value', 'no value', 'no value', 'no value', '2014-07-02 16:15:42', '2014-07-02 16:15:42', '999', '0', '1', '123232323');
INSERT INTO `user` VALUES ('19', 'liu1', 'zheng', '524510356@ss.com', 'no value', '1', '7DE125139D33F97FB8DB63CD6F55E942', 'no value', 'no value', 'no value', 'no value', '2014-07-02 16:31:12', '2014-07-02 16:31:12', '0', '0', '1', '123232323');
INSERT INTO `user` VALUES ('20', null, 'no value', 'test4test@qq.com', 'no value', '2', 'zplzplzpl', 'no value', 'no value', 'no value', 'no value', '2014-07-08 21:25:44', '2014-07-08 21:25:44', '0', '0', '1', 'no value');
INSERT INTO `user` VALUES ('21', null, 'no value', 'ttt@qq.com', 'no value', '2', '5a190b68893cef8c888a9f72df07b361', 'no value', 'no value', 'no value', 'no value', '2014-07-08 21:32:57', '2014-07-08 21:51:44', '0', '0', '1', 'no value');
INSERT INTO `user` VALUES ('22', null, 'no value', '294384672@qq.com', 'no value', '2', 'b250e1a65dfaa13e65f58b7adbed3ec4', 'no value', 'no value', 'no value', 'no value', '2014-07-13 09:36:52', '2014-07-18 22:46:45', '0', '0', '1', 'no value');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `ruid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `roleid` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`ruid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个是user和role关系表';

-- ----------------------------
-- Records of user_role
-- ----------------------------

-- ----------------------------
-- Table structure for `user_test_case`
-- ----------------------------
DROP TABLE IF EXISTS `user_test_case`;
CREATE TABLE `user_test_case` (
  `test_case_id` int(11) NOT NULL AUTO_INCREMENT,
  `solution_id` int(11) DEFAULT NULL,
  `excepted_res` text,
  `args` text,
  PRIMARY KEY (`test_case_id`),
  KEY `F_user_test_solution` (`solution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户编程的时候自定义测试用例';

-- ----------------------------
-- Records of user_test_case
-- ----------------------------

-- ----------------------------
-- Table structure for `schools`
-- ----------------------------
DROP TABLE IF EXISTS `schools`;
CREATE TABLE `schools`
(
   `id`       int(10)  NOT NULL AUTO_INCREMENT,
   `code`     int(10) not null comment '学校代码',
   `name`     varchar(255) not null comment '汉字名称',
   `pinyin`   varchar(255) default null comment '拼音名称',
   `alp`      varchar(255) not null,
   primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schools
-- ----------------------------
INSERT INTO `schools` VALUES ('1', '10001', '复旦大学', 'fudandaxue', 'fddx');
INSERT INTO `schools` VALUES ('2', '10002', '同济大学', 'tongjidaxue', 'tjdx');
INSERT INTO `schools` VALUES ('3', '20001', '清华大学', 'qinghuadaxue', 'qhdx');
INSERT INTO `schools` VALUES ('4', '20002', '北京大学', 'beijingdaxue', 'bjdx');
INSERT INTO `schools` VALUES ('5', '30001', '浙江大学', 'zhejiangdaxue', 'zjdx');
INSERT INTO `schools` VALUES ('6', '40001', '南京大学', 'nanjingdaxue', 'njdx');

-- ----------------------------
-- Table structure for `verify_question`
-- ----------------------------
DROP TABLE IF EXISTS `verify_question`;
CREATE TABLE `verify_question` (
  `id` int(11) NOT NULL,
  `question` varchar(255) NOT NULL COMMENT '验证问题',
  `answer` varchar(255) NOT NULL COMMENT '验证答案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of verify_question
-- ----------------------------
INSERT INTO `verify_question` VALUES ('1', '本站网址是？', 'www.findfool.com');
INSERT INTO `verify_question` VALUES ('2', '本站中文名称是？', '余悦');
INSERT INTO `verify_question` VALUES ('3', '圣诞节是几月几日？', '12.25');
INSERT INTO `verify_question` VALUES ('4', '中国的英文名称是？', 'china');
