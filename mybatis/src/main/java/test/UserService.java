package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public User findById(int id) {
			return userMapper.findById(id);
	}
	
	
	@Transactional
	public void insertUser(User user) {userMapper.insert(user);}
}

