package com.gjun.domain;
//JavaBean as Entity類別
//打招呼訊息
public class HelloBean implements java.io.Serializable{
	//Attribute
	private String who;
	private String helloString;
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getHelloString() {
		return helloString;
	}
	public void setHelloString(String helloString) {
		this.helloString = helloString;
	}
	

}
