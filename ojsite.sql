-- MySQL dump 10.13  Distrib 5.1.63, for apple-darwin10.3.0 (i386)
--
-- Host: localhost    Database: ojsite
-- ------------------------------------------------------
-- Server version	5.1.63

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `api`
--

DROP TABLE IF EXISTS `api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api` (
  `apiid` int(11) NOT NULL AUTO_INCREMENT,
  `api` text NOT NULL COMMENT '这个是有该权限的api',
  PRIMARY KEY (`apiid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='这个是api表\r\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api`
--

LOCK TABLES `api` WRITE;
/*!40000 ALTER TABLE `api` DISABLE KEYS */;
INSERT INTO `api` VALUES (1,'/user/add/hr'),(2,'/user/confirm'),(3,'/contactus'),(4,'/question/query'),(5,'/search/site'),(6,'/search/my'),(7,'/search/user/common'),(8,'/search/admin'),(9,'/test/manage/submite'),(10,'/question/add'),(11,'/test/show'),(12,'/test/manage'),(13,'/test/manage/setting/set'),(14,'/test/manage/invite'),(15,'/user/setting/query'),(16,'/user/setting/set'),(17,'/set/manage'),(18,'/user/add/admin'),(19,'/test/add'),(20,'/test/queryByID'),(21,'/test/queryByName'),(22,'/search/sets'),(23,'/question/delete'),(24,'/test/addquestion');
/*!40000 ALTER TABLE `api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactus`
--

DROP TABLE IF EXISTS `contactus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactus` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `email` text NOT NULL COMMENT '这个是留言者的email',
  `name` text COMMENT '留言者名字',
  `context` text COMMENT '内容',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='留言表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactus`
--

LOCK TABLES `contactus` WRITE;
/*!40000 ALTER TABLE `contactus` DISABLE KEYS */;
INSERT INTO `contactus` VALUES (1,'xxx','zpl','fuck fuck fuck ');
/*!40000 ALTER TABLE `contactus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invite`
--

DROP TABLE IF EXISTS `invite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invite` (
  `iid` int(11) NOT NULL AUTO_INCREMENT,
  `testid` int(11) DEFAULT NULL COMMENT '测试的id',
  `hrid` int(11) DEFAULT NULL COMMENT '测试的hr',
  `uid` int(11) DEFAULT NULL COMMENT '被邀请的用户id，如果该email第一次邀请，则系统自动注册',
  `inviteTime` varchar(20) DEFAULT NULL COMMENT '邀请发出去的时间',
  `finishtime` varchar(20) DEFAULT NULL COMMENT '用户完成的时间，如果是0表示没有完成',
  `score` int(10) DEFAULT '0' COMMENT '存放的形式为 20/50',
  `begintime` varchar(20) DEFAULT NULL COMMENT '用户开始做测试的时间',
  `duration` varchar(20) DEFAULT NULL COMMENT '做题的时间',
  `state` int(10) DEFAULT '0' COMMENT '该邀请当前是否生效，如果试题已结束则无效，便于定时器更新',
  `totalScore` int(10) DEFAULT NULL COMMENT '该套题的满分',
  PRIMARY KEY (`iid`),
  KEY `f_t_i` (`testid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='这个是邀请的表，里面存放着被邀请的人';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invite`
--

LOCK TABLES `invite` WRITE;
/*!40000 ALTER TABLE `invite` DISABLE KEYS */;
INSERT INTO `invite` VALUES (16,1,23,1,'2014-12-01 22:43','',0,NULL,'10000',0,0);
/*!40000 ALTER TABLE `invite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `uuid` int(11) DEFAULT NULL COMMENT '全局统一id',
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
  `rightanswer` varchar(20) DEFAULT NULL COMMENT '正确答案',
  `score` int(10) DEFAULT NULL,
  `explain` varchar(20) DEFAULT NULL COMMENT '题目的解答',
  PRIMARY KEY (`problem_id`),
  KEY `f_problem_owner` (`creator`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='这个是问题表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (33,33,'choose1','choose12','2014-07-18',NULL,22,1,5,512,0,0,0,'2014-07-18 22:58:31',1,1,'0000',4,NULL),(33,34,'choose3','choose12','2014-07-18',NULL,22,1,5,512,0,0,22,'2014-07-18 22:58:57',1,1,'0000',4,NULL),(35,35,'test1','<p>just test</p>\n\n<div id=\"xunlei_com_thunder_helper_plugin_d462f475-c18e-46be-bd10-327458d045bd\">&nbsp;</div>\n','2014-11-11',NULL,23,1,5,512,0,0,0,'2014-11-11 11:10:10',0,1,'0000',4,NULL),(36,36,'',NULL,'2014-11-11',1,23,1,5,512,0,0,0,'2014-11-11 19:14:25',0,1,'1000',4,NULL),(-1,37,'123','<p>123333444</p>\n','2014-11-11',NULL,23,1,5,512,0,0,23,'2014-11-25 16:22:57',0,1,'0000',4,NULL),(38,38,'123','<p>1234444</p>\n','2014-11-11',NULL,23,1,5,512,0,0,23,'2014-11-25 16:50:05',0,1,'0000',4,NULL),(39,39,'','<p>12</p>\n\n<p>12</p>\n\n<p>12</p>\n\n<p>12</p>\n\n<p>12</p>\n\n<p>12</p>\n\n<p>12</p>\n','2014-11-15',NULL,23,1,5,512,0,0,0,'2014-11-15 11:33:21',0,1,'0000',4,NULL),(40,40,'rr','<p>rr</p>\n','2014-11-18',1,23,1,5,512,0,0,0,'2014-11-18 09:41:31',0,1,'1000',4,NULL),(35,41,'test1','<p>just test</p>\n\n<p>&nbsp;</p>\n','2014-11-11',NULL,23,1,5,512,0,0,23,'2014-11-20 23:52:17',0,1,'0000',4,NULL),(35,42,'test1','<p>just test</p>\n\n<p>&nbsp;</p>\n','2014-11-11',NULL,23,1,5,512,0,0,23,'2014-11-20 23:53:29',0,1,'0000',4,NULL),(35,43,'test1','<p>just test</p>\n\n<p>&nbsp;</p>\n','2014-11-11',2,23,1,5,512,0,0,23,'2014-11-21 00:29:44',0,1,'1000',4,NULL),(38,44,'123321','<p>123</p>\n','2014-11-11',1,23,1,5,512,0,0,23,'2014-11-24 13:54:23',0,0,'0000',4,NULL),(38,45,'123342','<p>123</p>\n','2014-11-11',1,23,1,5,512,0,0,23,'2014-11-24 13:54:41',0,0,'0000',4,NULL),(38,46,'123','<p>123456783</p>\n','2014-11-11',1,23,1,5,512,0,0,23,'2014-11-25 15:15:11',0,0,'0000',4,NULL),(38,47,'123','<p>123456</p>\n','2014-11-11',2,23,1,5,512,0,0,23,'2014-11-25 15:20:29',0,0,'1000',4,NULL),(48,48,'program test','<p><br />\nprogram test1</p>\n','2014-12-03',NULL,23,2,5,512,0,0,0,'2014-12-03 17:08:36',0,1,'0000',4,NULL),(49,49,'',NULL,'2014-12-03',NULL,23,2,5,512,0,0,0,'2014-12-03 17:19:06',0,1,'0000',4,NULL),(50,50,'',NULL,'2014-12-03',NULL,23,2,5,512,0,0,0,'2014-12-03 17:41:17',0,1,'0000',4,NULL),(51,51,'program test2','<p>test case test2</p>\n','2014-12-03',NULL,23,2,5,512,0,0,0,'2014-12-03 17:42:31',0,1,'0000',4,NULL),(52,52,'program test 3','<p>program test 3333&nbsp; &nbsp;</p>\n','2014-12-03',NULL,23,2,5,512,0,0,0,'2014-12-03 22:35:33',0,1,'0000',4,NULL),(53,53,'program testcase4','<p>program testcase4</p>\n','2014-12-03',3,23,2,5,512,0,0,0,'2014-12-03 22:58:09',0,1,'0000',4,NULL),(NULL,54,NULL,'angularjs中的服务实质上是',NULL,5,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,0,0,'0100',4,NULL),(NULL,55,NULL,'\"使用了angularjs的如下代码：\nfunctionCtrl($scope) {\n  $scope.message =\"\"Waiting 2000ms for update\"\";    \n  setTimeout(function () {\n  　　$scope.message =\"\"Timeout called!\"\";\n     \n  }, 2000); \n}输出是\"',NULL,5,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,5,0,'0',4,NULL),(NULL,56,NULL,'AngularJS中指定控制器的是哪个指令？',NULL,1,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,5,0,'0',4,NULL),(NULL,57,NULL,'AngularJS元素过滤的正确写法是哪一个？',NULL,1,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,5,0,'0',4,NULL),(NULL,58,NULL,'angularjs中服务的正确写法是？',NULL,100,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,5,0,'0',4,NULL),(NULL,59,NULL,'angularjs使用了mvc进行web开发，其中控制层一般用什么语言来定义',NULL,5,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,0,0,'1000',4,NULL),(NULL,60,NULL,'angularjs中control间通信最好使用什么方式？',NULL,10,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,5,0,'0',4,NULL),(NULL,61,NULL,'angularjs中的$apply()的作用是？',NULL,10,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,5,0,'0',4,NULL),(NULL,62,NULL,'\"使用了angularjs的如下代码：\nfunctionCtrl($scope) {\n  $scope.message =\"\"Waiting 2000ms for update\"\";    \n  setTimeout(function () {\n  　　$scope.message =\"\"Timeout called!\"\";\n     \n  }, 2000); \n}输出是\"',NULL,1,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,5,0,'0',4,NULL);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_test_case`
--

DROP TABLE IF EXISTS `problem_test_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_test_case` (
  `test_case_id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) DEFAULT NULL COMMENT '问题编号',
  `score` int(11) DEFAULT '4' COMMENT '该测试用例的分数',
  `excepted_res` text COMMENT '预期的值',
  `args` text COMMENT '参数',
  `detail` text COMMENT '描述测试用例',
  PRIMARY KEY (`test_case_id`),
  KEY `p_id` (`problem_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COMMENT='这个是问题相关联问题的测试用例\r\n这个也可以表示选择题的选项和答案\r\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_test_case`
--

LOCK TABLES `problem_test_case` WRITE;
/*!40000 ALTER TABLE `problem_test_case` DISABLE KEYS */;
INSERT INTO `problem_test_case` VALUES (49,33,4,'true','试试',NULL),(50,34,4,'true','试试',NULL),(51,35,4,'true','option1',NULL),(52,35,4,'false','option2',NULL),(53,38,4,'true','<p>123444</p>\n',NULL),(54,38,4,'true','<p>123</p>\n',NULL),(55,39,4,'false','12',NULL),(56,39,4,'false','12',NULL),(57,40,4,'true','123',NULL),(58,40,4,'false','234',NULL),(59,40,4,'false','456',NULL),(60,41,4,'true','option1',NULL),(61,41,4,'false','option2',NULL),(62,42,4,'true','option1',NULL),(63,42,4,'false','option2',NULL),(64,43,4,'true','option1',NULL),(65,43,4,'true','option2',NULL),(66,44,4,'false','<p>123</p>\n',NULL),(67,44,4,'false','<p>123</p>\n',NULL),(68,45,4,'false','<p>123</p>\n',NULL),(69,45,4,'false','<p>123</p>\n',NULL),(70,46,4,'false','<p>123</p>\n',NULL),(71,46,4,'false','<p>123</p>\n',NULL),(72,49,4,'(1,2,3)','<p>1</p>\n\n<p>2</p>\n\n<p>3</p>\n',NULL),(73,49,4,'123','<p>2</p>\n\n<p>3</p>\n',NULL),(74,51,4,'456',NULL,NULL),(75,51,4,'890',NULL,NULL),(76,52,4,'789',NULL,NULL),(77,53,10,'456','123',NULL),(78,53,4,'bbb','aaa',NULL);
/*!40000 ALTER TABLE `problem_test_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='这个是存放测试的表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (1,23,'test1','2014-11-05 11:24:25',70,'',1,'solevial@sohu.com'),(2,23,'test2','2014-11-05 13:11:34',70,'',2,'solevial@sohu.com'),(3,23,'test3','2014-11-05 14:04:10',70,'',3,'solevial@sohu.com'),(4,24,'abc','2014-11-06 19:31:57',70,'1,2',4,'123@qq.com'),(5,23,'tt3','2014-11-11 17:31:36',70,'',5,'solevial@sohu.com'),(6,23,'test4','2014-12-15 11:04:18',70,'just test4',6,NULL);
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizproblem`
--

DROP TABLE IF EXISTS `quizproblem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quizproblem` (
  `tpid` int(11) NOT NULL AUTO_INCREMENT,
  `quizid` int(11) NOT NULL COMMENT '测试id',
  `problemid` int(11) NOT NULL COMMENT '问题id',
  `date` datetime DEFAULT NULL,
  `lang` text COMMENT '（1,2,3,4）的形式 用来保存该题目只能用哪些语言',
  PRIMARY KEY (`tpid`),
  KEY `f_tp_test` (`quizid`),
  KEY `f_tp_problem` (`problemid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='这个是测试与问题的关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizproblem`
--

LOCK TABLES `quizproblem` WRITE;
/*!40000 ALTER TABLE `quizproblem` DISABLE KEYS */;
INSERT INTO `quizproblem` VALUES (5,2,35,NULL,NULL),(9,1,40,NULL,NULL),(10,1,36,NULL,NULL),(12,1,43,NULL,NULL),(13,1,47,NULL,NULL),(19,1,53,NULL,NULL),(20,1,46,NULL,NULL);
/*!40000 ALTER TABLE `quizproblem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultinfo`
--

DROP TABLE IF EXISTS `resultinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultinfo`
--

LOCK TABLES `resultinfo` WRITE;
/*!40000 ALTER TABLE `resultinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `roleid` int(11) NOT NULL DEFAULT '0' COMMENT '这个是角色id',
  `rolename` text,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特权等级有\r\n0：任何用户\r\n1：参与测试的用户\r\n2：hr\r\n3：网站管理员\r\n4：超级管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (0,'visitor'),(1,'tester'),(2,'hr'),(3,'admin'),(4,'superadmin');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_api`
--

DROP TABLE IF EXISTS `role_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_api` (
  `rpid` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL COMMENT '角色表',
  `apiid` int(11) DEFAULT NULL COMMENT 'api表',
  PRIMARY KEY (`rpid`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='这个是角色-权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_api`
--

LOCK TABLES `role_api` WRITE;
/*!40000 ALTER TABLE `role_api` DISABLE KEYS */;
INSERT INTO `role_api` VALUES (1,2,1),(2,2,2),(3,2,3),(4,2,4),(5,2,5),(6,2,6),(7,3,7),(8,4,8),(9,2,9),(10,2,10),(11,2,11),(12,2,12),(13,2,13),(14,2,14),(15,2,15),(16,2,16),(17,3,17),(18,4,18),(19,3,1),(20,3,2),(21,3,3),(22,3,4),(23,3,5),(24,3,6),(27,3,9),(28,3,10),(29,3,11),(30,3,12),(31,3,13),(32,3,14),(33,3,15),(34,3,16),(35,4,5),(36,4,4),(37,4,3),(38,4,2),(39,4,1),(40,4,6),(41,4,7),(42,4,5),(43,4,9),(44,4,10),(45,4,11),(46,4,12),(47,4,14),(48,4,13),(49,4,15),(50,4,16),(51,4,17),(52,1,4),(53,1,3),(54,0,3),(55,2,19),(56,3,19),(57,2,20),(58,3,20),(59,2,21),(60,3,21),(61,2,22),(62,3,22),(63,2,23),(64,3,23),(65,2,24),(66,3,24);
/*!40000 ALTER TABLE `role_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sets`
--

DROP TABLE IF EXISTS `sets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sets` (
  `problem_set_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `date` datetime DEFAULT NULL,
  `owner` int(11) DEFAULT NULL,
  `comment` varchar(1000) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`problem_set_id`),
  KEY `f_owner_id` (`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='这个是试题集信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sets`
--

LOCK TABLES `sets` WRITE;
/*!40000 ALTER TABLE `sets` DISABLE KEYS */;
INSERT INTO `sets` VALUES (1,'HTML5','2014-07-14 15:52:56',1,'HTML知识'),(2,'CSS3','2014-07-14 15:53:18',1,'CSS知识'),(3,'JAVASCRIPT\n','2014-07-13 15:53:40',1,'javascript知识'),(5,'BOOTSTRAP',NULL,1,'先进的响应式框架'),(6,'ANGULARJS',NULL,1,'前段MVC框架');
/*!40000 ALTER TABLE `sets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solution_record`
--

DROP TABLE IF EXISTS `solution_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solution_record`
--

LOCK TABLES `solution_record` WRITE;
/*!40000 ALTER TABLE `solution_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `solution_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solution_run`
--

DROP TABLE IF EXISTS `solution_run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `test_id` int(10) DEFAULT NULL COMMENT '测试的id',
  PRIMARY KEY (`solution_id`),
  KEY `p_id` (`problem_id`),
  KEY `f_run_test_id` (`quizid`),
  KEY `f_run_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='这个是用来记录用户的编程的信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solution_run`
--

LOCK TABLES `solution_run` WRITE;
/*!40000 ALTER TABLE `solution_run` DISABLE KEYS */;
INSERT INTO `solution_run` VALUES (1,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-05 18:02:47',0,1),(2,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-05 23:04:00',0,1),(3,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-09 16:12:01',0,1),(4,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-09 16:13:16',0,1),(5,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-09 16:24:13',0,1),(6,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-09 16:25:39',0,1),(7,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-09 16:42:14',0,1),(8,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-09 16:45:40',0,1),(9,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-09 16:46:00',0,1),(10,1,NULL,53,'#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}',NULL,NULL,1,'2014-12-14 19:34:23',0,1);
/*!40000 ALTER TABLE `solution_run` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tagid` int(11) NOT NULL AUTO_INCREMENT,
  `context` text COMMENT '内tag容',
  PRIMARY KEY (`tagid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='标签的表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'测试'),(2,'水货'),(3,'选择题'),(4,'yj'),(5,'test'),(6,'hhg'),(7,'fff'),(8,'dd'),(9,'ss'),(10,'gg'),(11,'hh'),(12,'dd&hh'),(13,''),(14,'rrr'),(15,'eee'),(16,'er'),(17,'1'),(18,'2'),(19,'3');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tagproblem`
--

DROP TABLE IF EXISTS `tagproblem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tagproblem` (
  `tpid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tag和problem的关联表',
  `tagid` int(11) NOT NULL,
  `problemid` int(11) NOT NULL,
  PRIMARY KEY (`tpid`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='tag和problem的关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tagproblem`
--

LOCK TABLES `tagproblem` WRITE;
/*!40000 ALTER TABLE `tagproblem` DISABLE KEYS */;
INSERT INTO `tagproblem` VALUES (46,5,33),(47,5,34),(48,17,35),(49,18,35),(50,19,35),(51,17,41),(52,18,41),(53,19,41),(54,17,42),(55,18,42),(56,19,42),(57,17,43),(58,18,43),(59,19,43),(60,17,48),(61,18,48);
/*!40000 ALTER TABLE `tagproblem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testuser`
--

DROP TABLE IF EXISTS `testuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testuser` (
  `tuid` int(10) NOT NULL AUTO_INCREMENT COMMENT 'test user id 主键',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名，预留',
  `email` varchar(20) NOT NULL COMMENT 'email地址，用此来标识用户',
  `school` varchar(100) DEFAULT NULL COMMENT '学校',
  `company` varchar(100) DEFAULT NULL COMMENT '公司',
  `blog` varchar(100) DEFAULT NULL COMMENT '个人博客，git等等',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `pwd` varchar(100) DEFAULT NULL COMMENT '密码',
  `tel` varchar(20) DEFAULT NULL COMMENT '电话',
  `registerdate` varchar(20) DEFAULT NULL COMMENT '用户生成日期',
  `lastlogindate` varchar(20) DEFAULT NULL COMMENT '用户登陆时间',
  `state` varchar(1) DEFAULT '0' COMMENT '用户状态',
  `degree` varchar(20) DEFAULT NULL COMMENT '学历',
  PRIMARY KEY (`tuid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='testuser表，用于保存做测试的用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testuser`
--

LOCK TABLES `testuser` WRITE;
/*!40000 ALTER TABLE `testuser` DISABLE KEYS */;
INSERT INTO `testuser` VALUES (1,'11','693605668@qq.com','11','',NULL,0,'',NULL,'','','1',NULL);
/*!40000 ALTER TABLE `testuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testuser_problem`
--

DROP TABLE IF EXISTS `testuser_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testuser_problem` (
  `tuid` int(10) NOT NULL,
  `problemid` int(10) NOT NULL,
  `useranswer` varchar(20) DEFAULT '0000' COMMENT '用户的答案',
  `rightanswer` varchar(20) DEFAULT NULL COMMENT '正确答案',
  `type` int(10) DEFAULT NULL COMMENT '试题类型',
  `invite_id` int(10) DEFAULT NULL COMMENT '邀请id',
  PRIMARY KEY (`tuid`,`problemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='testuser和problem的关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testuser_problem`
--

LOCK TABLES `testuser_problem` WRITE;
/*!40000 ALTER TABLE `testuser_problem` DISABLE KEYS */;
INSERT INTO `testuser_problem` VALUES (1,36,'','0000',1,16),(1,40,'000','0000',1,16),(1,43,'00','0000',1,16),(1,47,'','0000',1,16),(1,53,'00','0000',2,16);
/*!40000 ALTER TABLE `testuser_problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='这个是用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','xx@qq.com','zpl',2,NULL,NULL,NULL,NULL,NULL,'2014-06-26 21:28:03',NULL,0,0,1,''),(3,'ss','no value','524510356@qq.com','QQ',3,'zpl','no value','no value','no value','no value','2014-06-29 18:36:15','2014-07-12 17:39:08',97,1,1,'188888888'),(4,'atom','no value','524510356@gmail.com','xxoo',2,'dhsyd7623','no value','no value','no value','no value','2014-06-30 10:43:32','2014-06-30 10:43:32',0,0,1,'23232323232'),(18,'liu1','zheng','liuzheng712@gmail.com','no value',3,'eb6efe717485bd28fb98f89b3d924725','no value','no value','no value','no value','2014-07-02 16:15:42','2014-07-02 16:15:42',999,0,1,'123232323'),(19,'liu1','zheng','524510356@ss.com','no value',1,'7DE125139D33F97FB8DB63CD6F55E942','no value','no value','no value','no value','2014-07-02 16:31:12','2014-07-02 16:31:12',0,0,1,'123232323'),(20,NULL,'no value','test4test@qq.com','no value',2,'zplzplzpl','no value','no value','no value','no value','2014-07-08 21:25:44','2014-07-08 21:25:44',0,0,1,'no value'),(21,NULL,'no value','ttt@qq.com','no value',2,'5a190b68893cef8c888a9f72df07b361','no value','no value','no value','no value','2014-07-08 21:32:57','2014-07-08 21:51:44',0,0,1,'no value'),(22,NULL,'no value','294384672@qq.com','no value',2,'b250e1a65dfaa13e65f58b7adbed3ec4','no value','no value','no value','no value','2014-07-13 09:36:52','2014-07-18 22:46:45',0,0,1,'no value'),(23,'no value','no value','solevial@sohu.com','微财',2,'7d19237c0a90dd618ed1b3e4c73d6a39','no value','no value','no value','no value','2014-11-05 10:26:55','2014-12-18 16:18:24',82,1,1,'no value'),(24,NULL,'no value','123@qq.com','no value',2,'25d55ad283aa400af464c76d713c07ad','no value','no value','no value','no value','2014-11-06 19:28:28','2014-11-06 19:29:47',0,0,1,'no value'),(25,'Mike','fang','weifangscs@163.com','no value',1,'7EE798DC3D51438B6F81973683880F21','no value','no value','no value','no value','2014-11-28 14:38:27','2014-11-28 14:38:27',0,0,1,'123456'),(26,'mike','fang','693605668@qq.com','no value',1,'0F8FDB2594DB55B626E5EFB4416A18E9','no value','no value','no value','no value','2014-11-28 15:06:10','2014-11-28 15:06:10',0,0,1,'123456');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `ruid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `roleid` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`ruid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个是user和role关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_test_case`
--

DROP TABLE IF EXISTS `user_test_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_test_case` (
  `test_case_id` int(11) NOT NULL AUTO_INCREMENT,
  `solution_id` int(11) DEFAULT NULL,
  `excepted_res` text,
  `args` text,
  PRIMARY KEY (`test_case_id`),
  KEY `F_user_test_solution` (`solution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户编程的时候自定义测试用例';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_test_case`
--

LOCK TABLES `user_test_case` WRITE;
/*!40000 ALTER TABLE `user_test_case` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_test_case` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-18 22:43:55

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

LOCK TABLES `schools` WRITE;
-- ----------------------------
-- Records of schools
-- ----------------------------
INSERT INTO `schools` VALUES ('1', '10001', '复旦大学', 'fudandaxue', 'fddx');
INSERT INTO `schools` VALUES ('2', '10002', '同济大学', 'tongjidaxue', 'tjdx');
INSERT INTO `schools` VALUES ('3', '20001', '清华大学', 'qinghuadaxue', 'qhdx');
INSERT INTO `schools` VALUES ('4', '20002', '北京大学', 'beijingdaxue', 'bjdx');
INSERT INTO `schools` VALUES ('5', '30001', '浙江大学', 'zhejiangdaxue', 'zjdx');
INSERT INTO `schools` VALUES ('6', '40001', '南京大学', 'nanjingdaxue', 'njdx');
UNLOCK TABLES;

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

LOCK TABLES `verify_question` WRITE;
-- ----------------------------
-- Records of verify_question
-- ----------------------------
INSERT INTO `verify_question` VALUES ('1', '本站网址是？', 'www.findfool.com');
INSERT INTO `verify_question` VALUES ('2', '本站中文名称是？', '余悦');
INSERT INTO `verify_question` VALUES ('3', '圣诞节是几月几日？', '12.25');
INSERT INTO `verify_question` VALUES ('4', '中国的英文名称是？', 'china');
UNLOCK TABLES;
