Spring

## 1. 介绍

### 下载地址：

https://repo.spring.io/release/org/springframework/spring/

### maven：

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.0.RELEASE</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.0.RELEASE</version>
</dependency>

```

### 官方文档：

https://www.docs4dev.com/docs/zh/spring-framework/5.1.3.RELEASE/reference/overview.html



## 2. IOC

### 2.1 why IOC

新建一个空白的maven项目

> 分析实现

我们先用我们原来的方式写一段代码 .

1、先写一个UserDao接口

```java
public interface UserDao { public void getUser();}
```

2、再去写Dao的实现类

```java
public class UserDaoImpl implements UserDao {   
    @Override   
    public void getUser() {       
        System.out.println("获取用户数据");  
    }
}
```

3、然后去写UserService的接口

```java
public interface UserService {   
	public void getUser();
}
```

4、最后写Service的实现类

```java
public class UserServiceImpl implements UserService {   
    private UserDao userDao = new UserDaoImpl();   
    @Override   
    public void getUser() 
    {       
        userDao.getUser();  
    }
}
```

5、测试一下

```java
@Test
public void test(){   
    UserService service = new UserServiceImpl();   
    service.getUser();
}
```

这是我们原来的方式 , 开始大家也都是这么去写的对吧 . 那我们现在修改一下 .

把Userdao的实现类增加一个 .

```java
public class UserDaoMySqlImpl implements UserDao {   
    @Override   
    public void getUser() {       
        System.out.println("MySql获取用户数据");  
    }
}
```

紧接着我们要去使用MySql的话 , 我们就需要去service实现类里面修改对应的实现

```java
public class UserServiceImpl implements UserService {   
    private UserDao userDao = new UserDaoMySqlImpl();   
    @Override   
    public void getUser() 
    {       
        userDao.getUser();  
    }
}
```

在假设, 我们再增加一个Userdao的实现类 .

```java
public class UserDaoOracleImpl implements UserDao {   
    @Override   
    public void getUser() {       
        System.out.println("Oracle获取用户数据");  
    }
}
```

那么我们要使用Oracle , 又需要去service实现类里面修改对应的实现 . 假设我们的这种需求非常大 , 这种方式就根本不适用了,每次变动 , 都需要修改大量代码 . 这种设计的耦合性太高了, 牵一发而动全身 .

**那我们如何去解决呢 ?** 

我们可以在需要用到他的地方 , 不去实现它 , 而是留出一个接口 , 利用set , 我们去代码里修改下 .

```java
public class UserServiceImpl implements UserService {   
    private UserDao userDao;
    // 利用set实现   
    public void setUserDao(UserDao userDao) {       
        this.userDao = userDao;  
    }   
    @Override   
    public void getUser() {       
        userDao.getUser();  
    }
}
```

现在去我们的测试类里 , 进行测试 ;

```java
@Test
public void test(){   
    UserServiceImpl service = new UserServiceImpl();   
    service.setUserDao( new UserDaoMySqlImpl() );   
    service.getUser();   
    //那我们现在又想用Oracle去实现呢   
    service.setUserDao( new UserDaoOracleImpl() );   
    service.getUser();
}
```

大以前所有东西都是由程序去进行控制创建 , 而现在是由我们自行控制创建对象 , 把主动权交给了调用者 . 程序不用去管怎么创建,怎么实现了 . 它只负责提供一个接口 .

这种思想 , 从本质上解决了问题 , 我们程序员不再去管理对象的创建了 , 更多的去关注业务的实现 . 耦合性大大降低 . 这也就是IOC的原型 !





### 2.2 HelloSpring

> 导入Jar包

注 : spring 需要导入commons-logging进行日志记录 . 我们利用maven , 他会自动下载对应的依赖项 .

```xml
<dependency>   
    <groupId>org.springframework</groupId>   
    <artifactId>spring-webmvc</artifactId>   
    <version>5.1.10.RELEASE</version>
</dependency>
```

> 编写代码

1、编写一个Hello实体类

```java
public class Hello {   
    private String name;   
    public String getName() {       
        return name;  
    }   
    public void setName(String name) {       
        this.name = name;  
    }   
    public void show(){       
        System.out.println("Hello,"+ name );  
    }
}
```

2、编写我们的spring文件 , 这里我们命名为beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!--bean就是java对象 , 由Spring创建和管理-->   
        <bean id="hello" class="com.kuang.pojo.Hello">       
            <property name="name" value="Spring"/>   
            </bean>
</beans>
```

