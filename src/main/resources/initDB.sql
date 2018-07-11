CREATE TABLE `user_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `nick_name` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `sys_logs_request` (
  `req_id` varchar(34) COLLATE utf8mb4_bin NOT NULL,
  `api` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `return_code` int(11) NOT NULL,
  `ip` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `run_time` int(11) DEFAULT NULL,
  `request_len` int(11) DEFAULT NULL,
  `response_len` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `app_key` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `request_params` text COLLATE utf8mb4_bin,
  `response_content` text COLLATE utf8mb4_bin,
  `error_trace` text COLLATE utf8mb4_bin,
  PRIMARY KEY (`req_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

