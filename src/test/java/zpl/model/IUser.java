package zpl.model;

/**
 * Created by zpl on 2014/6/28.
 */
public interface IUser {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     */
    public void Login(String username,String password);

}
