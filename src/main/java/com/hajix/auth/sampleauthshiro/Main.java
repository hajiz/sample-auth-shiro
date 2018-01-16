package com.hajix.auth.sampleauthshiro;

import org.apache.shiro.web.mgt.WebSecurityManager;


public class Main {
    public static void main(String[] args) {
        UserManager userManager = InMemoryUserManager.getInstance();

        setUpUsers(userManager);
        
        try {
            WebServer.start((WebSecurityManager) userManager.getSecurityManager());
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    private static void setUpUsers(UserManager userManager) {
        userManager.addUser("hajix", "verysafepassword");
    }
}
