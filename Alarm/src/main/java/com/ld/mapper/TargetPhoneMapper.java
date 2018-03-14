package com.ld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ld.model.FrontAndTarget;
import com.ld.model.FrontDevice;
import com.ld.model.TargetPhone;

/**
 * 目标手机Mapper
 * 
 * @version 1.0
 * @since 2018-1-26
 * @author zjl 1781187642@qq.com
 */
@Mapper
public interface TargetPhoneMapper {

	/**
	 * 获取目标手机列表（分页）
	 * 
	 * @param pageNum 当前页码
	 * @param pageSize 没页大小
	 * @return
	 */
	List<TargetPhone> listTargetPhone(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
	
	/**
	 * 统计目标手机条数用于分页
	 * 
	 * @return
	 */
	long countPhone();
	
	/**
	 * 通过id获取一条目标手机信息
	 * 
	 * @param id 主键id
	 * @return
	 */
	TargetPhone getTartgetPhoneById(@Param("id") Long id);
	
	/**
	 * 新增目标手机 
	 * 
	 * @param targetPhone 新增的目标手机
	 * @return 返回受影响的行数
	 */
	int insertTargetPhone(@Param("phone") TargetPhone targetPhone);
	
	/**
	 * 更新目标手机
	 * 
	 * @param targetPhone 修改后的目标手机对象
	 * @return 返回受影响的行数
	 */
	int updateTargetPhone(@Param("phone") TargetPhone targetPhone);
	
	/**
	 * 删除目标手机
	 * 
	 * @param id 目标手机id
	 * @return 返回受影响的行数
	 */
	int deleteTargetPhone(@Param("id") Long id);
	
	
	// 我的关联(目标手机和前端设备关联)
	
	
	/**
	 * 我的关联
	 * 
	 * @param targetPhoneId 目标手机的id
	 * @param pageNum 分页的当前页
	 * @param pageSize 分页的每页大小
	 * @return 
	 */
	List<FrontAndTarget> listMyRef(@Param("targetId") Long targetId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
	
	/**
	 * 查询总条数用于分页查询
	 * 
	 * @return
	 */
	long countMyRef(@Param("targetId") Long targetId);
	
	/**
	 * 删除一条我的关联记录
	 * 
	 * @param id 主键id
	 * @return
	 */
	int deleteMyRef(@Param("id") Long id);
	
	/**
	 * 通过前端设备id删除关联记录
	 * 
	 * @param frontId 前端设备id
	 * @return
	 */
	int deleteMyRefByFrontId(@Param ("frontId") Long frontId);
	
	
	// 我要关联
	
	/**
	 * 列出还能关联的前端设备
	 * 
	 * @param targetId 目标手机id
	 * @Param pageNum 当前页码
	 * @Param pageSize 每页大小
	 * @return
	 */
	List<FrontDevice> listWantRef(@Param("targetId") Long targetId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
	
	/**
	 * 查出所有可关联前端设备用于分页
	 * @return
	 */
	long countWantRef(@Param("targetId") Long targetId);
	
	/**
	 * 添加关联
	 * 
	 * @param frontId 前端设备号
	 * @param targetId 目标手机号
	 * @return
	 */
	int insertRef(@Param("frontId") Long frontId, @Param("targetId") Long targetId);
}
