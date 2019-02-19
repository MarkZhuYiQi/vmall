package com.mark.manager.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

///**
// * AbstractAnnotationConfigDispatcherServletInitializer剖析
// * 扩展AbstractAnnotationConfigDispatcherServletInitializer的任意类都会自动地
// * 配置Dispatcher-Servlet和Spring应用上下文，
// * Spring的应用上下文会位于应用程序的Servlet上下文之中。
// * servlet3.0环境下，容器会在classpath中查找实现javax.servlet.ServletContainerInitializer接口的类
// * 如果找到了，就去初始化Servlet容器
// * Spring提供了这个接口实现，名为SpringServletContainerInitializer
// * 这个类会依次搜索实现了WebApplicationInitializer的类，并委派这个类实现配置。
// * Spring 3.2开始引入一个简易的 WebApplicationInitializer 实现类，这就是 AbstractAnnotationConfigDispatcherServletInitializer。
// * 继承 AbstractAnnotationConfigDispatcherServletInitializer之后，也就是间接实现了 WebApplicationInitializer，
// * 在 Servlet 3.0 容器中，它会被自动搜索到，被用来配置 servlet 上下文。
// */
//public class DispatcherServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
//    private Logger logger = LoggerFactory.getLogger(DispatcherServletConfig.class);
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        logger.info("------------Root config-----------------");
//        return new Class<?>[] {RootApplicationContextConfig.class};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        logger.info("---------------Web config------------------");
//        return new Class<?>[] {SpringApplicationContextConfig.class};
//    }
//
//    /**
//     * 为DisaptcherServlet提供一个或更多的Servlet映射。
//     * 这里是被映射到/，指示他为默认的servlet，用来操作所有来到程序Request。
//     *
//     * @return
//     */
//    @Override
//    protected String[] getServletMappings() {
//        logger.info("-------------mapping-----------------");
//        return new String[] { "/" };
//    }
//}


public class DispatcherServletConfig implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
/*        // cors过滤器
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", CorsFilter.class);
        corsFilter.addMappingForUrlPatterns(null, false, "/*");*/
        // 配置spring
        servletContext.setInitParameter("contextConfigLocation", "classpath:spring/applicationContext.xml");
//        servletContext.setInitParameter("contextConfigLocation", "<NONE>");

        // 配置应用程序上下文，数据层，中间件
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootApplicationContextConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // 配置Spring上下文，控制器，视图控制器，handleMapping
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(SpringApplicationContextConfig.class);

        // springMVC核心类
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
