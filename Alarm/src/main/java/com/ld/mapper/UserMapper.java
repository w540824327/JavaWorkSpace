package com.ld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ld.model.User;

@Mapper
public interface UserMapper {

	User getUser(@Param("user") User user);
	
	int insertUser(@Param("user") User user);
	
	List<User> listUser(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
	
	long countUser();
	
	User getUserById(@Param("userId") Long userId);
	
	int updateUser(@Param("user") User user);
	
	int deleteUserById(@Param("userId") Long userId);
	
	int updatePassword(@Param("userId") Long userId, @Param("password") String password);
}
