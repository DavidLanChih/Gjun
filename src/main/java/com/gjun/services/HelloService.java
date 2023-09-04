package com.gjun.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gjun.domain.HelloBean;



//POJO Plain Old Java Object 
//轉換成服務REST
@RestController
@RequestMapping("/service/v1")
public class HelloService {
	
	public HelloService() {
		System.out.println("Hello 服務就位...");
	}
	//打招呼 回應資料功能 描述端點 只支援GET 尚未決定回應的MIME Type
	@GetMapping(path="/hello/sayhello",produces= {"text/html"})
	public String helloWorld() {
		//回資料
		return "<font size='6' color='red'>Hello World</font>";
	}
	
	//打招呼功能 傳遞一個誰 採用path as Parameter
	@GetMapping(path="/hello/bean/{w}",produces={"application/json"})
	public HelloBean helloBean(@PathVariable(name="w",required=false)String who) {
		//建構JavaBean
		HelloBean bean=new HelloBean();
		bean.setWho(who);
		bean.setHelloString("您好 世界和平");
		return bean;
		
	}

}
