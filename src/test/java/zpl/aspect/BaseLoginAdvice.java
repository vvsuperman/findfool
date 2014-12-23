package zpl.aspect;

/**
 * Created by zpl on 2014/6/28.
 */
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public abstract class BaseLoginAdvice implements MethodBeforeAdvice, MethodInterceptor,
        AfterReturningAdvice {
    /**
     * @param returnValue 目标方法返回值
     * @param method 目标方法
     * @param args 方法参数
     * @param target 目标对象
     *
     */
    @Override
    public void afterReturning(Object returnValue, Method method,
                               Object[] args, Object target) throws Throwable {

        throw new UnsupportedOperationException("abstract class CBaseLoginAdvice not implement this method");
    }


    /**
     * @param invocation 目标对象的方法
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        throw new UnsupportedOperationException("abstract class CBaseLoginAdvice not implement this method");
    }

    /**
     * @param method 将要执行的目标对象方法
     * @param args 方法的参数
     * @param target 目标对象
     */
    @Override
    public void before(Method method, Object[] args, Object target)
            throws Throwable {

        throw new UnsupportedOperationException("abstract class CBaseLoginAdvice not implement this method");
    }

}
