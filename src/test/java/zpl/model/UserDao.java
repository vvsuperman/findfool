package zpl.model;

/**
 * Created by zpl on 2014/6/28.
 */
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    public void save(){
        System.out.println("用户保存成功！");
    }
    public void delete(){
    	int i= 5/0;
    }
    public String selectUser(String name){  
        System.out.println("用户信息查询成功");  
        return "success";  
      } 
}