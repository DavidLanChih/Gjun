package com.gjun.beans;
//JavaBean as Entity class
public class Member implements java.io.Serializable {
	//封裝欄位Data Field
	private String userName;
	private String password;
	private String realName;
	private String email;
	//沒有寫 編譯產生預設建構子 空參數
	//Property as setUserName() and getUserName()採用=操作  userName
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
