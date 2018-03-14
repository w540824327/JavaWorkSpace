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

import org.springframework.stereotype.Component;

import com.ld.model.User;
import com.ld.util.StringUtils;

@Component
public class PrivilegeFilter implements Filter {

	private List<String> urlList = new ArrayList<String>();
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		urlList.add("/add");
		urlList.add("/edit");
		urlList.add("/delete");
		urlList.add("/rspass");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String requestURI = request.getRequestURI();
		if(contains(requestURI)) {
			User user = (User) request.getSession().getAttribute("WEBSITE_USER");
			if(user == null) {
				request.getRequestDispatcher("/user/show-login").forward(req, resp);
			} else {
				if("0".equals(user.getUserType())) {
					if(requestURI.contains("/user/delete")) {
						String ids = request.getParameter("ids");
						if(containsUserId(ids, Long.toString(user.getId()))) {
							request.getRequestDispatcher("/privilege.html").forward(req, resp);
						} else {
							chain.doFilter(req, resp);
						}
					} else {
						chain.doFilter(req, resp);
					}
				} else {
					request.getRequestDispatcher("/privilege.html").forward(req, resp);
				}
			}
		} else {
			chain.doFilter(req, resp);
		}
	}
	
	@Override
	public void destroy() {
		urlList.clear();
	}
	
	private boolean contains(String url) {
		for(String str : urlList) {
			if(url.endsWith(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean containsUserId(String ids, String id) {
		boolean flag = false;
		if(!StringUtils.isBlank(ids)) {
			String[] idArr = ids.split(",");
			if(!StringUtils.isEmpty(idArr)) {
				for(String str : idArr) {
					if(!StringUtils.isBlank(str) && str.equals(id)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

}
