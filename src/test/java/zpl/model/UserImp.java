package zpl.model;

/**
 * Created by zpl on 2014/6/28.
 */
public class UserImp implements IUser {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void Login(String username, String password) {
        System.out.println("---------- 程序正在执行  类名： com.laoyangx.Aop.chapter0.UserImpl 方法名:Login ----------------");
    }

}
