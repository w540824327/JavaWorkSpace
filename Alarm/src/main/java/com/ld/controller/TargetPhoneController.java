package com.ld.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ld.model.FrontAndTarget;
import com.ld.model.FrontDevice;
import com.ld.model.Pagination;
import com.ld.model.TargetPhone;
import com.ld.service.TargetPhoneService;
import com.ld.util.StringUtils;

@Controller
public class TargetPhoneController {
	
	private Logger logger = LoggerFactory.getLogger(TargetPhoneController.class);
	
	@Autowired
	private TargetPhoneService targetPhoneService; 
	
	/**
	 * 目标手机展示列表
	 * 
	 * @param pageNum 当前页码
	 * @param modle 页面model
	 * @return
	 */
	@RequestMapping("/target/list")
	public String showTargetPhone(@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
		Pagination<TargetPhone> pagination = targetPhoneService.getTargetPhonePagination(pageNum);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUrl", "/target/list");
		return "targetPhone";
	}
	
	/**
	 * 目标手机添加
	 * 
	 * @param targetPhone 新增目标手机
	 * @return
	 */
	@RequestMapping("/target/add")
	@ResponseBody
	public String addTargetPhone(TargetPhone targetPhone){
		String message = "success";
		Float smsPrice = targetPhone.getSmsPrice();
		String regex1 = "\\d(\\.\\d{1,3})?";
		if(!Pattern.compile(regex1).matcher(Float.toString(smsPrice)).find()) {
			message = "error";
		}
		String regex2 = "\\d(\\.\\d{1,3})?";
		Float prepayMoney = targetPhone.getPrepayMoney();
		if(!Pattern.compile(regex2).matcher(Float.toString(prepayMoney)).find()) {
			message = "error";
		}
		if("success".equals(message)) {
			try{
				targetPhoneService.addTargetPhone(targetPhone);
			}catch (Exception e) {
				logger.error(e.getMessage());
				message = "failure";
			}
		}
		return message;
	}
	
	/**
	 * 获取一条目标手机
	 * 
	 * @param id 目标手机主键id
	 * @return
	 */
	@RequestMapping("/target/get")
	@ResponseBody
	public TargetPhone getTargetPhoneById(@RequestParam("id") Long id, HttpServletRequest  request) {
		if(id == null) {
			return null;
		}
		TargetPhone targetPhone = targetPhoneService.getTargetPhoneById(id);
		targetPhone.setPrepayMoney(0.0f);
		request.getSession().setAttribute("oldTargetPhone", targetPhone);
		return targetPhone;
	}
	
	/**
	 * 修改目标手机
	 * 
	 * @param targetPhone 修改后的目标手机对象
	 * @return
	 */
	@RequestMapping("/target/edit")
	@ResponseBody
	public String updateTargetPhone(TargetPhone targetPhone, HttpServletRequest request ) {
		String message = "success";
		try{
			 TargetPhone oldTargetPhone = (TargetPhone) request.getSession().getAttribute("oldTargetPhone");
			 targetPhoneService.updateTargetPhone(oldTargetPhone, targetPhone);
			 request.getSession().removeAttribute("oldTargetPhone");
		}catch (Exception e) {
			logger.error(e.getMessage());
			message = "failure";
			
		}
		return message;
	}
	
	/**
	 * 删除目标手机（一条/多条）
	 * 
	 * @param ids 由js数组拼接的字符
	 * @return
	 */
	@RequestMapping("/target/delete")
	@ResponseBody
	public String deleteTargetPhone(@RequestParam("ids") String ids) {
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
					if(!StringUtils.isBlank(idArr[i])) {
						try {
							Long id = Long.parseLong(idArr[i]);
							params[i] = id;
						} catch (NumberFormatException e) {
							logger.error("delete TargetPhone exception where id is null");
							continue;
						}
					}
				}
				try {
					targetPhoneService.deleteTargetPhone(params);
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "failure";
				}
			}
		}
		return message;
	}
	
	
	/**
	 * 我的关联列表
	 * 
	 * @param targetId 目标手机id
	 * @param pageNum 页码
	 * @param model 页面model
	 * @return
	 */
	@RequestMapping("/myref/list")
	public String showMyRefList(@RequestParam("id") Long targetId, 
			@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
		Pagination<FrontAndTarget> pagination = targetPhoneService.getMyRefPagination(targetId, pageNum);
		TargetPhone targetPhone = targetPhoneService.getTargetPhoneById(targetId);
		model.addAttribute("targetPhone", targetPhone);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUrl", "/target/list");
		return "myRefList";
	}
	
	/**
	 * 删除我的关联信息 （一条/多条）
	 * 
	 * @param ids 由js数组拼接的字符
	 * @return
	 */
	@RequestMapping("/myref/delete")
	@ResponseBody
	public String deleteMyRef(@RequestParam("ids") String ids) {
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
					if(!StringUtils.isBlank(idArr[i])) {
						try {
							Long id = Long.parseLong(idArr[i]);
							params[i] = id;
						} catch (NumberFormatException e) {
							logger.error("delete linkedFrontDevice exception where id is null");
							continue;
						}
					}
				}
				try {
					targetPhoneService.deleteMyRef(params); 
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "failure";
				}
			}
		}
		return message;
	}
	
	
	/**
	 * 我要关联列表 （还没有关联的前端设备信息列表）
	 * 
	 * @param targetId 目标手机主键id
	 * @param model 页面Model
	 * @return
	 */
	@RequestMapping("/wantref/list")
	public String showWantRefList(@RequestParam("id") Long targetId,
			@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
		Pagination<FrontDevice> pagination = targetPhoneService.listWantRef(targetId, pageNum);
		model.addAttribute("targetId", targetId);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUrl", "/target/list");
		return "wantRefList";
	}
	
	/**
	 * 添加关联
	 * 
	 * @param frontId  前端设备主键id
	 * @param targetId 目标手机主键id
	 * @return
	 */
	@RequestMapping("/wantref/add")
	@ResponseBody
	public String addRef(@RequestParam("frontId") Long frontId, @RequestParam("targetId") Long targetId, Model model) {
		String message = "success";
		try {
			targetPhoneService.addRef(frontId, targetId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			message = "failure";
		}
        model.addAttribute("targetId", targetId);
		return message;
	}
}
