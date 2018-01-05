package com.mdm.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class TokenSessionWatcher implements Runnable {
    private final static Log log = LogFactory.getLog(TokenSessionWatcher.class);

    private final static int DEFAULT_INTERVAL = 5 * 60 * 1000;
    private final static Long PC_IDLE_TIME = 60 * 60 * 1000L;
    private int interval;
    private boolean runSwitch = true;

    public TokenSessionWatcher(int millisecond) {
        this.interval = millisecond;
    }

    public TokenSessionWatcher() {
        this.interval = DEFAULT_INTERVAL;
    }

    @Override
    public void run() {
        try {
            TokenSessionManager mgr = TokenSessionManager.getInstance();
            while (runSwitch) {
                Thread.sleep(interval);
                log.info("begin to clear inactive tokenSession,current size=" + mgr.getActiveConnectionNumber());
                List<TokenSession> ls = mgr.getAllTokenSession();
                long curTime = System.currentTimeMillis();
                for (TokenSession s : ls) {
                    if ((curTime - s.getLatestActiveTime()) > PC_IDLE_TIME) {
                        mgr.removeDomainTokenSession(s.getToken());
                    }
                }
                log.info("clear inactive tokenSession end,current size=" + mgr.getActiveConnectionNumber());
            }
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public void stop() {
        this.runSwitch = false;
    }

    public boolean isRunning() {
        return runSwitch;
    }
}
