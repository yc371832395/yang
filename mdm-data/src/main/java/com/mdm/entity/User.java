package com.mdm.entity;

import com.mdm.common.AbstractBean;

public class User extends AbstractBean {

    
    private Integer userId;
    
    private String username;
    
    private String userpass;
    
    

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
    
}
