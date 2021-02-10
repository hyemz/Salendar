package backend.server.salendar.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieService {
    public static Cookie createCookie(String cookieName, String value) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        long time = (cookieName == JwtService.ACCESS_TOKEN_NAME) ? JwtService.TOKEN_VALIDATION_SEDOND : JwtService.REFRESH_TOKEN_VALIDATION_SECOND;
        System.out.println(time);
        cookie.setMaxAge((int) time);
        cookie.setPath("/");
        return cookie;
    }


    public static Cookie getCookie(HttpServletRequest req, String cookieName) {
        final Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

}
