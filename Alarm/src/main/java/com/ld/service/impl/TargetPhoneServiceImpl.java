package com.ld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.mapper.SmsMapper;
import com.ld.mapper.TargetPhoneMapper;
import com.ld.model.FrontAndTarget;
import com.ld.model.FrontDevice;
import com.ld.model.Pagination;
import com.ld.model.TargetPhone;
import com.ld.service.TargetPhoneService;
import com.ld.util.ClassUtils;

@Transactional
@Service
public class TargetPhoneServiceImpl implements TargetPhoneService {

	@Value("${pagination.page.size}")
	private Integer pageSize;
	
	@Autowired
	private TargetPhoneMapper targetPhoneMapper;
	
	@Autowired
	private SmsMapper smsMapper;
	
	@Transactional(readOnly = true)
	public Pagination<TargetPhone> getTargetPhonePagination(Integer pageNum) {
		List<TargetPhone> list = targetPhoneMapper.listTargetPhone((pageNum - 1) * pageSize, pageSize);
		for(int i = 0; i<list.size();i++) {
			Long countSms = smsMapper.countTargetSms(list.get(i).getTargetPhone());
			list.get(i).setSmsCount(countSms);
		}
		long count = targetPhoneMapper.countPhone();
		return new Pagination<TargetPhone>(list, count, pageSize, pageNum);
	}

	@Transactional(readOnly = true)
	public TargetPhone getTargetPhoneById(long id) {
		return targetPhoneMapper.getTartgetPhoneById(id);
	}

	public int addTargetPhone(TargetPhone targetPhone) {
		targetPhone.setRemainMoney(targetPhone.getPrepayMoney());
		targetPhone.setSmsCount(0l);
		return targetPhoneMapper.insertTargetPhone(targetPhone);
	}

	public int updateTargetPhone(TargetPhone oldTargetPhone, TargetPhone targetPhone) {
		TargetPhone newTargetPhone = ClassUtils.getDiffObject(oldTargetPhone, targetPhone);
		if(!ClassUtils.isNull(newTargetPhone)) {
			newTargetPhone.setId(oldTargetPhone.getId());
			if(newTargetPhone.getPrepayMoney() != null && newTargetPhone.getPrepayMoney() > 0.0f) { // 说明预付金额变化了。就是新增充值金额，然后给余额充值。要是没变化=0就是null,就什么也不用做
				newTargetPhone.setRemainMoney(newTargetPhone.getPrepayMoney() + oldTargetPhone.getRemainMoney());
			}
			return targetPhoneMapper.updateTargetPhone(newTargetPhone);
		}
		return 0;
	}

	public int deleteTargetPhone(Long[] ids) {
		int result = 0;
		if(ids != null) {
			for(int i = 0; i < ids.length; i++) {
				Long id = ids[i];
				if(id != null) {
					result += targetPhoneMapper.deleteTargetPhone(id);
				}
			}
		}
		return result;
	}
	
	public int deleteMyRefByFrontId(Long frontId) {
		return targetPhoneMapper.deleteMyRefByFrontId(frontId);
	}

	@Transactional(readOnly = true)
	public Pagination<FrontAndTarget> getMyRefPagination(Long targetId, Integer pageNum) {
		List<FrontAndTarget> list = targetPhoneMapper.listMyRef(targetId, (pageNum - 1) * pageSize, pageSize);
        long count = targetPhoneMapper.countMyRef(targetId);
		return new Pagination<FrontAndTarget>(list, count, pageSize, pageNum);
	}

	public int deleteMyRef(Long[] ids) {
		int result = 0;
		if(ids != null) {
			for(int i = 0; i < ids.length; i++) {
				Long id = ids[i];
				if(id != null) {
					System.out.println("haha" + id);
					result += targetPhoneMapper.deleteMyRef(id);
				}
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	public Pagination<FrontDevice> listWantRef(Long targetId, Integer pageNum) {
		List<FrontDevice> list = targetPhoneMapper.listWantRef(targetId, (pageNum - 1) * pageSize, pageSize);
		long count = targetPhoneMapper.countWantRef(targetId);
		return new Pagination<FrontDevice>(list, count, pageSize, pageNum);
	}

	public int addRef(Long frontId, Long targetId) {
		return targetPhoneMapper.insertRef(frontId, targetId);
	}

}
