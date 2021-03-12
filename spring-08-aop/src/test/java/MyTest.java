import com.dyh.service.UserService;
import com.dyh.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //
        UserService userService = (UserService) context.getBean("userService");
        userService.add();
    }
}
