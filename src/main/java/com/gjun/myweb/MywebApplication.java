package com.gjun.myweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//配置應用系統啟動時 自動去掃描所有合約
@SpringBootApplication
@ComponentScan(basePackages= {"com.gjun"})
public class MywebApplication {

	//CLI 主程式
	public static void main(String[] args) {
		SpringApplication.run(MywebApplication.class, args);
	}

}
