package zpl.aspect;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
/**
 * Created by zpl on 2014/6/28.
 */
@Aspect
public class LogAspect {
    @Before("execution(* zpl.model.*.*(..))")
    public void saveLog() {
        Logger log = Logger.getLogger(LogAspect.class.getName());
        log.log(Level.INFO, "信息被保存");
    }
    @AfterThrowing(throwing="rvt",pointcut="execution(* zpl.model.*.*(..))")
    public void throwLog(Throwable rvt){
          System.out.println("获取目标方法抛出的异常"+rvt);
          System.out.println("记录日志");
    }
    //定义一个Around的切入点  
    @Around("execution(* zpl.model.*.*(..))")  
    public Object selectLog(ProceedingJoinPoint pj) throws Throwable{  
        Logger log = Logger.getLogger(LogAspect.class.getName());  
        log.log(Level.INFO, "信息被查询");  
        //System.out.println(pj.getArgs().getClass()+"--");  
        Object result= pj.proceed(new String[]{"peitihuande zhi"});  
        return "peitihuande zhi";  
    } 
}
