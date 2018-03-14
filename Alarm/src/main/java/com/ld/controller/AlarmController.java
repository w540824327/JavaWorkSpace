package com.ld.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ld.model.AlarmInfoCount;
import com.ld.model.AlarmRecord;
import com.ld.model.Pagination;
import com.ld.service.AlarmService;
import com.ld.util.StringUtils;

@Controller
@RequestMapping("/alarm")
public class AlarmController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private AlarmService alarmService;
	
	@RequestMapping("/count")
	public String showAlarmCount(@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
		Pagination<AlarmInfoCount> pagination = alarmService.getAlarmCountPagination(pageNum);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUrl", "/alarm/count");
		return "alarmCount";
	}
	
	@RequestMapping("/record")
	public String showAlarmRecord(@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
		Pagination<AlarmRecord> pagination = alarmService.getAlarmRecordPagination(pageNum);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUrl", "/alarm/record");
		return "alarmRecord";
	}
	
	@RequestMapping("/record/delete")
	@ResponseBody
	public String deleteAlarmRecord(@RequestParam("ids") String ids) {
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
						logger.error("delete alarmRecord exception where id is not long");
						continue;
					}
				}
				try {
					alarmService.deleteAlarmRecord(params);
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "failure";
				}
			}
		}
		return message;
	}
	
}
