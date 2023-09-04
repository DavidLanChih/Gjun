package com.gjun.beans;
//JavaBean as Entity class(一個物件一筆資料)
//JavaBean 封裝欄位 存取子setter and getter 空參數建構子 /實作序列化界面
public class Customer implements java.io.Serializable{
	//封裝欄位
	private String customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String country;
	
	//產生setter and getter as Property
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	

}
