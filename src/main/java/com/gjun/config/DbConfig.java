package com.gjun.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

//配置跟資料存取有關模組Spring bean元件
//定義類別為一個spring組態類別
@Configuration
@PropertySource(value= {"classpath:application.properties"}) 
public class DbConfig {
	//Data Field ${} spring EL(Expression Language)
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;
	
	//For northwind
	//Data Field ${} spring EL(Expression Language)
	@Value("${spring.datasource.url2}")
	private String norUrl;
	@Value("${spring.datasource.username2}")
	private String norUserName;
	@Value("${spring.datasource.password2}")
	private String norPassword;
	//透過方法生產Spring Bean元件 注入到網站系統容器中
	//自訂建構子
	public DbConfig() {
		System.out.println("資料庫組態類別產生了...");
	}

	@Bean
	@Qualifier("sakilads") 
	@Scope("singleton") //定義Spring Bean生命週期是獨一 就是共用的物件
	@Order(1)
	public DataSource createDataSource() {
		System.out.println("DataSource  Spring Bean產生了...");
	
		//建構MySQL DataSource物件(就是連接物件的工廠)
		MysqlDataSource datasource=new MysqlDataSource();
		//配置DataSource屬性(driverclassname/url/username/password)
		datasource.setUrl(url); //Property Injection 屬性注入
		datasource.setUser(userName);
		datasource.setPassword(password);
		System.out.println("DataSource.."+datasource.getUrl());
		//無須設定Driver Class 使用同一個api內建Driver
		return datasource;
		
	}
	@Bean
	@Qualifier("nords") //自訂合格名稱(適時選用)
	//明確配置另一個資料庫DataSource物件
	public DataSource createNorDB() {
		MysqlDataSource datasource=new MysqlDataSource();
		datasource.setUrl(norUrl); //Property Injection 屬性注入
		datasource.setUser(norUserName);
		datasource.setPassword(norPassword);
		return datasource;
	}
	//生產一個JdbcTemplate Bean
	@Bean
	@Order(2)
	@Qualifier("sakilaJdbc")
	public JdbcTemplate createJdbcTemplate(@Qualifier("sakilads")DataSource datasource) {
		System.out.println("Spring JdbcTemplate產生了..."+datasource);
		//建構一個個體物件
		JdbcTemplate jdbc=new JdbcTemplate();
		//與DataSource 連接工廠物件互動
		jdbc.setDataSource(datasource);
		return jdbc;
		
	}
	
	//生產一個JdbcTemplate Bean
		@Bean
		@Order(3)
		@Qualifier("norJdbc")
		public JdbcTemplate createNorthwindJdbcTemplate(@Qualifier("nords")DataSource datasource) {
			System.out.println("northwind Spring JdbcTemplate產生了..."+datasource);
			//建構一個個體物件
			JdbcTemplate jdbc=new JdbcTemplate();
			//與DataSource 連接工廠物件互動
			jdbc.setDataSource(datasource);
			return jdbc;
			
		}
}
