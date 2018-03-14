package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/findById/{id}")
	@ResponseBody
	public User findById(@PathVariable int id) {
		User user=userService.findById(id);
		return user;
		
	}
}
