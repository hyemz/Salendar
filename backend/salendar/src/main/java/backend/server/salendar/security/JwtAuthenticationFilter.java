package backend.server.salendar.security;

import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.service.CookieService;
import backend.server.salendar.service.JwtService;
import backend.server.salendar.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        final Cookie jwtCookie = cookieService.getCookie(request, JwtService.ACCESS_TOKEN_NAME);

        // 유효한 토큰인지 확인합니다.
        if (jwtCookie != null) {
            String token = jwtCookie.getValue();

            if (jwtService.validateToken(token)) {
                Authentication authentication = jwtService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                final Cookie refreshCookie = cookieService.getCookie(request, JwtService.REFRESH_TOKEN_NAME);
                String refreshToken = refreshCookie.getValue();

                if (refreshToken != null) {
                    String refreshUsrName = redisService.getData(refreshToken);
                    if (refreshUsrName.equals(jwtService.getUserNo(refreshToken))) {
                        Authentication authentication = jwtService.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        User user = userRepository.findByUsrEmail(refreshUsrName).get();
                        String newToken = jwtService.createToken(user.getUsrEmail(), user.getRoles());

                        Cookie newAccessToken = cookieService.createCookie(JwtService.ACCESS_TOKEN_NAME, newToken);
                        response.addCookie(newAccessToken);
                    };
                }
            }
        }

        chain.doFilter(request, response);
    }
}