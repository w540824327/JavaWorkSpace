package com.ld.service;

import com.ld.model.Pagination;
import com.ld.model.User;

public interface UserService {

	User login(User user);
	
	int addUser(User user);
	
	Pagination<User> getUserPagination(int pageNum);
	
	User getUserById(Long userId);
	
	int updateUser(User oldUser,User user);
	
	int deleteUser(Long[] ids);
	
	int editPassword(Long userId, String password);
}
