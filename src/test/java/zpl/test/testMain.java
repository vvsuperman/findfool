package zpl.test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zpl.model.*;
/**
 * Created by zpl on 2014/6/28.
 */
public class testMain {
    public static void main(String[] args) {

        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("/applicationContext-test.xml");

        UserDao user = (UserDao) ctx.getBean("userDao");
        //user.Login("username", "123456");
        user.save();
        //suser.delete();
        user.selectUser("ss");

    }

}
