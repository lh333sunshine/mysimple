package com.mysimple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mysimple.service.ConfigBean;

@EnableConfigurationProperties({ConfigBean.class})
@SpringBootApplication
@MapperScan("com.mysimple.dao.mapper")
public class MysimpleStart {

	public static void main(String[] args) {
		SpringApplication.run(MysimpleStart.class, args);
	}
}
