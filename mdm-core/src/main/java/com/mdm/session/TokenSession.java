package com.mdm.session;

import com.alibaba.fastjson.JSONArray;
import com.mdm.common.AbstractBean;

import java.util.Set;

public class TokenSession extends AbstractBean {
    private String token;
    private Integer accountId;
    private String accountName;
    private String nickName;
    private long createTime;
    private long latestActiveTime;
    // 功能权限Token
    private Set<String> functionPermissionTokens;
    private JSONArray functionPermission;
    //角色
    private Set<String> roles;

    public JSONArray getFunctionPermission() {
        return functionPermission;
    }

    public void setFunctionPermission(JSONArray functionPermission) {
        this.functionPermission = functionPermission;
    }

    public Set<String> getFunctionPermissionTokens() {
        return functionPermissionTokens;
    }

    public void setFunctionPermissionTokens(Set<String> functionPermissionTokens) {
        this.functionPermissionTokens = functionPermissionTokens;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLatestActiveTime() {
        return latestActiveTime;
    }

    public void setLatestActiveTime(long latestActiveTime) {
        this.latestActiveTime = latestActiveTime;
    }

    public void touch() {
        setLatestActiveTime(System.currentTimeMillis());
    }

    public void destory() {
        functionPermissionTokens.clear();
        functionPermissionTokens = null;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}
