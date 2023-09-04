package com.gjun.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gjun.beans.Customer;

import jakarta.servlet.http.HttpServletRequest;

//客戶資料處理控制器
@PropertySource(value= {"classpath:application.properties"})
@Controller 
public class CustomerController {
	//屬性注入
	@Value("${application.service.customersqrycountry}")
	private String customersQryURL;
	@Value("${application.service.customersupdate}")
	private String customersUpdateURL;
	//透過屬性注入DataSource 認定type
	@Autowired
	@Qualifier("sakilads")  //使用這一個合格名稱注入 相對DataSource(sakila資料庫) 
	private DataSource datasource;
	//注入Java Web底層的Servlet API 直接使用注入依賴物件
	@Autowired
	private HttpServletRequest request;
	@Autowired
	@Qualifier("norJdbc")
	private JdbcTemplate jdbcTemplate;
	
	//設定Action 提供查詢國家別頁面與輸出結果
	//描述對外的端點URL 跟請求方法 同時設定GET與POST
	@RequestMapping(path="/customers/qry/country",method= {RequestMethod.GET,RequestMethod.POST})
	public String customersQryForCountry(Model model,String country) {
		//使用HttpServletRequest持續狀態
		request.setAttribute("coun", country);
		//判斷country是否有輸出 再來進行查詢控制程序 可能有安全性問題
		//改採用傳送方法來問 Request Method-GET or POST
		String method=request.getMethod();
		System.out.println("傳送方法:"+method);
		List<Customer> result=null;
		if(method.equals("POST")) {
			//進行查詢
			//1.透過注入連接工廠DataSource 問出一個Connection物件
			Connection connection;
			
			try {
				//1.透過注入連接工廠DataSource 問出一個Connection物件(同時開啟連接)
				connection = datasource.getConnection();
				if(!connection.isClosed()) {
					//採用參數架構 防範SQL Injection
					String sql="Select customer_id,first_name,last_name,email,country From vwcustomerdetails where country=?";
					//透過連接拖出來那一個PreparedStatement物件
					PreparedStatement st=connection.prepareStatement(sql);
					//下參數(參數順序從1開始)
					st.setString(1,country);
					//執行查詢 拖出來管理查詢結果的ResutSet物件(線上查詢Fetching)
					ResultSet rs=st.executeQuery();
					//必須保持連線 逐筆截取下來 Fetching--線上讀取資料(逐筆讀取)--產生離線物件模組Model
					//建構集合物件
					//介面 介面多型化<Model-JavaBean> 建構實作該介面類別
					result=new ArrayList<Customer>();
					//逐筆讀取 將資料封裝到JavaBean去
					while(rs.next()) {
						//建構Javabean物件
						Customer customer=new Customer();
						//封裝欄位
						customer.setCustomerId(rs.getString("customer_id"));
						customer.setFirstName(rs.getString("first_name"));
						customer.setLastName(rs.getString("last_name"));
						customer.setEmail(rs.getString("email"));
						customer.setCountry(rs.getString("country"));
						//讓集合參考
						result.add(customer);
					}
					//逐筆讀取下來 封裝成前端離線物件模組(集合收集 List<javabean>
					System.out.println("記錄數:"+result.size());
					//關閉連接
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("連接資料錯誤....");
			}
			
		}
		//持續查詢狀態
		System.out.println(model.getAttribute("coun"));
		model.addAttribute("country",country);
		model.addAttribute("result",result);
		return "custqrycountry"; //使用thymeleaf 樣板引擎 調用的View Page名稱 
	}

	//進行客戶資料查詢northwind 採用前端ajax方式配合服務
	//注入Model持續狀態到頁面去渲染 
	@GetMapping(path="/customers/northwind/qry")
	public String norCustomersQryByCountry(Model model) {
		//參照出組態配置 vue framework/axios framework URL
		//調用表單
		model.addAttribute("query",customersQryURL);
		model.addAttribute("update",customersUpdateURL);
		return "norcustomersqry";
	}
	
}
