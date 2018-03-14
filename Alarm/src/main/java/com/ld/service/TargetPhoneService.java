package com.ld.service;

import com.ld.model.FrontAndTarget;
import com.ld.model.FrontDevice;
import com.ld.model.Pagination;
import com.ld.model.TargetPhone;

/**
 * 目标手机service
 *
 * @version 1.0
 * @since 2018-1-26
 * @author zjl 1781187542@qq.com
 */
public interface TargetPhoneService {

	/**
	 * 获取目标手机的分页
	 * 
	 * @param pageNum 当前页码
	 * @return
	 */
	Pagination<TargetPhone> getTargetPhonePagination(Integer pageNum);
	
	/**
	 * 通过id查询一条目标手机信息
	 *  
	 * @param id 目标手机主键id
	 * @return
	 */
	TargetPhone getTargetPhoneById(long id);
	
	/**
	 * 添加目标手机
	 * 
	 * @param targetPhone 新增的目标手机
	 * @return
	 */
    int addTargetPhone(TargetPhone targetPhone);
    
    /**
     * 更新目标手机信息
     * 
     * @param oldTargetPhone
     * @param targetPhone
     * @return
     */
    int updateTargetPhone(TargetPhone oldTargetPhone, TargetPhone targetPhone);
    
    /**
     * 通过id删除一条目标手机
     * 
     * @param id 主键id
     * @return
     */
    int deleteTargetPhone(Long[] id);
    
   
    // 目标手机和前端设备已关联部分service
    
    /**
	 * 按"目标手机号"分页获取已关联的"前端设备"信息
	 * 
	 * @param targetId 目标手机的id
	 * @param pageSize  当前页码
	 * @return
	 */
	Pagination<FrontAndTarget> getMyRefPagination(Long targetId, Integer pageNum);
	
	/**
	 * 删除一条我的关联
	 * 
	 * @param targetPhoneId 目标手机的id
	 * @return
	 */
	int deleteMyRef(Long[] ids);
	
	/**
	 * 通过前端设备id删除一条我的关联
	 * 
	 * @param frontId 前端设备id
	 * @return
	 */
	int deleteMyRefByFrontId(Long frontId);
	
	/**
	 * 获取想要关联的列表
	 * 
	 * @param targetId 目标手机id
	 * @param PageNum 页码
	 * @return
	 */
	Pagination<FrontDevice> listWantRef(Long targetId, Integer pageNum);
	
	/**
	 * 添加关联
	 * 
	 * @param frontId 前端设备id
	 * @param targetId 目标手机id
	 * @return
	 */
	int addRef(Long frontId, Long targetId);
}
