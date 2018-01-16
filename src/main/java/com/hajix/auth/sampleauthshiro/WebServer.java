package com.hajix.auth.sampleauthshiro;

import java.net.URI;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

import com.google.inject.Guice;
import com.google.inject.Stage;

public class WebServer {

    public static void start(WebSecurityManager sm) throws Exception {
        Guice.createInjector(
            Stage.PRODUCTION,
            new TestModule()
        );
        
        int port = 5000;
        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        
        URI uri = WebServer.class.getClassLoader().getResource("./static").toURI();
        Resource newResource = Resource.newResource(uri);
        context.setBaseResource(newResource);

        context.addFilter(ShiroFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        context.addFilter(RolesAuthorizationFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
//        Disabled Guice/Jackson as this conflicts with serving static contents
//        context.addFilter(GuiceFilter.class, "/*", EnumSet.<DispatcherType>of(DispatcherType.REQUEST, DispatcherType.ASYNC));
        context.addServlet(DefaultServlet.class, "/*");
        
        context.addEventListener(new CustomEnvironmentLoaderListener(sm));

        server.start();

        server.join();
    }
}
