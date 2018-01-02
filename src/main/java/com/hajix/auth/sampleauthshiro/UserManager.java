package com.hajix.auth.sampleauthshiro;

import org.apache.shiro.mgt.SecurityManager;

public interface UserManager {

    void addUser(String username, String password);
    
    SecurityManager getSecurityManager();
}
