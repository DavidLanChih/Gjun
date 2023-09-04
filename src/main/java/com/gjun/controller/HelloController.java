package com.gjun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gjun.domain.HelloProxy;

//無須繼承或者實作介面 繼承Object
//注入HelloProxy物件 操作打招呼回應內容
@Controller
public class HelloController {
	//Data Field注入--Attribute
	@Autowired
	private HelloProxy helloProxy;
	//自訂建構子 
	public HelloController() {
		//尚未注入依賴物件
		System.out.println("Hello控制器產生個體物件..."+helloProxy);
	}
	//使用方法(Action) 給前端瀏覽器進行請求 可以執行
	//需要端點EndPoint http://hosted/xxx/xxx/xxx
	//支援前端採用Http Request Method-GET
	//回應值 調用View有關
	@RequestMapping(path= {"/hello/helloworld"},method= {RequestMethod.GET})
	public ResponseEntity<String> helloWorld() {
		String content=helloProxy.sayHello();
		System.out.println("答案:"+content);
		//回應一個Http Response物件 可以封裝資訊(Entity)
		return  new ResponseEntity<String>(content,HttpStatus.OK);
	}
	//打招呼 回應一個頁面
	@RequestMapping(path="/hello/hellopage",method=RequestMethod.GET)
	public String hellowPage() {
		//調用(Dispatcher)一個頁面--延續原先的Request and Response生命週期到那一個頁面去
		return "hello"; //回給Thymeleaf engine調用樣板資料夾下的頁面 hello.html
	}
	
}
