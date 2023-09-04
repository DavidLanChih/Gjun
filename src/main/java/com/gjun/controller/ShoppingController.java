package com.gjun.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gjun.beans.Shopping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path="/shopping")
@SessionAttributes({"cart"})
public class ShoppingController {
	
	//Session Test 參數注入 HttpSession
	@GetMapping("/sessiondemo")
	public String SessionDemo(HttpSession session) {
		//建構一個集合物件 具有順序性
		List<Shopping> cart=new java.util.ArrayList<>();
		//建構購物物件
		Shopping s1=new Shopping();
		s1.setProductId(1);
		s1.setProductName("泡麵");
		s1.setUnitprice(200);
		s1.setQuantity(10);
		
		Shopping s2=new Shopping();
		s2.setProductId(2);
		s2.setProductName("氣泡水");
		s2.setUnitprice(200);
		s2.setQuantity(10);
		
		Shopping s3=new Shopping();
		s3.setProductId(3);
		s3.setProductName("麵包");
		s3.setUnitprice(200);
		s3.setQuantity(10);
		
		//放到購物車List物件
		cart.add(s1);
		cart.add(s2);
		cart.add(s3);
		//讓Session參考購物車(List物件)
		session.setAttribute("cart",cart);
		
		
		
		System.out.println(session.getId());
		return "sessiondemo";
	}

	//show cart list呈現購物車 由後端狀態(list)轉換嵌入到頁面 為JavaScript array
	@GetMapping(path="/showcart")
	
	public String showCart() {
		return "showcart";
	}
	//注入進來的Session加入一個Spring Model
	@GetMapping(path="/cartadd")
	public String cartAdd(ModelMap model) {
		Shopping s2=new Shopping();
		s2.setProductId(4);
		s2.setProductName("冰淇淋");
		s2.setUnitprice(200);
		s2.setQuantity(10);
		model.addAttribute("cart",s2); //預設request scope
		return "showcart";
	}
}
