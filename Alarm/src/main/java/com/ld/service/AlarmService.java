package com.ld.service;

import com.ld.model.AlarmInfoCount;
import com.ld.model.AlarmRecord;
import com.ld.model.Pagination;

public interface AlarmService {

	Pagination<AlarmRecord> getAlarmRecordPagination(int pageNum);
	
	int deleteAlarmRecord(Long[] ids);
	
	Pagination<AlarmInfoCount> getAlarmCountPagination(int pageNum);
	
}
