CREATE TABLE `user_tab` (
  `id` int(11) NOT NULL COMMENT '主键Id',
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名称',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1：正常，2：被删除',
  `addtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';

INSERT INTO `user_tab`(`id`, `name`, `status`, `addtime`) VALUES (1, 'hht', 1, now());