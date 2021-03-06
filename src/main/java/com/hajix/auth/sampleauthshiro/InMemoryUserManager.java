package com.hajix.auth.sampleauthshiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

public class InMemoryUserManager implements UserManager {
    private static InMemoryUserManager instance;

    public static InMemoryUserManager getInstance() {
        if (instance == null) {
            instance = new InMemoryUserManager();
        }
        return instance;
    }

    private SimpleAccountRealm realm;
    private SecurityManager securityManager;

    private InMemoryUserManager() {
        realm = new SimpleAccountRealm();
        securityManager = new DefaultWebSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);
    }

    public void addUser(String username, String password) {
        realm.addAccount(username, password);
    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

}