3、我们可以去进行测试了 .

```java
@Testpublic void test(){   
    //解析beans.xml文件 , 生成管理相应的Bean对象  
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");   
    //getBean : 参数即为spring配置文件中bean的id .   
    Hello hello = (Hello) context.getBean("hello");   
    hello.show();
}
```



> 修改案例一

我们在案例一中， 新增一个Spring配置文件beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"      xsi:schemaLocation="http://www.springframework.org/schema/beans       http://www.springframework.org/schema/beans/spring-beans.xsd">   
    <bean id="MysqlImpl" class="com.kuang.dao.impl.UserDaoMySqlImpl"/>   
    <bean id="OracleImpl" class="com.kuang.dao.impl.UserDaoOracleImpl"/>   
    <bean id="ServiceImpl" class="com.kuang.service.impl.UserServiceImpl">       
        <!--注意: 这里的name并不是属性 , 而是set方法后面的那部分 , 首字母小写-->       
        <!--引用另外一个bean , 不是用value 而是用 ref-->       
        <property name="userDao" ref="OracleImpl"/>   
    </bean>
</beans>
```

测试！

```java
@Testpublic void test2(){   
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");   
    UserServiceImpl serviceImpl = (UserServiceImpl) context.getBean("ServiceImpl");   
    serviceImpl.getUser();
}
```

OK , 到了现在 , 我们彻底不用再程序中去改动了 , 要实现不同的操作 , 只需要在xml配置文件中进行修改 , 所谓的IoC,一句话搞定 : 对象由Spring 来创建 , 管理 , 装配 ! 



### 2.2 IOC创建对象方式

> 通过无参构造方法来创建

1、User.java

```java
public class User {   
    private String name;   
    public User() {       
        System.out.println("user无参构造方法");  
    }   
    public void setName(String name) {       
        this.name = name;  
    }   
    public void show(){       
        System.out.println("name="+ name );  
    }
}
```

2、beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"      xsi:schemaLocation="http://www.springframework.org/schema/beans       http://www.springframework.org/schema/beans/spring-beans.xsd">   
    <bean id="user" class="com.kuang.pojo.User">       
        <property name="name" value="kuangshen"/>   
    </bean>
</beans>
```

3、测试类

```java
@Testpublic void test(){   
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");   
    //在执行getBean的时候, user已经创建好了 , 通过无参构造   
    User user = (User) context.getBean("user");   
    //调用对象的方法 .   
    user.show();
}
```

结果可以发现，在调用show方法之前，User对象已经通过无参构造初始化了！



> 通过有参构造方法来创建

1、UserT . java

```java
public class UserT {   
    private String name;   
    public UserT(String name) {       
        this.name = name;  
    }   
    public void setName(String name) {       
        this.name = name;  
    }   
    public void show(){       
        System.out.println("name="+ name );  
    }
}
```

2、beans.xml 有三种方式编写

```xml
<!-- 第一种根据index参数下标设置 -->
<bean id="userT" class="com.kuang.pojo.UserT">   
    <!-- index指构造方法 , 下标从0开始 -->   
    <constructor-arg index="0" value="kuangshen2"/>
</bean>
<!-- 第二种根据参数名字设置 -->
<bean id="userT" class="com.kuang.pojo.UserT">   
    <!-- name指参数名 -->   
    <constructor-arg name="name" value="kuangshen2"/>
</bean>
<!-- 第三种根据参数类型设置 -->
<bean id="userT" class="com.kuang.pojo.UserT">   
    <constructor-arg type="java.lang.String" value="kuangshen2"/>
</bean>
```

3、测试

```java
@Testpublic void testT(){   
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");   
    UserT user = (UserT) context.getBean("userT");   
    user.show();
}
```

结论：在配置文件加载的时候。其中管理的对象都已经初始化了！





### 2.4 Spring配置

> 别名

alias 设置别名 , 为bean设置别名 , 可以设置多个别名

```xml
<!--设置别名：在获取Bean的时候可以使用别名获取-->
<alias name="userT" alias="userNew"/>
```

> Bean的配置

