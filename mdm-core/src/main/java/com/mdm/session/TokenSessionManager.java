package com.mdm.session;

import com.mdm.session.impl.TokenSessionMapCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class TokenSessionManager {
    private final static Log log = LogFactory.getLog(TokenSessionManager.class);
    private final static TokenSessionManager instance = new TokenSessionManager();
    private final ISessionCache cache;

    private TokenSessionManager() {
        cache = new TokenSessionMapCache();
        init();
    }

    public static TokenSessionManager getInstance() {
        return instance;
    }

    /**
     * 返回当前所有会话列表
     */
    public List<TokenSession> getAllTokenSession() throws Exception {
        return cache.list();

    }

    private void init() {

        log.info("begin to start tokenSession watcher...");
        // 启动自动清理监控线程
        Thread t = new Thread(new TokenSessionWatcher());
        t.setDaemon(true);
        t.start();
    }

    /**
     * 添加一个会话到缓存
     */
    public void addDomainTokenSession(TokenSession s) throws Exception {
        cache.put(s);
    }

    /**
     * 从缓存中获取一个会话对象
     */
    public TokenSession getDomainTokenSession(String token) throws Exception {
        return cache.get(token);
    }

    /**
     * 从缓存中删除一个对象
     */
    public void removeDomainTokenSession(String token) throws Exception {
        cache.remove(token);
    }

    /**
     * 获得当前的有效的会话数量
     */
    public Integer getActiveConnectionNumber() throws Exception {
        return cache.size();
    }

    public void destory() throws Exception {
        cache.clear();
    }

}
