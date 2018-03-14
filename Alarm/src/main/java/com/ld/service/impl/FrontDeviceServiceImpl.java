package com.ld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.mapper.FrontDeviceMapper;
import com.ld.mapper.TargetPhoneMapper;
import com.ld.model.FrontDevice;
import com.ld.model.Pagination;
import com.ld.service.FrontDeviceService;
import com.ld.util.ClassUtils;

@Transactional
@Service
public class FrontDeviceServiceImpl implements FrontDeviceService {

	@Value("${pagination.page.size}")
	private Integer pageSize;
	
	@Autowired
	private FrontDeviceMapper frontDeviceMapper;
	
	@Autowired
	private TargetPhoneMapper targetPhoneMapper;

	@Transactional(readOnly = true)
	public Pagination<FrontDevice> getFrontDevicePagination(Integer pageNum) {
		List<FrontDevice> recordList = frontDeviceMapper.listFrontDevice((pageNum - 1) * pageSize, pageSize);
		long recordCount = frontDeviceMapper.countFrontDevice();
		return new Pagination<>(recordList, recordCount, pageSize, pageNum);
	}

	public int updateFrontDevice(FrontDevice oldFrontDevice, FrontDevice frontDevice) {
		FrontDevice diffObject = ClassUtils.getDiffObject(oldFrontDevice, frontDevice);
		if(!ClassUtils.isNull(diffObject)) {
			diffObject.setId(oldFrontDevice.getId());
			return frontDeviceMapper.updateFrontDevice(diffObject);	
		}
		return 0;
	}
	
	@Transactional(readOnly = true)
	public FrontDevice getFrontDevice(Long id) {
		return frontDeviceMapper.getFrontDevice(id);
	}

	public int addFrontDevice(FrontDevice frontDevice) {
		frontDevice.setEndCheckTime(frontDevice.getStartCheckTime()); // 初始化的时候将自动初始化最后自检时间
		return frontDeviceMapper.insertFrontDevice(frontDevice);
	}

	public int deleteFrontDevice(Long[] ids) {
		int result = 0;
		if(ids != null) {
			for(int i = 0; i < ids.length; i++) {
				if(ids[i] != null) {
					result += frontDeviceMapper.deleteFrontDevice(ids[i]); // 删除前端设备的记录
					targetPhoneMapper.deleteMyRefByFrontId(ids[i]); // 删除目标手机-前端设备的关联记录
				}
			}
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public FrontDevice getFrongDeviceDetail(Long id) {
		return frontDeviceMapper.getFrontDeviceDetail(id);
	}
	
}
