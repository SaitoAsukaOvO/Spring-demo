import com.dyh.dao.OracleImpl;
import com.dyh.pojo.Hello;
import com.dyh.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {


    @Test
    public void test1() {
        //获取spring的上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //我们的对象都写在spring中管理，要使用取出即可
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello.toString());
    }

    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //拿到容器，需要什么 get什么
        UserService userServiceImpl = (UserService) context.getBean("UserServiceImpl");
        userServiceImpl.getUser();
    }

    @Test
    public void test3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        OracleImpl oracleImpl = (OracleImpl) context.getBean("oracleImpl");
        oracleImpl.getUser();
    }

}
