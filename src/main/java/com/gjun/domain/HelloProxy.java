package com.gjun.domain;
//代理IHello(哪一個實體,取最新版的Hello)
public class HelloProxy {
	private String companyName="巨匠";
	private IHello hello;
	//自訂建構子
	public HelloProxy() {
		
	}
	//設定公司行號
	public void setCompanyName(String companyName)
	{
		this.companyName=companyName;
	}
	//注入真正打招呼的功能(注入依賴物件 Dependency)
	public void setHello(IHello hello) {
		this.hello=hello;
	}
	public String sayHello() {
		return hello.sayHello(companyName);
	}

}