```xml
<!--bean就是java对象,由Spring创建和管理-->
<!--   id 是bean的标识符,要唯一,如果没有配置id,name就是默认标识符   
如果配置id,又配置了name,那么name是别名   
name可以设置多个别名,可以用逗号,分号,空格隔开   
如果不配置id和name,可以根据applicationContext.getBean(.class)获取对象;
class是bean的全限定名=包名+类名-->
<bean id="hello" name="hello2 h2,h3;h4" class="com.kuang.pojo.Hello">   
    <property name="name" value="Spring"/>
</bean>
```

> import

团队的合作通过import来实现 .

```xml
<import resource="{path}/beans.xml"/>
```



## 3. DI 依赖注入（set方式注入）

- 依赖注入：set注入
  - 依赖：bean对象的创建依赖于容器
  - 注入：bean对象中的所有属性，由容器来注入

#### pojo: Student.java & Address.java

```java
package com.dyh.pojo;

import java.util.*;

public class Student {

    private String name;
    private Address address;
    private String[] books;
    private List<String> hobbies;
    private Map<String, String> card;
    private Set<String> games;
    private String wife;
    private Properties info;
    //省略get set toString
}

```

```java
package com.dyh.pojo;

public class Address {
    private String address;
    
    //省略get set toString
}

```



#### resource/beans.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="com.dyh.pojo.Address">
        <property name="address" value="Irvine"></property>
    </bean>

    <bean id="student" class="com.dyh.pojo.Student">
        <!--普通值注入, value-->
        <property name="name" value="dyh"/>

        <!--bean注入，ref-->
        <property name="address" ref="address"/>

        <!--数组注入，ref-->
        <property name="books">
            <array>
                <value>红楼</value>
                <value>西游</value>
                <value>水浒</value>
                <value>三国</value>
            </array>
        </property>

        <!--list-->
        <property name="hobbies">
            <list>
                <value>coding</value>
                <value>gym</value>
                <value>basketball</value>
            </list>
        </property>

        <!--map-->
        <property name="card">
            <map>
                <entry key="ID" value="11112222233333"></entry>
                <entry key="bankCard" value="123456"></entry>
            </map>
        </property>

        <!--set-->
        <property name="games">
            <set>
                <value>LOL</value>
                <value>COD</value>
                <value>COC</value>
            </set>
        </property>

        <!--空字符-->
<!--        <property name="wife" value=""/>-->
        <!--null-->
        <property name="wife">
            <null/>
        </property>

        <!--properties
        key=value
        key=value
        -->
        <property name="info">
            <props>
                <prop key="StudentID">20190525</prop>
                <prop key="Sex">Male</prop>
                <prop key="Name">abc</prop>
            </props>
        </property>

    </bean>

</beans>
```



#### Test:

```java
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
```



## 4. 拓展注入方式

可以使用p命名空间和c命名空间(需要导入xml约束)：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">


    <!--p命名空间注入，可以直接注入属性的值：property-->
    <bean id="user" class="com.dyh.pojo.User" p:name="dyh" p:age="24"/>

    <!--c命名空间注入， 通过构造器注入：constructor-->
    <bean id="user2" class="com.dyh.pojo.User" c:name="dy" c:age="22"/>
</beans>
```

Test:

```java
    @Test
    public void test2() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("userbean.xml");
        User user = context.getBean("user2", User.class);
        System.out.println(user);
    }
```





## 5. bean的作用域：

![bean作用域](.\img\bean作用域.png)

### singleton（默认）：

![singleton](.\img\singleton.png)

```xml
<bean id="accountService" class="com.something.DefaultAccountService"/>

<!-- the following is equivalent, though redundant (singleton scope is the default) -->
<bean id="accountService" class="com.something.DefaultAccountService" scope="singleton"/>
```



### prototype:

![prototype](.\img\prototype.png)

```xml
<bean id="accountService" class="com.something.DefaultAccountService" scope="prototype"/>
```



## 6. bean的自动装配



### byName自动装配

```xml
    <!--byname： 会自动再容器上下文中查找，和自己set方法后面的值对应的beanid-->
    <bean id="people" class="com.dyh.pojo.People" autowire="byName">
        <property name="name" value="dyh"/>
    </bean>
```



### byType自动装配

```xml
    <!--byType： 会自动再容器上下文中查找，和自己set对象属性相同的beanid (装配的类型必须全局只有一个)-->
    <bean id="people" class="com.dyh.pojo.People" autowire="byType">
        <property name="name" value="dyh"/>
    </bean>
```



### 总结

