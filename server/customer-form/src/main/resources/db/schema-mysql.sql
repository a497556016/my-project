CREATE TABLE `core_extend_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module_name` varchar(64) NOT NULL COMMENT '模块名称',
  `table_name` varchar(64) NOT NULL COMMENT '表名称',
  `table_desc` varchar(500) DEFAULT NULL COMMENT '表描述',
  `creater` varchar(64) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(64) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `module_name` (`module_name`,`table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='测试扩展业务表';

CREATE TABLE `core_extend_table_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `table_id` int(11) DEFAULT NULL COMMENT '表ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `type` varchar(255) DEFAULT NULL COMMENT '字段类型',
  `length` int(11) DEFAULT NULL COMMENT '长度',
  `nullable` bit(1) DEFAULT b'1' COMMENT '是否允许为空',
  `default_value` varchar(255) DEFAULT NULL COMMENT '默认值',
  `comment` varchar(500) DEFAULT NULL COMMENT '字段描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;