import com.dyh.pojo.User;
import com.dyh.pojo.UserT;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test1() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) classPathXmlApplicationContext.getBean("aaa");
        UserT userT = (UserT) classPathXmlApplicationContext.getBean("userT");
        //System.out.println(user == userT);
        user.show();
    }
}
