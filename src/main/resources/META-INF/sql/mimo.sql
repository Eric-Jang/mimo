/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : mimo

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2012-08-02 21:32:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mimo_article`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_article`;
CREATE TABLE `mimo_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `not_empty_photos` bit(1) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `on_top` bit(1) NOT NULL,
  `status` varchar(10) NOT NULL,
  `forbid_comments` bit(1) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  `modify_time` bigint(13) NOT NULL,
  `channel_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `channel_id` (`channel_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `tags` (`tags`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `empty_photos` (`not_empty_photos`) USING BTREE,
  CONSTRAINT `mimo_article_ibfk_1` FOREIGN KEY (`channel_id`) REFERENCES `mimo_channel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_article
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_article_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_article_attachment`;
CREATE TABLE `mimo_article_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `content_type` varchar(255) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `article_id` (`article_id`) USING BTREE,
  CONSTRAINT `mimo_article_attachment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `mimo_article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_article_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_article_comment`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_article_comment`;
CREATE TABLE `mimo_article_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(32) NOT NULL,
  `content` tinytext NOT NULL,
  `status` int(11) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `article_id` (`article_id`) USING BTREE,
  CONSTRAINT `mimo_article_comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `mimo_article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_article_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_article_views`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_article_views`;
CREATE TABLE `mimo_article_views` (
  `id` int(11) NOT NULL,
  `views` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  CONSTRAINT `mimo_article_views_ibfk_1` FOREIGN KEY (`id`) REFERENCES `mimo_article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_article_views
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_channel`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_channel`;
CREATE TABLE `mimo_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `path` varchar(16) NOT NULL,
  `about` text,
  `priority` int(11) NOT NULL,
  `title` tinytext,
  `meta_keyword` tinytext,
  `meta_title` tinytext,
  `meta_descr` tinytext,
  `father_id` int(32) DEFAULT NULL,
  `self_template_id` int(11) NOT NULL,
  `article_template_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `path` (`path`) USING BTREE,
  KEY `father_id` (`father_id`) USING BTREE,
  KEY `name` (`name`) USING BTREE,
  KEY `self_template_id` (`self_template_id`),
  KEY `article_template_id` (`article_template_id`),
  CONSTRAINT `mimo_channel_ibfk_1` FOREIGN KEY (`father_id`) REFERENCES `mimo_channel` (`id`) ON DELETE CASCADE,
  CONSTRAINT `mimo_channel_ibfk_2` FOREIGN KEY (`self_template_id`) REFERENCES `mimo_template` (`id`),
  CONSTRAINT `mimo_channel_ibfk_3` FOREIGN KEY (`article_template_id`) REFERENCES `mimo_template` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_channel
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_confs`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_confs`;
CREATE TABLE `mimo_confs` (
  `name` varchar(255) NOT NULL,
  `data` blob,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_confs
-- ----------------------------
INSERT INTO `mimo_confs` VALUES ('conf', null);
INSERT INTO `mimo_confs` VALUES ('email_conf', null);
INSERT INTO `mimo_confs` VALUES ('home_conf', null);

-- ----------------------------
-- Table structure for `mimo_guestbook`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_guestbook`;
CREATE TABLE `mimo_guestbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(32) NOT NULL,
  `email` varchar(32) NOT NULL,
  `content` tinytext NOT NULL,
  `admin` bit(1) NOT NULL,
  `status` int(11) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `email` (`email`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_guestbook
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_monitoring_record`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_monitoring_record`;
CREATE TABLE `mimo_monitoring_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actor` varchar(255) NOT NULL,
  `action` varchar(255) NOT NULL,
  `source` varchar(255) NOT NULL,
  `target` varchar(255) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  `elapsed_time` bigint(13) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `actor` (`actor`) USING BTREE,
  KEY `action` (`action`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_monitoring_record
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_security_authority`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_security_authority`;
CREATE TABLE `mimo_security_authority` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `permission` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE,
  UNIQUE KEY `permission` (`permission`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_security_authority
-- ----------------------------
INSERT INTO `mimo_security_authority` VALUES ('031412c5553111e1badbc8b1208a5409', '删除文章', 'article:delete');
INSERT INTO `mimo_security_authority` VALUES ('0f37e4c8561111e1badbc8b1208a5409', '删除文章附件', 'article-attachment:delete');
INSERT INTO `mimo_security_authority` VALUES ('28129b5c561111e1badbc8b1208a5409', '上传文章附件', 'article-attachment:upload');
INSERT INTO `mimo_security_authority` VALUES ('2a20751f553111e1badbc8b1208a5409', '查看网站资源文件', 'resource:list');
INSERT INTO `mimo_security_authority` VALUES ('2d771e86560a11e1badbc8b1208a5409', '查看留言', 'guestbook:list');
INSERT INTO `mimo_security_authority` VALUES ('3164fb0adb8111e18b91b870f44a8925', 'Email通知配置', 'email-conf:edit');
INSERT INTO `mimo_security_authority` VALUES ('3377ef58561111e1badbc8b1208a5409', '下载文章附件', 'article-attachment:download');
INSERT INTO `mimo_security_authority` VALUES ('33fc3fae5cfb11e195de360472309768', '修改首页配置', 'home-conf:edit');
INSERT INTO `mimo_security_authority` VALUES ('375b9291554011e1badbc8b1208a5409', '删除网站安全资源文件', 'security-resource:delete');
INSERT INTO `mimo_security_authority` VALUES ('37da5484558511e1badbc8b1208a5409', '查看图片资源文件', 'photo-resource:list');
INSERT INTO `mimo_security_authority` VALUES ('3c9ff420553111e1badbc8b1208a5409', '上传网站资源文件', 'resource:upload');
INSERT INTO `mimo_security_authority` VALUES ('44587c52558511e1badbc8b1208a5409', '上传图片资源文件', 'photo-resource:upload');
INSERT INTO `mimo_security_authority` VALUES ('4b7b01e3553111e1badbc8b1208a5409', '下载网站资源文件', 'resource:download');
INSERT INTO `mimo_security_authority` VALUES ('4d189dde561111e1badbc8b1208a5409', '删除文章评论', 'article-comment:delete');
INSERT INTO `mimo_security_authority` VALUES ('5ab322b1558511e1badbc8b1208a5409', '下载图片资源文件', 'photo-resource:download');
INSERT INTO `mimo_security_authority` VALUES ('5bc1994d553111e1badbc8b1208a5409', '查看网站安全资源文件', 'security-resource:list');
INSERT INTO `mimo_security_authority` VALUES ('69e85e5d553111e1badbc8b1208a5409', '上传网站安全资源文件', 'security-resource:upload');
INSERT INTO `mimo_security_authority` VALUES ('6f6c2e22558511e1badbc8b1208a5409', '删除图片资源文件', 'photo-resource:delete');
INSERT INTO `mimo_security_authority` VALUES ('75a77701561111e1badbc8b1208a5409', '修改文章评论', 'article-comment:edit');
INSERT INTO `mimo_security_authority` VALUES ('7a9b21de553111e1badbc8b1208a5409', '下载网站安全资源文件', 'security-resource:download');
INSERT INTO `mimo_security_authority` VALUES ('7e4bc13b553011e1badbc8b1208a5409', '查看栏目', 'channel:list');
INSERT INTO `mimo_security_authority` VALUES ('8875b634562d11e1badbc8b1208a5409', '删除网站资源', 'resource:delete');
INSERT INTO `mimo_security_authority` VALUES ('982f0461553011e1badbc8b1208a5409', '创建栏目', 'channel:create');
INSERT INTO `mimo_security_authority` VALUES ('a16df686553011e1badbc8b1208a5409', '编辑栏目', 'channel:edit');
INSERT INTO `mimo_security_authority` VALUES ('a922a95a553011e1badbc8b1208a5409', '删除栏目', 'channel:delete');
INSERT INTO `mimo_security_authority` VALUES ('aafc75a6560a11e1badbc8b1208a5409', '删除留言', 'guestbook:delete');
INSERT INTO `mimo_security_authority` VALUES ('b4ed4707560a11e1badbc8b1208a5409', '编辑留言', 'guestbook:edit');
INSERT INTO `mimo_security_authority` VALUES ('b6d662d7553011e1badbc8b1208a5409', '查看模板', 'template:list');
INSERT INTO `mimo_security_authority` VALUES ('c09eee18553011e1badbc8b1208a5409', '创建模板', 'template:create');
INSERT INTO `mimo_security_authority` VALUES ('c847b24e553011e1badbc8b1208a5409', '编辑模板', 'template:edit');
INSERT INTO `mimo_security_authority` VALUES ('cf89ee4f553011e1badbc8b1208a5409', '删除模板', 'template:delete');
INSERT INTO `mimo_security_authority` VALUES ('ddc34a4e553011e1badbc8b1208a5409', '查看文章', 'article:list');
INSERT INTO `mimo_security_authority` VALUES ('deaaa3afda2711e18037b870f44a8925', '站点配置', 'conf:edit');
INSERT INTO `mimo_security_authority` VALUES ('e4788f2e561011e1badbc8b1208a5409', '查看文章评论', 'article-comment:list');
INSERT INTO `mimo_security_authority` VALUES ('e6f805e1553011e1badbc8b1208a5409', '创建文章', 'article:create');
INSERT INTO `mimo_security_authority` VALUES ('ed485c5d561011e1badbc8b1208a5409', '查看文章附件', 'article-attachment:list');
INSERT INTO `mimo_security_authority` VALUES ('f41f4602553011e1badbc8b1208a5409', '编辑文章', 'article:edit');

-- ----------------------------
-- Table structure for `mimo_security_role`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_security_role`;
CREATE TABLE `mimo_security_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `show_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_security_role
-- ----------------------------
INSERT INTO `mimo_security_role` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'admin', '管理员');
INSERT INTO `mimo_security_role` VALUES ('b1e377a15cfd11e195de360472309768', 'webmaster', '网站管理员');

-- ----------------------------
-- Table structure for `mimo_security_role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_security_role_authority`;
CREATE TABLE `mimo_security_role_authority` (
  `role_id` varchar(32) NOT NULL,
  `authority_id` varchar(32) DEFAULT NULL,
  KEY `role_id` (`role_id`) USING BTREE,
  KEY `authority_id` (`authority_id`) USING BTREE,
  CONSTRAINT `mimo_security_role_authority_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `mimo_security_role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `mimo_security_role_authority_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `mimo_security_authority` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_security_role_authority
-- ----------------------------
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '7e4bc13b553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '33fc3fae5cfb11e195de360472309768');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '44587c52558511e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '37da5484558511e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'a16df686553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '2a20751f553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'e6f805e1553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'c09eee18553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'ddc34a4e553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '5ab322b1558511e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'cf89ee4f553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'c847b24e553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '28129b5c561111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '6f6c2e22558511e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '031412c5553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '3c9ff420553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'b6d662d7553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '982f0461553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'a922a95a553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '4b7b01e3553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', '3164fb0adb8111e18b91b870f44a8925');
INSERT INTO `mimo_security_role_authority` VALUES ('b1e377a15cfd11e195de360472309768', 'f41f4602553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '33fc3fae5cfb11e195de360472309768');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '3c9ff420553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '7e4bc13b553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '3377ef58561111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '031412c5553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '4d189dde561111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '3164fb0adb8111e18b91b870f44a8925');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'ed485c5d561011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'f41f4602553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '5bc1994d553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'e4788f2e561011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'b6d662d7553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'a922a95a553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '28129b5c561111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '75a77701561111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'aafc75a6560a11e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '982f0461553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'c847b24e553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '6f6c2e22558511e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '37da5484558511e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'c09eee18553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'b4ed4707560a11e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '4b7b01e3553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '2a20751f553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'deaaa3afda2711e18037b870f44a8925');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '8875b634562d11e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '375b9291554011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '5ab322b1558511e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'ddc34a4e553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '69e85e5d553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '44587c52558511e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '0f37e4c8561111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'a16df686553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '2d771e86560a11e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'e6f805e1553011e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '7a9b21de553111e1badbc8b1208a5409');
INSERT INTO `mimo_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'cf89ee4f553011e1badbc8b1208a5409');

-- ----------------------------
-- Table structure for `mimo_security_user`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_security_user`;
CREATE TABLE `mimo_security_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `father_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  KEY `account_non_locked` (`account_non_locked`) USING BTREE,
  KEY `father_id` (`father_id`) USING BTREE,
  CONSTRAINT `mimo_security_user_ibfk_1` FOREIGN KEY (`father_id`) REFERENCES `mimo_security_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_security_user
-- ----------------------------
INSERT INTO `mimo_security_user` VALUES ('373b4947553c11e1badbc8b1208a5409', 'admin', 'WLhAhgDCPJ7JRilY7mr+9A==', '', '373b4947553c11e1badbc8b1208a5409');
INSERT INTO `mimo_security_user` VALUES ('a40d32eada4811e1a31cb870f44a8925', 'webmaster', 'WguXk5GW5jHqVI1XxR2xpQ==', '', null);

-- ----------------------------
-- Table structure for `mimo_security_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_security_user_role`;
CREATE TABLE `mimo_security_user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE,
  CONSTRAINT `mimo_security_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `mimo_security_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `mimo_security_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `mimo_security_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_security_user_role
-- ----------------------------
INSERT INTO `mimo_security_user_role` VALUES ('a40d32eada4811e1a31cb870f44a8925', 'b1e377a15cfd11e195de360472309768');
INSERT INTO `mimo_security_user_role` VALUES ('373b4947553c11e1badbc8b1208a5409', '10edd41d553a11e1badbc8b1208a5409');

-- ----------------------------
-- Table structure for `mimo_template`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_template`;
CREATE TABLE `mimo_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `encode` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_template
-- ----------------------------
