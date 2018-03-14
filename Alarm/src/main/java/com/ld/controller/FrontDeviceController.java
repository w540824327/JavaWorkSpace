package com.ld.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ld.model.FrontDevice;
import com.ld.model.Pagination;
import com.ld.service.FrontDeviceService;
import com.ld.util.StringUtils;

@Controller
@RequestMapping("/front")
public class FrontDeviceController {

	private Logger logger = LoggerFactory.getLogger(FrontDeviceController.class);
	
	@Autowired
	private FrontDeviceService fontDeviceService;
	
	@RequestMapping("/list")
	public String showFrontDevice(@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
		Pagination<FrontDevice> pagination = fontDeviceService.getFrontDevicePagination(pageNum);
		model.addAttribute("pagination", pagination);
		model.addAttribute("currentUrl", "/front/list");
		return "frontDevice";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String addFrontDevice(FrontDevice frontDevice) {
		String message = "success";
		if(frontDevice == null) {
			message = "error1";
		} else {
			if(StringUtils.isBlank(frontDevice.getFrontPhone())
					|| frontDevice.getBattery1() == null
					|| frontDevice.getBattery2() == null
					|| frontDevice.getBattery3() == null
					|| StringUtils.isBlank(frontDevice.getInstallPosition())
					|| frontDevice.getInstallTime() == null
					|| frontDevice.getCheckCycle() == null
					|| frontDevice.getStartCheckTime() == null) {
				message = "error2";
			} else {
				try {
					fontDeviceService.addFrontDevice(frontDevice);
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "error3";
				}
			}
		}
		return message;
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public FrontDevice getFrontDevice(@RequestParam("id") Long id,HttpSession session) {
		FrontDevice oldFrontDevice = fontDeviceService.getFrontDevice(id);
		session.setAttribute("oldFrontDevice", oldFrontDevice);
		return oldFrontDevice;
	}
	
	@RequestMapping("/detail")
	@ResponseBody
	public FrontDevice getFrontDeviceDetail(@RequestParam("id") Long id) {
		return fontDeviceService.getFrongDeviceDetail(id);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public String updataFrontDevice(FrontDevice frontDevice,HttpSession session) {
		String message = "success";
		if(frontDevice == null) {
			message = "error1";
		} else {
			if(frontDevice.getId() == null
					|| StringUtils.isBlank(frontDevice.getFrontPhone())
					|| StringUtils.isBlank(frontDevice.getInstallPosition())
					|| frontDevice.getInstallTime() == null
					|| frontDevice.getCheckCycle() == null) {
				message = "error2";
			} else {
				try {
					FrontDevice oldFrontDevice = (FrontDevice) session.getAttribute("oldFrontDevice");
					session.removeAttribute("sfrontDevice");
					fontDeviceService.updateFrontDevice(oldFrontDevice, frontDevice);
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
	public String deleteFrontDevice(@RequestParam("ids") String ids) {
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
					fontDeviceService.deleteFrontDevice(params);
				} catch (Exception e) {
					logger.error(e.getMessage());
					message = "failure";
				}
			}
		}
		return message;
	}
	
}
