package com.ld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ld.model.SmsInfo;

@Mapper
public interface SmsMapper {

	List<SmsInfo> listSmsInfo(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
	
	int deleteSmsInfo(@Param("smsId") Long smsId);
	
	long countSmsInfo();
	
	Long countSmsInfoNot1(@Param("frontPhone") String frontPhone);
	
	Long countSmsSuccess(@Param("frontPhone") String frontPhone);
	
	Long countTargetSms(@Param("targetPhone") String targetPhone);
	
}
