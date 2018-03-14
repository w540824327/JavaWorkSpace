package com.ld.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.mapper.AlarmRecordMapper;
import com.ld.mapper.FrontDeviceMapper;
import com.ld.mapper.SmsMapper;
import com.ld.model.AlarmInfoCount;
import com.ld.model.AlarmRecord;
import com.ld.model.FrontDevice;
import com.ld.model.Pagination;
import com.ld.service.AlarmService;

@Transactional
@Service
public class AlarmServiceImpl implements AlarmService {

	@Value("${pagination.page.size}")
	private Integer pageSize;
	
	@Autowired
	private AlarmRecordMapper alarmRecordMapper;
	
	@Autowired
	private FrontDeviceMapper frontDeviceMapper;
	
	@Autowired
	private SmsMapper smsMapper;
	
	@Transactional(readOnly = true)
	public Pagination<AlarmRecord> getAlarmRecordPagination(int pageNum) {
		List<AlarmRecord> alarmRecordList = alarmRecordMapper.listAlarmRecord((pageNum - 1) * pageSize, pageSize);
		for(AlarmRecord alarmRecord: alarmRecordList) {
			if(alarmRecord.getAlarmType().equals("0")) {
				alarmRecord.setAlarmType("自检");
			} else if(alarmRecord.getAlarmType().equals("1")) {
				alarmRecord.setAlarmType("倾斜");
			} else if(alarmRecord.getAlarmType().equals("2")) {
				alarmRecord.setAlarmType("光纤");
			} else if(alarmRecord.getAlarmType().equals("3")) {
				alarmRecord.setAlarmType("倾斜+光纤");
			}
		}
		long recordCount = alarmRecordMapper.countAlarmRecord();
		return new Pagination<>(alarmRecordList, recordCount, pageSize, pageNum);
	}

	public int deleteAlarmRecord(Long[] ids) {
		int result = 0;
		if(ids != null && ids.length > 0) {
			for(int i = 0; i < ids.length; i++) {
				if(ids[i] != null) {
					result += alarmRecordMapper.deleteAlarmRecordById(ids[i]);
				}
			}
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public Pagination<AlarmInfoCount> getAlarmCountPagination(int pageNum) {
		List<FrontDevice> frontDeviceList = frontDeviceMapper.listFrontDevice((pageNum - 1) * pageSize, pageSize);
		List<AlarmInfoCount> alarmcountList = new ArrayList<AlarmInfoCount>();
		for(int i=0;i<frontDeviceList.size(); i++) {
			AlarmInfoCount alarmInfoCount = new AlarmInfoCount();
			Long alarmNum = alarmRecordMapper.countAlarmNum(frontDeviceList.get(i).getFrontPhone());
			alarmInfoCount.setFrontDevice(frontDeviceList.get(i));
			alarmInfoCount.setAlarmCount(alarmNum);
			Long smsCount = smsMapper.countSmsInfoNot1(frontDeviceList.get(i).getFrontPhone());
			alarmInfoCount.setSmsCount(smsCount);
			Long smsCountSuccess = smsMapper.countSmsSuccess(frontDeviceList.get(i).getFrontPhone());
			alarmInfoCount.setSmsCountSuccess(smsCountSuccess);
			Calendar calendarStart = Calendar.getInstance();
			Calendar calendarEnd = Calendar.getInstance();
			calendarStart.add(Calendar.MONTH, 0);
			calendarEnd.add(Calendar.MONTH, 1);
			calendarStart.set(Calendar.DAY_OF_MONTH, 1);
			calendarEnd.set(Calendar.DAY_OF_MONTH, 1);
			calendarStart.set(Calendar.HOUR_OF_DAY, 0);
			calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
			calendarStart.set(Calendar.SECOND,0);
			calendarEnd.set(Calendar.SECOND,0);
			calendarStart.set(Calendar.MINUTE,0);
			calendarEnd.set(Calendar.MINUTE,0);
			Date startDate = calendarStart.getTime();
			Date endDate = calendarEnd.getTime();
			Long checkCount = alarmRecordMapper.checkCount(frontDeviceList.get(i).getFrontPhone(), startDate, endDate);
			alarmInfoCount.setCheckCount(checkCount);
			alarmcountList.add(alarmInfoCount);
		}
		long count = frontDeviceMapper.countFrontDevice();
		return new Pagination<AlarmInfoCount>(alarmcountList,count,pageSize,pageNum);
	}

}
