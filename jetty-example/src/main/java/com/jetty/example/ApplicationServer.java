package com.jetty.example;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;

public class ApplicationServer {

    public static void main(String[] args) throws Exception {

        /* 静态资源 */
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("d:/");
        resourceHandler.setStylesheet("");

        /* 响应特定请求 */
        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/data");
        contextHandler.setHandler(resourceHandler);

        /* 响应servlet */
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(new ServletHolder(new MyServlet()), "/service/1");
        /*  */
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/service");
        servletContextHandler.addServlet(MyServlet.class, "/1");

        /* 添加handler */
        HandlerList handlers = new HandlerList();
        handlers.addHandler(contextHandler);
        handlers.addHandler(servletContextHandler);
//        handlers.addHandler(new DefaultHandler());

        /* 创建线程池 */
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool();
        queuedThreadPool.setMinThreads(10);
        queuedThreadPool.setMaxThreads(100);

        /* 创建server */
        Server server = new Server(queuedThreadPool);
        /* 创建connector */
        ServerConnector http = new ServerConnector(server);
        http.setReuseAddress(true);
        http.setIdleTimeout(30000);
        http.setHost("localhost");
        http.setPort(8080);

        /* 安装jmx */
        MBeanContainer mbContainer=new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addEventListener(mbContainer);
        server.addBean(mbContainer);
        server.addBean(Log.getLog());

        server.addConnector(http);
        server.setHandler(handlers);

        server.start();
        server.join();
    }

}
