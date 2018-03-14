package com.ld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.mapper.SmsMapper;
import com.ld.model.Pagination;
import com.ld.model.SmsInfo;
import com.ld.service.SmsService;

@Transactional
@Service
public class SmsServiceImpl implements SmsService {

	@Value("${pagination.page.size}")
	private Integer pageSize;
	
	@Autowired
	private SmsMapper smsMapper;
	
	@Transactional(readOnly = true)
	@Override
	public Pagination<SmsInfo> getSmsPagination(Integer pageNum) {
		List<SmsInfo> smsList = smsMapper.listSmsInfo((pageNum -1) * pageSize, pageSize);
		long recordCount = smsMapper.countSmsInfo();
		return new Pagination<>(smsList, recordCount, pageSize, pageNum);
	}

	public int deleteSmsInfo(Long[] ids) {
		int result = 0;
		if(ids != null && ids.length > 0) {
			for(int i = 0; i < ids.length; i++) {
				if(ids[i] != null) {
					result += smsMapper.deleteSmsInfo(ids[i]);
				}
			}
		}
		return result;
	}

}
