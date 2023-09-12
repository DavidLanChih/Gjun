package com.gjun.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gjun.beans.Message;
import com.gjun.beans.NorCustomers;
//RESTful 服務
@RestController
@RequestMapping(path="/service/v1")
public class CustomerService {
	//Attribute
	@Autowired
	@Qualifier("norJdbc")  //for資料庫是northwind
	private JdbcTemplate jdbcTemplate;
	//採用QueryString ?country=UK 查詢相關客戶資料
	@GetMapping(path="/customers/qry/rawdata",produces= {"application/json"})
	public ResponseEntity<Object> customersQryByCountry(@RequestParam("country")String country) {
		//TODO 配合注入進來JdbcTemplate指向northwind進行客戶查詢
		//jdbcTemplate
		String db=null;
		List<NorCustomers> result=null;
		ResponseEntity<Object> response=null;
		try {
			db= jdbcTemplate.getDataSource().getConnection().getCatalog();
			
			//使用注入進來的JdbcTemplate進行多筆查詢
			var mapper=BeanPropertyRowMapper.newInstance(NorCustomers.class);
			result=jdbcTemplate.query("Select CustomerID,CompanyName,Address,Phone,Country From customers Where Country=?",
					 mapper,new Object[] {country});
			
			//找不到資料
			if(result.size()==0) {
				//查無該國家別客戶
				Message message=new Message();
				message.setCode(400);
				message.setMsg(String.format("國家別:%s 查無任何客戶",country));
				//進一步包裝 Response
				response=ResponseEntity.badRequest().body(message);
				
				
			}else {
				response=ResponseEntity.ok(result);
				System.out.println(result.iterator().next().getPhone());
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		
	}

	//修改相對客戶資料服務 RESTful PUT-修改
	@PutMapping(path="/customers/update/rawdata",produces="application/json",consumes="application/json")
	public Message customersUpdate(@RequestBody()NorCustomers customers) {
		//TODO 進行資料維護 
		String upate="update customers set CompanyName=?,Address=?,Phone=?,Country=? Where CustomerID=?";
		//使用JdbcTemplate
		Message message=new Message();
		try {
			int affect=jdbcTemplate.update(upate, 
				customers.getCompanyName(),
				customers.getAddress(),
				customers.getPhone(),
				customers.getCountry(),
				customers.getCustomerId());
			if(affect>0) {
				//更新一筆
				message.setCode(200);
				message.setMsg("更新客戶成功");
				
			}else {
				message.setCode(400);
				message.setMsg("沒有該客戶可更新");
			}
		}catch(DataAccessException ex) {
			message.setCode(400);
			message.setMsg("更新客戶失敗");
		}
		return message;
	}

	//刪除(Http request method-DELETE)相對客戶資料服務RESTful Key如何傳遞 採用Path 當作參數
	@DeleteMapping(path="/customers/delete/{id}/rawdata")
	public ResponseEntity<Message> customersDelete(@PathVariable("id") String customerId) {
		Message message=new Message();
		ResponseEntity<Message> response=null;
		//刪除敘述
		String sql="Delete from customers Where CustomerID=?"; //?使用參數
		try {
			int row=jdbcTemplate.update(sql,customerId);
			if(row==1) {
				message.setCode(200);
				message.setMsg("刪除成功");
			}else {
				message.setCode(200);
				message.setMsg("查無該客戶");
			}
			response=ResponseEntity.ok(message); //ok() http status code=200
			
		}catch(DataAccessException ex)
		{
			//刪除不成功
			System.out.println(ex.getMessage());
			message.setCode(400);
			message.setMsg("刪除失敗");
			//設定body 序列化JSON badRequest() http status code-400
			response=ResponseEntity.badRequest().body(message);
			
		}
		return response;
	}
}
