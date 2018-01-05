package com.mdm.session;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static Integer getUserId(String token) throws Exception {
        return TokenSessionManager.getInstance().getDomainTokenSession(token).getAccountId();
    }

    public static String getUsername(String token) throws Exception {
        return TokenSessionManager.getInstance().getDomainTokenSession(token).getAccountName();
    }

    public static String getNickname(String token) throws Exception {
        return TokenSessionManager.getInstance().getDomainTokenSession(token).getNickName();
    }

    public static String getToken(HttpServletRequest request) throws Exception {
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader("token");
        }
        if (StringUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        return token;
    }
}
