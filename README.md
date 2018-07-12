MySimple Project 项目是一个简单，便捷的JAVA接口开发框架。主要相关技术包括：springboot,mybatis,mysql。 集成了接口文档自动化生成，数据库DAO层代码自动生成，接口访问日志查询，接口测试等功能。本项目中有个UserController的接口示例。如果有问题或建议，请联系 lnonameh@qq.com，后续逐步改进。

#使用方式
1,数据库配置：在application-dev.properties文件中设置数据库连接spring.datasource.url，用户名spring.datasource.username，密码spring.datasource.password三项
2,数据库初始化：在数据库中运行 initDB.sql脚本创建两张表。其中 user_test是用来做DEMO用的。sys_logs_request是记录接口访问日志表。
3,测试方式：直接运行  MysimpleStart.java主函数，访问http://localhost:8011/page/index.html可看到管理界面。
	在界面右上角点击"添加地址"，输入http://localhost:8011。用于配置访问接口的域名,保存后刷新界面。
	在界面左边的接口测试栏目中，接口名输入"/user/register"，接口参数输入{"account":"testUser","password":"123456","nickName":"hello"}