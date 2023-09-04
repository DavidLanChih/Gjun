package com.gjun.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.*;

public class UserValidFilter implements Filter{

	public UserValidFilter() {
		System.out.println("會員驗證攔截器被配置了...");
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
				FilterChain filterChain) throws ServletException, IOException {
				//Cookie 是實做到Http
				HttpServletRequest request=(HttpServletRequest)req;
				HttpServletResponse response=(HttpServletResponse)resp;
				Cookie[] cookies=request.getCookies();
				System.out.println(cookies);
				boolean r=false;
				if(cookies!=null) {
					//掃描所有Cookie是否有一個名稱.cred
					for(Cookie cookie:cookies) {
						if(cookie.getName().equals(".cred")) {
							r=true;
							break;
						}
					}
				}
				if(r) {
					//合法
					System.out.println("合法");
					filterChain.doFilter(request, response);
				}else {
					//非法
					System.out.println("非法");
					response.sendError(401); //Http status code 401
					
					
					
				}
			
		}
		
		
		
		
	

}
