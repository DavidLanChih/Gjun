package com.gjun.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gjun.beans.Member;
import com.gjun.db.DbUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;




@Controller
@PropertySource(value= {"classpath:application.properties"}) //注入資源檔
public class MemberController {
	//Attribute 定義來自於資源的屬性值 @Value使用 Expression ${propertyName}
	@Value("${application.db.mysql.driverClassName}")
	private String driverClassName;
	@Value("${application.db.mysql.url}")
	private String url;
	@Value("${application.db.mysql.userName}")
	private String userName;
	@Value("${application.db.mysql.password}")
	private String password;
	
	//注入底層Servlet api HttpServletRequest--封裝前端所有資訊的Http底層
	@Autowired
	private HttpServletRequest request;
	
	
	//注入JdbcTemplate物件
	@Autowired
	@Qualifier("sakilaJdbc")
	private JdbcTemplate jdbcTemplate; //注入依賴物件DI 這一個物件必須同時與 DataSource互動 採用IoC注入控制反轉
	//註冊頁面 與註冊新增作業 Action
	//參數採用注入物件Injection
	@RequestMapping(path="/member/register",method= {RequestMethod.GET,RequestMethod.POST})
	public String registerForm(Model model,Member member) {
		
		System.out.println(member.getUserName());
		System.out.println(member);
		System.out.println(this.driverClassName);
		//訊息字串狀態
		String message=null;
		//如果注入物件具有userName表單postback
		if(member.getUserName()!=null) {
			//TODO 進行會員註冊
			//呼叫類別成員 Model 產生一個連接物件
			try {
				Connection connection=
						DbUtility.createConnection(this.driverClassName,this.url,this.userName,this.password);
				//一切正常往下走 需要命令物件 帶命令敘述 進行新增作業
				Statement st=connection.createStatement(); //連接物件產生命令物件 兩者有互動
				//產生新增SQL字串(SQL pass Through)
				String sql=String.format("Insert Into users(username,password,realname,email) values('%s','%s','%s','%s')",
						member.getUserName(),
						member.getPassword(),
						member.getRealName(),
						member.getEmail()
						);
				System.out.println(sql);
				//命令物件執行executeUpdte() insert/update/delete
				st.executeUpdate(sql);
				//執行成功
				//關閉連接
				connection.close();
				message="註冊成功";
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				//錯誤訊息狀態 通知使用者(View)
				message="註冊失敗!!可能使用者備使用了!!";
			}
			
			
		}
		//往表單調用 持續注入進來的Member物件
		model.addAttribute("users",member);
		model.addAttribute("message",message);
		return "register"; //調用頁面register.html
	}

	//登入作業  login
	@RequestMapping(path="/member/login",method= {RequestMethod.GET,RequestMethod.POST})
	public String login( Model model,HttpServletResponse response,Member member) {
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie c:cookies) {
				System.out.println("名稱:"+c.getName()+" 值:"+c.getValue());
			}
		}
		String message=null;
		//System.out.println(this.jdbcTemplate.getDataSource());
		//調用頁面
		//post之後查詢 驗證
		if(request.getMethod().equals("POST")) {
			
			//TODO 進行登入驗證
			//System.out.println(member.getUserName());
			//呼叫JdbcTemplate  method進行查詢 單筆
			try 
			{
				Member result=jdbcTemplate.queryForObject(
						"select username,password from users where username=? and password=?",
						//匿名類別 實作RowMapper介面抽象方法 callback 自訂程序
						new RowMapper<Member>() {
							//實作方法
							@Override
							public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
								//建構Member物件
								Member member=new Member();
								member.setUserName(rs.getString("username"));
								member.setPassword(rs.getString("password"));
								return member;
							}
					
						},
						//不確數量參數
						member.getUserName(),
						member.getPassword()
						);
				System.out.println(member.getUserName());
				//驗證成功 有這一個會員
				//1.發出憑證 到瀏覽器(Client) 建構Cookie物件
				Cookie cookie=new Cookie(".cred",member.getUserName());
				//設定Cookie不能程式偽造的 須經由瀏覽器http 才算有效
				cookie.setHttpOnly(true);
				//Cookie domain or path限制 會預設cookie path為/member
				cookie.setPath("/");
				//2.借助回應物件Response帶 Cookie到前端去
				response.addCookie(cookie);
				message=String.format("%s 驗證通過!!!",member.getUserName());
			
			}catch(DataAccessException ex) {
				//找不到資料
				System.out.println("驗證失敗");
				message=String.format("%s 驗證失敗!!!",member.getUserName());
			}
		
		}
		model.addAttribute("message",message);
		return "login";
	}
}
