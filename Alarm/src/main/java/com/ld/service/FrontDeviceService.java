package com.ld.service;

import com.ld.model.FrontDevice;
import com.ld.model.Pagination;

public interface FrontDeviceService {

	Pagination<FrontDevice> getFrontDevicePagination(Integer pageNum);
	
	int updateFrontDevice(FrontDevice oldFrontDevice,FrontDevice frontDevice);
	
	int deleteFrontDevice(Long[] ids);
	
	FrontDevice getFrontDevice(Long id);
	
	int addFrontDevice(FrontDevice frontDevice);
	
	FrontDevice getFrongDeviceDetail(Long id);
}
