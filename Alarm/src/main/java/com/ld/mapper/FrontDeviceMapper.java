package com.ld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ld.model.FrontDevice;

@Mapper
public interface FrontDeviceMapper {

	List<FrontDevice> listFrontDevice(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
	
	long countFrontDevice();
	
	int insertFrontDevice(@Param("frontDevice") FrontDevice frontDevice);
	
	int updateFrontDevice(@Param("frontDevice") FrontDevice frontDevice);
	
	int deleteFrontDevice(@Param("id") Long id);
	
	FrontDevice getFrontDevice(@Param("id") Long id);
	
	FrontDevice getFrontDeviceDetail(Long id);
}
