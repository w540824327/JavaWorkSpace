package com.ld.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.ld.model.User;

@Component
public class LoginFilter implements Filter{

	private List<String> urlList = new ArrayList<String>();
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		urlList.add("/user/show-login");
		urlList.add("/user/login");
		urlList.add("/user/checkcode");
		urlList.add(".js");
		urlList.add(".css");
		urlList.add(".jpg");
		urlList.add(".gif");
		urlList.add(".png");
		urlList.add(".jpeg");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String requestURI = request.getRequestURI();
		if(contains(requestURI)) {
			chain.doFilter(req, resp);
			return;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("WEBSITE_USER");
		if(user == null) {
			request.getRequestDispatcher("/user/show-login").forward(req, resp);
		} else {
			chain.doFilter(req, resp);
		}
	}
	
	@Override
	public void destroy() {
		
	}
	
	private boolean contains(String url) {
		for(String str : urlList) {
			if(url.contains(str)) {
				return true;
			}
			if(url.toLowerCase().endsWith(str)) {
				return true;
			}
		}
		return false;
	}

}
