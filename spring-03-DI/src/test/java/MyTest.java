import com.dyh.pojo.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student.toString());


        /**
         * Student{
         * name='dyh',
         * address=Address{address='Irvine'},
         * books=[红楼, 西游, 水浒, 三国],
         * hobbies=[coding, gym, basketball],
         * card={ID=11112222233333, bankCard=123456},
         * games=[LOL, COD, COC],
         * wife='null',
         * info={StudentID=20190525, Name=abc, Sex=Male}
         * }
         */
    }
}
