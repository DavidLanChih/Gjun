package com.gjun.components;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.gjun.domain.EngHello;
import com.gjun.domain.HelloProxy;
import com.gjun.domain.IHello;
import com.gjun.domain.TWHello;

//POJO(Plain Old Java Object)
//定義元件合約(@Component) 使用方法生產元件
@Component
public class HelloBeans {
	//建構子
	public HelloBeans() {
		System.out.println("HelloBean產生個體物件了...");
	}
	//負責生產元件到Spring引擎去(Factory)
	@Bean(name="twhello")
	public IHello helloBean() {
		System.out.println("HelloBean元件產生...");
		return new TWHello(); //產生Instance個體物件
	}
	@Bean(name="enghello")
	public IHello helloBean2() {
		System.out.println("HelloBean元件產生...");
		return new EngHello(); //產生Instance個體物件
	}
	//採用autowired 自動注入判斷 預設採用byType 改採用byName
	//生產代理的Hello 
	//方法的參數實踐注入控制反轉工程
	@Bean
	public HelloProxy helloProxy(IHello enghello) {
		System.out.println("HelloProxy元件產生...");
		//建構一個HelloProxy
		HelloProxy proxy=new HelloProxy();
		proxy.setHello(enghello);
		//偷偷呼喚一下 
		System.out.print(proxy.sayHello());
		return proxy;
	}

}
