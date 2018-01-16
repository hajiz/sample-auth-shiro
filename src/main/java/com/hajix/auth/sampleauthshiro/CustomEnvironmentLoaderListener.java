package com.hajix.auth.sampleauthshiro;

import javax.servlet.ServletContext;

import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;

public class CustomEnvironmentLoaderListener extends EnvironmentLoaderListener {
    
    private WebSecurityManager sm;

    public CustomEnvironmentLoaderListener(WebSecurityManager sm) {
        this.sm = sm;
    }

    @Override
    protected WebEnvironment createEnvironment(ServletContext sc) {
        DefaultWebEnvironment environment = new DefaultWebEnvironment();
        
        environment.setServletContext(sc);
        environment.setSecurityManager(sm);
        environment.setFilterChainResolver(createResolver());

        return environment;
    }
    
    private FilterChainResolver createResolver() {
        FilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();
        PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) filterChainResolver;
        FilterChainManager manager = resolver.getFilterChainManager();
        manager.createChain("/*", "authc");
        FormAuthenticationFilter filter = (FormAuthenticationFilter) manager.getChain("/*").get(0);
        filter.setLoginUrl("/login.html");
        return filterChainResolver;
    }
}
