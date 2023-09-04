package com.gjun.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.gjun.filter.UserValidFilter;

import jakarta.servlet.Filter;

//註冊應用系統環境元件Spring Bean
@PropertySource(value= {"classpath:application.properties"})
@Configuration
public class AppInitConfig {
	@Value("${application.security.path}")
	private String path;
	
	//經由方法生產Spring Bean
	@Bean
	public FilterRegistrationBean<Filter> registerFilter() {
		//切割path為字串陣列
		String[] paths=path.split(",");
		FilterRegistrationBean<Filter> filterRegister=new FilterRegistrationBean<>();
		filterRegister.setFilter(new UserValidFilter());
		//何時升起這一個攔截器??
		filterRegister.addUrlPatterns(paths);
		//內部設定一個Filter Name
		filterRegister.setName("userValid");
		return filterRegister;
	}

}