- byName时候，必须保证所有bean的id唯一，并且这个bean需要和自动注入的属性的set方法值一致
- byType时候，必须保证所有bean的class唯一，并且这个bean需要和自动注入的属性的类型一致



### 使用注解自动装配

#### 步骤

1. 导入约束 context约束

2. 配置注解的支持 <context:annotation-config/>

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context.xsd">
   
       <context:annotation-config/>
   
   </beans>
   ```



#### @Autowire

Autowired 首先是根据byType  若有两个class相同，则根据byName 即变量名与spring容器中的id名相同

1. 直接在属性上使用即可 

   （可以不需要set方法， 前提是这个自动装配的属性再IOC容器中存在，且符合byType自动装配）

```java
package com.dyh.pojo;

import org.springframework.beans.factory.annotation.Autowired;

public class People {

    @Autowired
    private Cat cat;
    @Autowired
    //@Qualifier(value = "dog222") 指定一个唯一的bean对象进行注入
    private Dog dog;
    private String name;
}
```

当自动装配环境比较复杂时，自动装配无法通过一个【@Autowire】进行装配，例如既有beanid = dog 又有beanid = dog222， 可以使用@Qualifier(value = "dog222") 指定一个唯一的bean进行注入





## 7. 使用注解开发

### 属性如何注入

```java
package com.dyh.pojo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//等价于 <bean id="user" class="com.dyh.pojo.User"/>
//@Component 组件
@Component
public class User {
//    @Value("dyh")
    public String name;

    @Value("ddd")
    public void setName(String name) {
        this.name = name;
    }
}
```



### 衍生注解

@Component 有几个衍生注解，在web开发中会按照MVC三层架构分层

- dao 【@Repository】

- service【@Service】

- controller 【@Controller】

  这四个注解功能都一样，都是把类放到spring容器中

### 自动装配置

```markdown
- @Autowire : 自动装配，byType
	如果Autowire不能唯一自动装配属性，则需要通过@Qualifier(value="xxx")
- @Nullable : 字段标记了这个注解，说明这个字段可以为null
- @Resource ： 自动装配，byName
```



### 作用域

```java
package com.dyh.pojo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//等价于 <bean id="user" class="com.dyh.pojo.User"/>
//@Component 组件
@Component
@Scope(value = "prototype")
public class User {
//    @Value("dyh")
    public String name;

    @Value("ddd")
    public void setName(String name) {
        this.name = name;
    }
}

```



## 8. 使用java配置Spring

### 实体类：

/pojo/User.java

```java
package com.dyh.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {
    private String name;

    public String getName() {
        return name;
    }

    @Value("dyh")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```



### 配置类

/config/MyConfig.java

```java
package com.dyh.config;


import com.dyh.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration代表这是一个配置类 = beans.xml
@Configuration
@ComponentScan("com.dyh.pojo")
public class MyConfig {

    //注册一个bean，就相当于我们之前写的一个bean标签
    //这个方法的名字，就相当于bean标签中的id属性
    //这个方法的返回值，就相当于bean标签中的class属性
    @Bean
    public User getUser() {
        //return就是返回要注入到bean的对象
        return new User();
    }
}
```



### 测试

```java
@Test
public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        User getUser = (User) context.getBean("getUser");
        System.out.println(getUser.getName());
}
```





## 9. Spring AOP 实现

### 方式一： 使用原生的Spring Api接口：

BeforeLog.java

```java
package com.dyh.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BeforeLog implements MethodBeforeAdvice {
    public void before(Method method, Object[] objects, Object target) throws Throwable {
        System.out.println(target.getClass().getName() + "'s " + method.getName() + " method is used");
    }
}
```

AfterLog.java

```java
package com.dyh.log;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterLog implements AfterReturningAdvice {
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println(method.getName() + " method is used; Return Value: " + returnValue);
    }
}
```

applicationContext.xml

```xml
   <!--配置aop-->
    <aop:config>
        <!--定义切入点-->
        <aop:pointcut id="pointcut" expression="execution(* com.dyh.service.UserServiceImpl.*(..))"/>

        <!--执行环绕增强-->
        <aop:advisor advice-ref="beforeLog" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>
```



### 方式二：自定义类

myProxy.java

```java
package com.dyh.utils;

public class MyProxy {

    public void before() {
        System.out.println("===============before the method===================");
    }

    public void after() {
        System.out.println("================after the method====================");
    }
}

