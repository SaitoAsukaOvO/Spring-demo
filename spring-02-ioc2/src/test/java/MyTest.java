import com.dyh.pojo.User;
import com.dyh.pojo.UserT;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test1() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        User user = (User) classPathXmlApplicationContext.getBean("user");
        User userT = (User) classPathXmlApplicationContext.getBean("user");
        System.out.println(user == userT);
        user.show();
    }
}
