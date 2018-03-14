package com.ld.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ld.model.AlarmRecord;

@Mapper
public interface AlarmRecordMapper {

	List<AlarmRecord> listAlarmRecord(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
	
	long countAlarmRecord();
	
	int deleteAlarmRecordById(@Param("id") Long id);
	
	Long countAlarmNum(@Param("frontPhone") String frontPhone);
	
	Long checkCount(@Param("frontPhone") String frontPhone,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
}
