package com.ld.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ld.model.Pagination;
import com.ld.model.SmsInfo;
import com.ld.service.SmsService;
import com.ld.util.StringUtils;

@Controller
@RequestMapping("/sms")
public class SmsController {

	private Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	private SmsService smsService;
	
	@RequestMapping("/list")
	public String showSmsInfo(@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
		Pagination<SmsInfo> pagination = smsService.getSmsPagination(pageNum);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUrl", "/sms/list");
		return "smsInfo";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteSmsInfo(@RequestParam("ids") String ids) {
		String message = "success";
		if(StringUtils.isBlank(ids)) {
			message = "failure";
		} else {
			String[] idArr = ids.split(",");
			if(StringUtils.isEmpty(idArr)) {
				message = "failure";
			} else {
				Long[] params = new Long[idArr.length];
				for(int i = 0; i < idArr.length; i++) {
					try {
						Long id = Long.parseLong(idArr[i]);
						params[i] = id;
					} catch (NumberFormatException e) {
						logger.error("delete record exception where id is not long");
						continue;
					}
				}
				try {
					smsService.deleteSmsInfo(params);
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "failure";
				}
			}
		}
		return message;
	}
}
