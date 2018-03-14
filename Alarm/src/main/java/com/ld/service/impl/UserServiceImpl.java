package com.ld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.mapper.UserMapper;
import com.ld.model.Pagination;
import com.ld.model.User;
import com.ld.service.UserService;
import com.ld.util.ClassUtils;
import com.ld.util.MD5Util;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Value("${pagination.page.size}")
	private Integer pageSize;
	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional(readOnly = true)
	@Override
	public User login(User user) {
		String password = MD5Util.md5(user.getPassword());
		user.setPassword(password);
		return userMapper.getUser(user);
	}

	@Override
	public int addUser(User user) {
		String password = user.getPassword();
		password = MD5Util.md5(password);
		user.setPassword(password);
		return userMapper.insertUser(user);
	}

	@Transactional(readOnly = true)
	@Override
	public Pagination<User> getUserPagination(int pageNum) {
		List<User> userList = userMapper.listUser((pageNum - 1) * pageSize, pageSize);
		long recordCount = userMapper.countUser();
		return new Pagination<>(userList, recordCount, pageSize, pageNum);
	}

	@Transactional(readOnly = true)
	@Override
	public User getUserById(Long userId) {
		return userMapper.getUserById(userId);
	}

	@Override
	public int updateUser(User oldUser,User user) {
		User diffObject = ClassUtils.getDiffObject(oldUser, user);
		boolean flag = ClassUtils.isNull(diffObject);
		if(!flag) {
			diffObject.setId(user.getId());
			return userMapper.updateUser(diffObject);
		}
		return 0;
	}

	@Override
	public int deleteUser(Long[] ids) {
		int result = 0;
		if(ids != null) {
			for(int i = 0; i < ids.length; i++) {
				Long id = ids[i];
				if(id != null) {
					result += userMapper.deleteUserById(id);
				}
			}
		}
		return result;
	}

	@Override
	public int editPassword(Long userId, String password) {
		password = MD5Util.md5(password);
		return userMapper.updatePassword(userId, password);
	}

}
