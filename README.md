MySimple Project 项目是一个简单，便捷的JAVA接口开发框架。主要相关技术包括：springboot,mybatis,mysql。 集成了接口文档自动化生成，数据库DAO层代码自动生成，接口访问日志查询，接口测试等功能。本项目中有个UserController的接口示例。如果有问题或建议，请联系 lnonameh@qq.com，后续逐步改进。

#使用方式
1,数据库配置：在application-dev.properties文件中设置数据库连接spring.datasource.url，用户名spring.datasource.username，密码spring.datasource.password三项
2,数据库初始化：在数据库中运行 initDB.sql脚本创建两张表。其中 user_test是用来做DEMO用的。sys_logs_request是记录接口访问日志表。
3,测试方式：直接运行  MysimpleStart.java主函数，访问http://localhost:8011/page/index.html可看到管理界面。
	在界面右上角点击"添加地址"，输入http://localhost:8011。用于配置访问接口的域名,保存后刷新界面。
	在界面左边的接口测试栏目中，接口名输入"/user/register"，接口参数输入{"account":"testUser","password":"123456","nickName":"hello"} 可测试访问的接口。访问后，能在“日志查询”中看到访问日志。
4,数据库访问代码自动生成：在generatorConfig.xml文件中修改数据库连接和用户名密码。根据表名，设定要生成的相关表代码。参考该文件中的user_test表示例。在项目中以maven的方式运行 mybatis-generator:generate。即可生成mybatis的相关dao代码。
5,接口文档自动生成：在controller类中使用@ApiDescribe声明接口名称和说明。在入参和出参的DTO里面使用@DtoFiledDescribe设置参数说明和示例数据。参照UserController类和相关的DTO类。 在DemoCreateDoc类中设定生成文档的存放路径，和需要设成文档的接口。直接以main方式运行该类即可。
	
	