package com.ld.service;

import com.ld.model.Pagination;
import com.ld.model.SmsInfo;

public interface SmsService {

	Pagination<SmsInfo> getSmsPagination(Integer pageNum);
	
	int deleteSmsInfo(Long[] ids);
}
