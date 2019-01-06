package com.mark;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 * 在调用过程中使用了通用的代理类包装了RealSubject实例，
 * 然后调用了Jdk的代理工厂方法实例化了一个具体的代理类。
 * 最后调用代理的doSomething方法，还有附加的before、after方法可以被任意复用（只要我们在调用代码处使用这个通用代理类去包装任意想要需要包装的被代理类即可）。
 * 当接口改变的时候，虽然被代理类需要改变，但是我们的代理类却不用改变了。
 * 这个调用虽然足够灵活，可以动态生成一个具体的代理类，而不用自己显示的创建一个实现具体接口的代理类。
 */

public class AopProxyTest {
    public static void main(String args[]) {
        ProxyHandler proxyHandler = new ProxyHandler();
        Subject sub = (Subject) proxyHandler.bind(new RealSubject());
        sub.doSomeThing();
    }
}gadd .class
interface Subject{
    public void doSomeThing();
}
class RealSubject implements Subject {

    @Override
    public void doSomeThing() {
        System.out.println("call doSomething()");
    }
}
class ProxyHandler implements InvocationHandler{
    private Object tar;
    // 绑定委托对象，并返回代理类
    public Object bind(Object tar) {
        this.tar = tar;
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(),
                    tar.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 被代理的类型为Object基类
        Object result = null;
        // 这里就可以进行所谓的AOP编程了
        System.out.println("方法执行前");
        // 在调用具体函数方法前，执行功能处理
        result = method.invoke(tar, args);
        // 在调用具体函数方法后，执行功能处理
        System.out.println("方法执行后");
        return result;
    }
}
