package com.mdm.session.impl;

import com.mdm.session.TokenSession;
import com.mdm.session.ISessionCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenSessionMapCache implements ISessionCache {
    private final static Log log = LogFactory.getLog(TokenSessionMapCache.class);
    private final Map<String, TokenSession> cache = new ConcurrentHashMap<>();

    @Override
    public void put(TokenSession session) throws Exception {
        cache.put(session.getToken(), session);

    }

    @Override
    public void remove(String token) throws Exception {
        TokenSession s = cache.get(token);
        if (s != null) {
//            s.destory();
            cache.remove(token);
            log.info("remove tokenSession success,token=" + token);
        } else {
            log.warn("remove tokenSession failed,cause:the tokenSession doesn't exsit,token= " + token);
        }

    }

    @Override
    public TokenSession get(String token) throws Exception {
        return cache.get(token);
    }

    @Override
    public List<TokenSession> list() throws Exception {
        List<TokenSession> ls = new ArrayList<>();
        ls.addAll(cache.values());
        return ls;
    }

    @Override
    public void clear() throws Exception {
        cache.clear();

    }

    @Override
    public Integer size() throws Exception {
        return cache.size();

    }

}