```

applicationContext.xml

```xml
    <bean id="myProxy" class="com.dyh.utils.MyProxy"/>
    <aop:config>
        <!--自定义切面 ref到引用的类-->
        <aop:aspect ref="myProxy">
            <!--切入点-->
            <aop:pointcut id="point" expression="execution(* com.dyh.service.UserServiceImpl.*(..))"/>
            <!--通知-->
            <aop:before method="before" pointcut-ref="point"/>
            <aop:after method="after" pointcut-ref="point"/>
        </aop:aspect>
    </aop:config>
```





### 方式三 :  注解实现:

```java
package com.dyh.utils;

//注解实现aop

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect // 标注这个类是切面
public class AnnotationPointCut {

    @Before("execution(* com.dyh.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("=========before the method=========");
    }

    @After("execution(* com.dyh.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("=========after the method==========");
    }

    //在环绕增强中我们可以给定一个参数，代表我们要获取处理切入的点
    @Around("execution(* com.dyh.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(">>>>>>>>>>before around<<<<<<<<<<<");
        Signature signature = joinPoint.getSignature();
        Object proceed = joinPoint.proceed();
        System.out.println(signature.toString() + " " + proceed);
        System.out.println(">>>>>>>>>>after around<<<<<<<<<<<<");
    }
}

```

applicationContext.xml:

```xml
    <!--方式三 注解实现-->
    <bean id="annotationPointCut" class="com.dyh.utils.AnnotationPointCut"/>
    <aop:aspectj-autoproxy/>
```



## 10. 整合MyBatis

### 编写数据源配置

### sqlSessionFactory

### sqlSessionTemplate

- spring-dao.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--data source: 使用Spring的数据源替换Mybatis的配置 c3p0 dbcp druid-->
      <bean id="datasource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
          <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
          <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;userUnicode=true&amp;characterEncoding=UTF-8"/>
          <property name="username" value="root"/>
          <property name="password" value="123456"/>
      </bean>
  
      <!--sqlSessionFactory-->
      <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
          <property name="dataSource" ref="datasource"/>
          <!--绑定Mybatis配置文件-->
          <property name="configLocation" value="classpath:mybatis-config.xml"/>
          <property name="mapperLocations" value="classpath:com/dyh/mapper/*.xml"/>
      </bean>
  
      <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
          <!--只能使用构造器注入sqlSessionFactory-->
          <constructor-arg index="0" ref="sqlSessionFactory"/>
      </bean>
  
  </beans>
  ```

- mybatis-config.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      
      <typeAliases>
          <package name="com.dyh.pojo"/>
      </typeAliases>
  
  </configuration>
  ```

### 需要给接口加实现类

- UserMapperImpl.java

  ```java
  package com.dyh.mapper;
  
  import com.dyh.pojo.User;
  import org.mybatis.spring.SqlSessionTemplate;
  
  import java.util.List;
  
  public class UserMapperImpl implements UserMapper {
      private SqlSessionTemplate sqlSessionTemplate;
  
      public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
          this.sqlSessionTemplate = sqlSessionTemplate;
      }
  
      public List<User> selectUser() {
          UserMapper mapper = sqlSessionTemplate.getMapper(UserMapper.class);
          return mapper.selectUser();
      }
  }
  ```

  

### 将自己写的实现类注入到spring

- applicationContext.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <import resource="spring-dao.xml"/>
      <bean id="userMapper" class="com.dyh.mapper.UserMapperImpl">
          <property name="sqlSessionTemplate" ref="sqlSessionTemplate"/>
      </bean>
  
  </beans>
  ```

  

### 测试使用

```java
import com.dyh.mapper.UserMapper;
import com.dyh.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyTest {
    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);


        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectUser();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.selectUser()) {
            System.out.println(user);
        }
    }
}

```



## 11. Transaction

http://mybatis.org/spring/zh/transactions.html

```xml
    <!--结合aop实现事务的织入-->
    <!--配置事务通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!--给方法配置事务-->
        <!--配置事务的传播特性：`propagation`-->
        <tx:attributes>
            <tx:method name="add" propagation="REQUIRED"/>
            <tx:method name="delete"/>
            <tx:method name="update"/>
        </tx:attributes>
    </tx:advice>

    <aop:config>
        <aop:pointcut id="txPointCut" expression="execution(* com.dyh.mapper.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
    </aop:config>
```

