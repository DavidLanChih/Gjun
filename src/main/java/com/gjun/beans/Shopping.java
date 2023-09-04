package com.gjun.beans;

import java.util.UUID;

//JavaBean 購物資料
public class Shopping implements java.io.Serializable{
	
	//Attribute
	private int productId;
	private String productName;
	private double unitprice; //單價
	private int quantity; //購物量
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

}
