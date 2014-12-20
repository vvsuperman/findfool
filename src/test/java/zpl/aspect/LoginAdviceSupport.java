package zpl.aspect;

/**
 * Created by zpl on 2014/6/28.
 */
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

public class LoginAdviceSupport extends BaseLoginAdvice {

    /**
     * 若在数据库中存在指定的用户,将用户登录信息写入日志文件
     * @param returnValue 目标方法返回值
     * @param method 目标方法
     * @param args 方法参数
     * @param target 目标对象
     */
    @Override
    public void afterReturning(Object returnValue, Method method,
                               Object[] args, Object target) throws Throwable {

        System.out.println("---------- 程序正在执行 类名： com.laoyangx.Aop.chapter0.LoginAdviceSupport 方法名:afterReturning ----------------");

        //将用户登录信息写入日志文件
    }

    /**
     * 验证用户输入是否符合要求
     * @param invocation 目标对象的方法
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        System.out.println("---------- 程序正在执行 类名： com.laoyangx.Aop.chapter0.LoginAdviceSupport 方法名:invoke ----------------");

        String username=invocation.getArguments()[0].toString();
        String password=invocation.getArguments()[1].toString();

        //在这里进行相关的验证操作

        //假设验证通过
        return invocation.proceed();
    }

    /**
     * 在数据库中查找指定的用户是否存在
     * @param method 将要执行的目标对象方法
     * @param args 方法的参数
     * @param target 目标对象
     */
    @Override
    public void before(Method method, Object[] args, Object target)
            throws Throwable {

        System.out.println("---------- 程序正在执行 类名： com.laoyangx.Aop.chapter0.LoginAdviceSupport 方法名:before ----------------");

        String username=(String)args[0];
        String passowrd=(String)args[1];

        //在这里进行数据库查找操作
    }
}
