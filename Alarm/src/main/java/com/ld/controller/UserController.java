package com.ld.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ld.model.Pagination;
import com.ld.model.User;
import com.ld.service.UserService;
import com.ld.util.CheckNumUtil;
import com.ld.util.MD5Util;
import com.ld.util.StringUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Value("${reset.password}")
	private String resetPassword;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/show-login")
	public String showUserLogin() {
		return "userLogin";
	}
	
	@RequestMapping("/list")
	public String showUserInfo(@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
		Pagination<User> pagination = userService.getUserPagination(pageNum);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUrl", "/user/list");
		return "userInfo";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(User user, String checkCode, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String checkNum = (String) session.getAttribute("LOGIN_CHECK_CODE");
		if(StringUtils.isBlank(checkCode) || user == null || 
				StringUtils.isBlank(user.getUsername()) || 
				StringUtils.isBlank(user.getPassword()) || 
				StringUtils.isBlank(user.getUserType())) {
			return "error1";
		}
		if(checkNum != null && !checkNum.equals(checkCode)) {
			return "error2";
		}
		User u = userService.login(user);
		if(u == null) {
			return "error3";
		} else {
			session.setAttribute("WEBSITE_USER", u);
		}
		return "success";
	}
	
	@RequestMapping("/editpass")
	@ResponseBody
	public String editPassword(String oldPass, String newPass, String confirmPass, HttpServletRequest request) {
		String message = "success";
		if(StringUtils.isBlank(oldPass) || StringUtils.isBlank(newPass) 
				|| StringUtils.isBlank(confirmPass)) {
			message = "error1";
		} else if(!newPass.equals(confirmPass)){
			message = "error2";
		} else {
			User user = (User) request.getSession().getAttribute("WEBSITE_USER");
			if(user != null && !MD5Util.md5(oldPass).equals(user.getPassword())) {
				message = "error3";
			} else {
				try {
					userService.editPassword(user.getId(), newPass);
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "error4";
				}
			}
		}
		return message;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String addUser(User user) {
		String message = "success";
		if(user == null) {
			message = "error1";
		} else {
			if(StringUtils.isBlank(user.getUsername()) 
					|| StringUtils.isBlank(user.getPassword())
					|| StringUtils.isBlank(user.getUserType())) {
				message = "error2";
			} else {
				try {
					user.setUsername(user.getUsername().trim());
					userService.addUser(user);
				} catch (Exception e) {
					message = "error3";
				}
			}
		}
		return message;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public String editUser(User user, HttpSession session) {
		String message = "success";
		if(user == null) {
			message = "error1";
		} else {
			if(StringUtils.isBlank(user.getUsername()) 
					|| StringUtils.isBlank(user.getUserType())) {
				message = "error2";
			} else {
				try {
					user.setUsername(user.getUsername().trim());
					User oldUser = (User) session.getAttribute("oldUser");
					session.removeAttribute("oldUser");
					userService.updateUser(oldUser,user);
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "error3";
				}
			}
		}
		return message;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteUser(@RequestParam("ids") String ids, HttpServletRequest request) {
		String message = "success";
		if(StringUtils.isBlank(ids)) {
			message = "failure";
		} else {
			String[] idArr = ids.split(",");
			if(StringUtils.isEmpty(idArr)) {
				message = "failure";
			} else {
				Long[] params = new Long[idArr.length];
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("WEBSITE_USER");
				boolean flag = false;
				for(int i = 0; i < idArr.length; i++) {
					if(!StringUtils.isBlank(idArr[i])) {
						try {
							Long id = Long.parseLong(idArr[i]);
							params[i] = id;
							if(user != null && user.getId().equals(id)) {
								flag = true;
							}
						} catch (NumberFormatException e) {
							logger.error("delete user exception where id is not long");
							continue;
						}
					}
				}
				try {
					userService.deleteUser(params);
					if(flag) {
						session.removeAttribute("WEBSITE_USER");
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "failure";
				}
			}
		}
		return message;
	}
	
	@RequestMapping("/rspass")
	@ResponseBody
	public String resetPassword(@RequestParam("id") Long userId, HttpServletRequest request) {
		String message = resetPassword;
		User user = userService.getUserById(userId);
		if(user == null) {
			message = "failure";
		} else {
			userService.editPassword(userId, resetPassword);
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("WEBSITE_USER");
			if(u != null && u.getId().equals(userId)) {// 如果是当前登录用户,则更新session
				u.setPassword(MD5Util.md5(resetPassword));
				session.setAttribute("WEBSITE_USER", u);
			}
		}
		return message;
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public User getUserById(@RequestParam("id") Long id, HttpSession session) {
		if(id == null) {
			return null;
		}
		User oldUser = userService.getUserById(id);
		session.setAttribute("oldUser", oldUser);
		return oldUser;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("WEBSITE_USER");
		return "userLogin";
	}
	
	@RequestMapping("/checkcode")
	public String getCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		BufferedImage bufImg = CheckNumUtil.createCheckNumImg();
		String checkNum = CheckNumUtil.getCheckNum();
		session.setAttribute("LOGIN_CHECK_CODE", checkNum);
		ImageIO.write(bufImg, "png", response.getOutputStream());
		return null;
	}
}
