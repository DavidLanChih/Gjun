package com.gjun.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//MVC Model開發
//採用Jdbc資料操作共用功能
public class DbUtility {
	
	//傳遞連接字串(URL)與userName and Pasword 生產一個連接上資料庫的Connection物件
	//傳遞Driver採用meta data先載型別到記憶體去 適當時候產生個體物件
	public static Connection createConnection(String driverClassName,String url,String userName,String password) throws ClassNotFoundException,SQLException  
	{
		
		Connection connection=null;
		try {
			//1.載入Driver class中繼資料到記憶體去
			Class.forName(driverClassName);
			//2.透過DriverManager配合連接字串url與username/password 產生一個開啟連接上資料庫的連接物件
			connection=DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return connection;
		
	}

}
