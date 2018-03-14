package hello;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration

public class Helloworld {
	
	@RequestMapping("/")
//    @RequestMapping("/test/{value1}")
    @ResponseBody
    String home() {
//    	int a=new run1().runto();
        return "Hello World!";
    }
//    @RequestMapping("/show/{value2}")
//    private class run1{
//    	public int runto()
//    	{
//    		return 1;
//    	}
//    	
//    }
//	@SpringbootApplication
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Helloworld.class, args);
    }
}
