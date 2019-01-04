package com.mark.aop;

import com.mark.pojo.Result;
import com.mark.pojo.Role;
import com.mark.pojo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
public class UserPointcut {
    @Pointcut("execution(* com.mark.controller.UserController.userTest(..))")
    public void myResult() {}

    @Before("myResult()")
    public void beforeResult(JoinPoint joinPoint) {
        System.out.println("@Before: before check");
        System.out.println("@Before: method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("@Before: paramters: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("@Before: the object which has been weaving: " + joinPoint.getTarget());
    }

    @AfterReturning(value = "myResult()", returning = "res")
    public void checkResult(JoinPoint point, Object res) {
        System.out.println("@AfterReturning: 后置切入点: " + res.toString());

        System.out.println("@AfterReturning：模拟日志记录功能...");
        System.out.println("@AfterReturning：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        System.out.println("@AfterReturning：参数为：" +
                Arrays.toString(point.getArgs()));
        System.out.println("@AfterReturning：返回值为：" + res.toString());
        System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
    }
    @After("myResult()")
    public void release(JoinPoint point) {
        System.out.println("@After：模拟释放资源...");
        System.out.println("@After：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@After：被织入的目标对象为：" + point.getTarget());
    }
    @Around("myResult()")
    public Object aroundResult(ProceedingJoinPoint point) throws Throwable {
        System.out.println("@Around: before proceed target method...");
        Object[] args = point.getArgs();
        System.out.println("@Around: parameters: " + Arrays.toString(args));
        if (args != null && args.length > 0 && args[0].getClass() == com.mark.pojo.User.class) {
            User user = new User();
            Role role = new Role();
            role.setRid("1");
            role.setRoleName("admin");
            user.setId(666);
            user.setUser("mark");
            user.setRole(role);
            args[0] = user;
        }
        //用改变后的参数执行目标方法
        Object returnValue = point.proceed(args);
        System.out.println("@Around: 执行目标方法之后");
        System.out.println("@Around：被织入的目标对象为：" + point.getTarget());
        return new Result("原返回值：" + returnValue + "，这是返回结果的后缀", "200");
    }
}
